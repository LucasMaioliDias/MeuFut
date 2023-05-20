package com.trabalho.projetomeufut

import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessagingService


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        // Armazena o token no banco de dados
        val database = FirebaseDatabase.getInstance()
        val tokenRef = database.reference.child("tokens")
        tokenRef.child(token).setValue(true)

        // Exibe uma mensagem de log informando que o token foi armazenado
        Log.d("Token", "Token armazenado no banco de dados: $token")
    }
}








