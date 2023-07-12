package com.example.a01_examen.DataBase

import com.example.a01_examen.models.Client

class DataBaseMemory {
    companion object{
        val clients = arrayListOf<Client>()
        var lastId = 0
        init {
            clients.add(
                Client(0, "1727628362", "Bryan Tapia", "0958881256", "Quito", false)
            )
            lastId = clients.size
        }
    }
}