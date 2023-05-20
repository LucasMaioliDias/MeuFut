package com.trabalho.projetomeufut

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.trabalho.projetomeufut.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()





        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.BottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.item1 -> {
                    //val intent = Intent(this, quadras::class.java)
                    //startActivity(intent)
                    Toast.makeText(this, "Voce ja esta no menu!!!", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item2 -> {
                    val intent = Intent(this, quadrasalugadas::class.java)
                    startActivity(intent)
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



        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recycleview.layoutManager = LinearLayoutManager(this)
        binding.recycleview.setHasFixedSize(true)
        binding.recycleview.adapter = Adpter(getlist(), getdescription(), getImageList()) { name ->
            val intent = Intent(this, menuQuadra::class.java)
            val position = getlist().indexOf(name) // Encontra a posição do item selecionado
            val image = getImageList()[position] // Obtém a imagem correspondente
            intent.putExtra("nomeQuadra", name)
            intent.putExtra("imagemQuadra", image)
            startActivity(intent)
        }
    }

    private fun getlist() = listOf(
        "Quadra da Amizade",
        "Quadra do Sol Nascente",

    )

    private fun getdescription() = listOf(
        "Onde a amizade é celebrada em cada partida.",
        "Jogue seu esporte favorito nesta quadra.",

    )

    private fun getImageList() = listOf(

        R.drawable.img1,
        R.drawable.img,




    )

}