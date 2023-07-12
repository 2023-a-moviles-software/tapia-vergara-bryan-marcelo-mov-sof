package com.example.a01_examen.dao

import com.example.a01_examen.DataBase.DataBaseMemory
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
        return DataBaseMemory.clients.find { it.getId() == id }
    }

    fun create(client: Client){
        if (DataBaseMemory.clients.isEmpty()){
            client.setId(0)
        }else{
            val lastId = DataBaseMemory.clients.last().getId()?.plus(1)
            client.setId(lastId!!)
        }
        DataBaseMemory.clients.add(client)
    }

    fun update(client: Client){
        val clientUpdated = getById(client.getId()!!)
        clientUpdated?.setIdentificationCard(client.getIdentificationCard())
        clientUpdated?.setName(client.getName())
        clientUpdated?.setPhone(client.getPhone())
        clientUpdated?.setResidence(client.getResidence())
        clientUpdated?.setIsPreferential(client.getIsPreferential())
    }

    fun delete(id: Int): Boolean{
        val client = getById(id) ?: return false
        return DataBaseMemory.clients.remove(client)
    }
}