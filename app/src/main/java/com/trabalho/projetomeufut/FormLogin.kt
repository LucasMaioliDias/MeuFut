package com.trabalho.projetomeufut

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class FormLogin : AppCompatActivity() {
    private lateinit var lblcadastrese: TextView
    private lateinit var txtUsuario: EditText
    private lateinit var txtSenhaLogin: EditText
    private lateinit var btnLogin: Button
    private lateinit var roberto: ProgressBar
    private lateinit var sharedPreferences: SharedPreferences
    private val array = arrayOf("Preencha todos os campos", "Login realizado com secesso ", "Erro no Login")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_login)



        txtUsuario = findViewById(R.id.txtUsuario)
        txtSenhaLogin = findViewById(R.id.txtSenhaLogin)
        btnLogin = findViewById(R.id.btnLogin)
        roberto = findViewById(R.id.roberto)
        lblcadastrese = findViewById(R.id.lblcadastrese)




        sharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)

        // Verifique se o usuário já está conectado
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navigateToNextScreen()
        }

        lblcadastrese.setOnClickListener {
            val cu = Intent(this, Formcadastro::class.java)
            startActivity(cu)
        }

        lblcadastrese.setOnClickListener {
            val cu = Intent(this, Formcadastro::class.java)
            startActivity(cu)
        }


        btnLogin.setOnClickListener {
            val email = txtUsuario.text.toString()
            val senha = txtSenhaLogin.text.toString()


            if (email.isEmpty() || senha.isEmpty()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(btnLogin.windowToken, 0)
                Snackbar.make(window.decorView.rootView, array[0], Snackbar.LENGTH_SHORT).show()
            } else {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(btnLogin.windowToken, 0)
                AutenticarUsuario()

            }
        }

    }


    private fun AutenticarUsuario() {
        val email = txtUsuario.text.toString()
        val senha = txtSenhaLogin.text.toString()

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    roberto.visibility = View.VISIBLE
                    Handler().postDelayed({
                        val joh = Intent(this, quadras::class.java)
                        startActivity(joh)
                    }, 3000)
                }
            }
    }

    private fun navigateToNextScreen() {
        val joh = Intent(this, quadras::class.java)
        startActivity(joh)
        finish() // Finalize a atividade atual para que o usuário não possa voltar para a tela de login usando o botão "Voltar" do dispositivo
    }
}


