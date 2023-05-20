package com.trabalho.projetomeufut

import ItemAgendamentoAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class quadrasalugadas : AppCompatActivity() {

    private lateinit var textViewNoAgendamentos: TextView
    private lateinit var agendado: RecyclerView
    private lateinit var adapter: ItemAgendamentoAdapter
    private lateinit var imagequadra : ImageView
    private lateinit var icon : ImageView
    private lateinit var minhaView : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadrasalugadas)




        textViewNoAgendamentos = findViewById(R.id.textViewNoAgendamentos)

        agendado = findViewById(R.id.agendadado)

        val dataList = getAgendamentoData()

        adapter = ItemAgendamentoAdapter(dataList)
        agendado.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        agendado.layoutManager = layoutManager











        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.item2 -> {
                    // Ação para o item 2
                    Toast.makeText(this, "Voce ja esta nas quadras agendandas!!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item3 -> {
                    val intent = Intent(this, conta::class.java)
                     startActivity(intent)
                    //Toast.makeText(this, "Voce já esta no login!!!", Toast.LENGTH_SHORT).show()
                    true

                }

                else -> {
                    true
                }
            }
        }











    }

    private fun getAgendamentoData(): List<AgendamentoItem> {
        val dataList = mutableListOf<AgendamentoItem>()

        val userID = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("agendamentos").document(userID!!)

        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val dia = document.get("dia") as? String ?: ""
                val horaField = document.get("hora")
                val hora = if (horaField is ArrayList<*>) {
                    val horaList = horaField as ArrayList<*>
                    horaList.joinToString(", ")
                } else {
                    ""
                }
                val nomeQuadra = document.get("nomeQuadra") as? String ?: ""
                val agendamentoItem = AgendamentoItem(nomeQuadra, dia, hora)
                dataList.add(agendamentoItem)
                adapter.notifyDataSetChanged()

                if (nomeQuadra.isEmpty()) {
                    textViewNoAgendamentos.visibility = View.VISIBLE
                    textViewNoAgendamentos.text = "Nenhum agendamento encontrado."
                    agendado.visibility = View.GONE
                } else {
                    textViewNoAgendamentos.visibility = View.GONE
                }
            }
        }

        return dataList
        }





}










