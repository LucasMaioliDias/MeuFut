package com.trabalho.projetomeufut

import CardViewAdapter
import SquareAdapter
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class menuQuadra : AppCompatActivity() {
    private lateinit var calendario: TextView
    private lateinit var datePicker: DatePicker
    private lateinit var agendamento: TextView
    private lateinit var celular: TextView

    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerView2: RecyclerView
    private lateinit var adapter: CardViewAdapter
    private lateinit var adapter2: SquareAdapter

    private lateinit var hours: List<String>


    private var selectedTime: String = ""
    private var selectedDate: String = ""
    private var selectedDateGlobal: String = ""
    var selectedDateGlobal2: List<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_quadra)

        calendario = findViewById(R.id.calendario)
        val datePicker = findViewById<DatePicker>(R.id.datePicker)
        //-----------------------------------------------------------//
        val agendamento = findViewById<TextView>(R.id.agendamento)
        val nomequadra = intent.extras
        val nome = nomequadra?.getString("nomeQuadra")
        agendamento.text = nome

        //-----------------------------------------------------------//

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        //-----------------------------------------------------------//

        val items = mutableListOf<String>()
        val textAbove = mutableListOf<String>()
        val textBelow =
            SimpleDateFormat("MMMM", Locale.getDefault()).format(Calendar.getInstance().time)
        val dateFormat = SimpleDateFormat("dd", Locale.getDefault())
        val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        for (i in 0 until 7) {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_MONTH, i)
            items.add(dateFormat.format(calendar.time))
            textAbove.add(DateFormatSymbols.getInstance().shortWeekdays[calendar.get(Calendar.DAY_OF_WEEK)].capitalize())
        }
        // --------------------------------------------------//


        val adapter = CardViewAdapter(items, textAbove, textBelow) { selectedDate ->
            selectedDateGlobal = selectedDate
            Toast.makeText(this, selectedDateGlobal, Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter


        //-----------------------------------------------------------//


        val recyclerView2: RecyclerView = findViewById(R.id.recyclerView2)
        val layoutManager2 = GridLayoutManager(this, 4) // Define o número de colunas desejado
        recyclerView2.layoutManager = layoutManager2

        val items2 = generateHoursList() // Sua lista de itens
        val adapter2 = SquareAdapter(items2)
        recyclerView2.adapter = adapter2

        //-----------------------------------------------------------//


        adapter2.setOnItemClickListener { time ->
            selectedDateGlobal2 = time

        }
        //-----------------------------------------------------------//


        //-----------------------------------------------------------//
        calendario = findViewById(R.id.calendario)
        calendario.setOnClickListener {
            if (datePicker.visibility == View.GONE) {
                datePicker.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                datePicker.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }
        //-----------------------------------------------------------//

        val btnMarcar = findViewById<Button>(R.id.btnMarcar)

        btnMarcar.setOnClickListener {
            val nomequadra = intent.extras
            val nome = nomequadra?.getString("nomeQuadra")
            val selectedDate = selectedDateGlobal
            val selectedTime = selectedDateGlobal2 ?: emptyList()
            val name = nome?: ""

            SalvarAgendamento(selectedDate, selectedTime, name)

        }


    }


    private fun generateHoursList(): List<String> {
        val hours = mutableListOf<String>()
        for (hour in 6..23) {
            hours.add(String.format(Locale.getDefault(), "%02d:00", hour))
            hours.add(String.format(Locale.getDefault(), "%02d:30", hour))
        }
        return hours
    }


    /* private fun SalvarAgendamento(data: String, time: String, name: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userID = currentUser?.uid

        // Verifica se o usuário está autenticado
        if (userID != null) {
            val nomequadra = intent.extras
            val nome = nomequadra?.getString("nomeQuadra")
            val database = FirebaseDatabase.getInstance()
            val agendamentoRef = database.reference.child("agendamento")
            val novoAgendamentoRef = agendamentoRef.push()
            val name = nome
            val userName = "Nome do usuário" // Substitua pela lógica correta para obter o nome do usuário
            val userTelefone = "Telefone do usuário" // Substitua pela lógica correta para obter o telefone do usuário

            val agendamento = hashMapOf(
                "nomeQuadra" to name,
                "dia" to data,
                "tempo" to time,
                "nomeUsuario" to userName,
                "telefoneUsuario" to userTelefone

            )

            novoAgendamentoRef.setValue(agendamento)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Agendamento salvo com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Erro ao salvar o agendamento!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }*/


    private fun SalvarAgendamento(selectedDate: String, selectedTime: List<String>, name: String) {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userID = currentUser?.uid
        val userEmail = currentUser?.email

        db.collection("usuarios").document(userID!!)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val userName = documentSnapshot.getString("nome")

                val agendamento = hashMapOf(
                    "nomeQuadra" to name,
                    "dia" to selectedDate,
                    "hora" to selectedTime,
                    "usuario" to hashMapOf(
                        "email" to userEmail,
                        "nome" to userName
                    )
                )

                // Salvar o agendamento com os dados do usuário
                db.collection("agendamentos").document(userID)
                    .set(agendamento)
                    .addOnSuccessListener {
                        Log.d("db", "Sucesso ao salvar os dados do agendamento")
                        Toast.makeText(
                            this@menuQuadra,
                            "Agendamento concluído com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.d("db_error", "Erro ao salvar dados do agendamento: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Log.d("db_error", "Erro ao obter o documento do usuário: ${e.message}")
            }
    }
}




