package com.trabalho.projetomeufut

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class Formcadastro : AppCompatActivity() {

    private lateinit var txtNome: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtSenha: EditText
    private lateinit var txtTelefone: EditText
    private lateinit var btnCadastro: Button
    private val array = arrayOf("Preencha todos os campos", "Cadastro realizado com sucesso","Erro no cadastro")
    private lateinit var usuariosID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formcadastro)

        FirebaseApp.initializeApp(this)

        txtNome = findViewById(R.id.txtNome)
        txtTelefone = findViewById(R.id.txtTelefone)
        txtEmail = findViewById(R.id.txtEmail)
        txtSenha = findViewById(R.id.txtSenha)
        btnCadastro = findViewById(R.id.btnCadastro)

        btnCadastro.setOnClickListener { v: View ->
            val nome = txtNome.text.toString()
            val email = txtEmail.text.toString()
            val telefone = txtTelefone.text.toString()
            val senha = txtSenha.text.toString()

            if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
                Snackbar.make(v, array[0], Snackbar.LENGTH_SHORT).show()
            } else {
                cadastrarUsuario( email, senha, nome, telefone)
            }
        }
    }

    fun cadastrarUsuario(email: String, senha: String, nome: String, telefone: String) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    SalvarDadosUsuario(nome,telefone)

                    val user = FirebaseAuth.getInstance().currentUser
                    Snackbar.make(window.decorView.rootView,array[1], Snackbar.LENGTH_SHORT).show()
                    val intent = Intent(this, FormLogin::class.java)
                    startActivity(intent)
                } else {
                    var erro = ""
                    try {
                        throw task.exception!!
                    } catch (e: FirebaseAuthWeakPasswordException) {
                        erro = "Digite uma senha com no mínimo 6 caracteres"
                    } catch (e : FirebaseAuthUserCollisionException){
                        erro = "Esse usuário já foi cadastrado"
                    } catch (e : FirebaseAuthInvalidCredentialsException){
                        erro = "Email inválido"
                    }
                    Snackbar.make(window.decorView.rootView, erro, Snackbar.LENGTH_SHORT).show()
                }
            }
    }

    fun SalvarDadosUsuario(nome: String, telefone: String) {
        val db = FirebaseFirestore.getInstance()
        val usuarios = hashMapOf(
            "nome" to nome,
            "telefone" to telefone
        )
        val usuariosID = FirebaseAuth.getInstance().currentUser?.uid
        val usuariosRef = db.collection("usuarios").document(usuariosID!!)
        usuariosRef.set(usuarios)
            .addOnSuccessListener {
                Log.d("db", "sucesso ao salvar os dados")
            }
            .addOnFailureListener { e ->
                Log.d("db_error", "erro ao salvar dados: ${e.message}")
            }
    }
}
