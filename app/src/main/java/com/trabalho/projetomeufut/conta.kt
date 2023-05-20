package com.trabalho.projetomeufut
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File
import java.io.FileOutputStream

class conta : AppCompatActivity() {
    private val PICK_IMAGE_REQUEST_CODE = 123

    private lateinit var txtNomeCadastro: TextView
    private lateinit var buttonLogout: Button
    private lateinit var txtEmailCadastro: TextView
    private lateinit var profile_image: ImageView
    private lateinit var txtTelefoneCadastro: TextView
    private lateinit var txtTrocarFoto: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_conta)

        txtNomeCadastro = findViewById(R.id.txtNomeCadastro)
        buttonLogout = findViewById(R.id.buttonLogout)
        txtEmailCadastro = findViewById(R.id.txtEmailCadastro)
        profile_image = findViewById(R.id.profile_image)
        txtTelefoneCadastro = findViewById(R.id.txtTelefoneCadastro)
        txtTrocarFoto = findViewById(R.id.txtTrocarFoto)


        buttonLogout.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, FormLogin::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()

        }

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

                    true
                }
                R.id.item3 -> {
                    //val intent = Intent(this, conta::class.java)
                   // startActivity(intent)
                    Toast.makeText(this, "Voce já esta no login!!!", Toast.LENGTH_SHORT).show()
                    true

                }

                else -> {
                    true
                }
            }
        }

        val txtTrocarFoto = findViewById<TextView>(R.id.txtTrocarFoto)
        txtTrocarFoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }
        val textView = findViewById<TextView>(R.id.txtTrocarFoto)
        textView.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
        }


        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val email = currentUser?.email
        txtEmailCadastro.text = email

        val userID = FirebaseAuth.getInstance().currentUser?.uid
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("usuarios").document(userID!!)

        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val nome = document.getString("nome")
                val telefone = document.getString("telefone")
                txtNomeCadastro.text = nome
                txtTelefoneCadastro.text = telefone
            }
        }.addOnFailureListener { exception ->
            // tratar exceção aqui
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            Glide.with(this).load(imageUri)
                .into(profile_image) // carregar a imagem selecionada no ImageView usando Glide

            val inputStream = contentResolver.openInputStream(imageUri!!)
            val file = File(
                getExternalFilesDir(null),
                "profile_image"
            ) // salva a imagem no diretório externo de arquivos
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()

            // salvar o caminho da imagem no Firebase, por exemplo:
            val userID = FirebaseAuth.getInstance().currentUser?.uid
            val db = FirebaseFirestore.getInstance()
            db.collection("usuarios").document(userID!!).update("caminho_imagem", file.absolutePath)
        }
    }
}









