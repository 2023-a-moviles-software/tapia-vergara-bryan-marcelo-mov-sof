package com.example.a01_examen.dao

import com.example.a01_examen.database.DataBaseMemory
import com.example.a01_examen.models.Client


class ClientDAO {

    companion object{
        private var instance: ClientDAO = ClientDAO()
        fun getInstance(): ClientDAO {
            return instance
        }
    }

    fun getAll(): ArrayList<Client>{
        return DataBaseMemory.clients
    }

    fun getById(id: Int): Client? {
        return DataBaseMemory.clients.find { it.id == id }
    }

    fun create(client: Client){
        if (DataBaseMemory.clients.isEmpty()){
            client.id = 0
        }else{
            val lastId = DataBaseMemory.clients.last().id?.plus(1)
            client.id = lastId
        }
        DataBaseMemory.clients.add(client)
    }

    fun update(client: Client){
        val clientUpdated = getById(client.id!!)
        clientUpdated!!.identificationCard = client.identificationCard
        clientUpdated.name = client.name
        clientUpdated.phone = client.phone
        clientUpdated.residence = client.residence
        clientUpdated.isPreferential = client.isPreferential
    }

    fun delete(id: Int): Boolean{
        val client = getById(id) ?: return false
        return DataBaseMemory.clients.remove(client)
    }
}