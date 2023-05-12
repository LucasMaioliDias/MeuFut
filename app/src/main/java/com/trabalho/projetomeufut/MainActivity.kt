package com.trabalho.projetomeufut

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.trabalho.projetomeufut.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()



        initRecyclerView()


    }

    private fun initRecyclerView(){
        binding.recycleview.layoutManager = LinearLayoutManager(this)
        binding.recycleview.setHasFixedSize(true)
        binding.recycleview.adapter = Adpter(getlist(),getdescription())


    }

    private fun getlist() = listOf(
        "Quadra da Amizade",
        "Quadra do Sol Nascente",
        "Quadra Esportiva São Jorge",
        "Quadra dos Amigos Unidos",
        "Quadra do Recanto Esportivo",
        "Quadra dos Craques",
        "Quadra da Vitória",
        "Quadra do Esporte Total",
        "Quadra do Futuro",
        "Quadra do Sucesso",
        "Quadra do Esporte Total",
        "Quadra do Futuro",
        "Quadra do Sucesso",
    )

    private fun getdescription() = listOf(
        "Onde a amizade é celebrada em cada partida.",
        "Jogue seu esporte favorito nesta quadra.",
        "Onde você pode se sentir como um campeão.",
        "Reúna seus amigos nesta quadra de diversão.",
        "Um local tranquilo e agradável para praticar esportes.",
        "Os verdadeiros craques mostrarem seu talento.",
        "Experimente a sensação de vitória a cada jogo.",
        "Essa é a proposta da quadra do Esporte Total.",
        "Projetada para os esportes do futuro.",
        "Escreva sua história de sucesso no mundo dos esportes.",
        "Essa é a proposta da quadra do Esporte Total.",
        "Projetada para os esportes do futuro.",
        "Escreva sua história de sucesso no mundo dos esportes.",
    )


}