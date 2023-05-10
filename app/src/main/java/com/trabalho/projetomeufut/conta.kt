package com.trabalho.projetomeufut
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.bumptech.glide.Glide
import com.google.android.gms.cast.framework.media.ImagePicker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    val intent = Intent(this, quadras::class.java)
                    startActivity(intent)
                    true
                }
                R.id.item2 -> {
                    // Ação para o item 2

                    true
                }
                R.id.item3 -> {
                    val intent = Intent(this, conta::class.java)
                    startActivity(intent)
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









