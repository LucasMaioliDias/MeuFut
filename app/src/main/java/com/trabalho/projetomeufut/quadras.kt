package com.trabalho.projetomeufut

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class quadras : AppCompatActivity() {

    private lateinit var card1: CardView
    private lateinit var card2: CardView
    private lateinit var card3: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quadras)
        card1 = findViewById(R.id.card1)
        card2 = findViewById(R.id.card2)
        card3 = findViewById(R.id.card3)


        card1.setOnClickListener {
            // Navegue para a nova tela quando o primeiro CardView for clicado
            val intent = Intent(this, menuQuadra::class.java)
            startActivity(intent)
        }

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
                    FirebaseAuth.getInstance().signOut()

                    val intent = Intent(this, FormLogin::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                    true
                }
                R.id.item3 -> {
                    val intent = Intent(this, carrosel::class.java)
                    startActivity(intent)
                    true

                }

                else -> {
                    true
                }
            }
        }
    }

}