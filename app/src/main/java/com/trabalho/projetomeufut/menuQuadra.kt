package com.trabalho.projetomeufut

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class menuQuadra : AppCompatActivity() {

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


}
