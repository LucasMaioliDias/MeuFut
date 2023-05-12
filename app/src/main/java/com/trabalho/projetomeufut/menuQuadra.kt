package com.trabalho.projetomeufut

import CardViewAdapter
import android.graphics.Rect
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class menuQuadra : AppCompatActivity() {
    private lateinit var teste: Button
    private lateinit var calendario: TextView
    private lateinit var btnMarcar: Button
    private lateinit var datePicker: DatePicker
    private lateinit var timePicker: TimePicker
    private lateinit var calendar: Calendar
    private lateinit var dia: String
    private lateinit var mes: String
    private lateinit var hora: String
    private lateinit var minute: String



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_quadra)


        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager


        val items = listOf("1", "2", "3", "4", "5","6","7") // Sua lista de números
        val textAbove = listOf("Seg","Ter","Qua","Qui","Sex","Sab","Dom") // Texto acima do número
        val textBelow = "Maio" // Texto abaixo do número

        val adapter = CardViewAdapter(items, textAbove, textBelow)
        recyclerView.adapter = adapter








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








        btnMarcar = findViewById(R.id.btnMarcar)
        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)

        calendar = Calendar.getInstance()
        dia = ""
        mes = ""
        hora = ""
        minute = ""

        datePicker.setOnDateChangedListener { _, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            dia = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
            mes = if (month < 9) "0${month + 1}" else "${month + 1}"
        }

        timePicker.setOnTimeChangedListener { _, hour, minute ->
            hora = if (hour < 10) "0$hour" else hour.toString()
            this.minute = if (minute < 10) "0$minute" else minute.toString()
        }


        btnMarcar.setOnClickListener {

            val dataHora = "$dia/$mes/${calendar.get(Calendar.YEAR)} $hora:$minute"
            SalvarAgendamento(dataHora)
        }
    }

    private fun SalvarAgendamento(dataHora: String) {
        val db = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userID = currentUser?.uid
        val userEmail = currentUser?.email

        // Consultar o documento do usuário para obter o nome
        db.collection("usuarios").document(userID!!)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                val userName = documentSnapshot.getString("nome")

                val Agendamento = hashMapOf(
                    "dataHora" to dataHora,
                    "usuario" to hashMapOf(
                        "email" to userEmail,
                        "nome" to userName
                    )
                )

                // Salvar o agendamento com os dados do usuário
                val AgendamentoRef = db.collection("agendamentos").document(userID)
                AgendamentoRef.set(Agendamento)
                    .addOnSuccessListener {
                        Log.d("db", "sucesso ao salvar os dados")
                        Toast.makeText(
                            this@menuQuadra,
                            "Agendamento concluído com sucesso!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.d("db_error", "erro ao salvar dados: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Log.d("db_error", "erro ao obter o documento do usuário: ${e.message}")
            }
    }

    class HorizontalMarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.right = margin
        }
    }


}
