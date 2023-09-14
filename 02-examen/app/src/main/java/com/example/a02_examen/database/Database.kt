package com.example.a02_examen.database

import com.example.a02_examen.models.Client
import com.example.a02_examen.models.Payment

class Database {
    companion object {
        val listClients = arrayListOf<Client>()
        val listPayments = arrayListOf<Payment>()
    }
}