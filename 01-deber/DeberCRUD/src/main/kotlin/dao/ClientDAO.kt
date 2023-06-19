package dao

import models.Client
import java.io.File

class ClientDAO {
    private var file: File = File("src/main/kotlin/data/clients.txt").also {
        if (!it.exists()){
            it.createNewFile()
        }
    }

    companion object{
        private var instance: ClientDAO = ClientDAO()
        fun getInstance(): ClientDAO {
            return instance
        }
    }

    fun getAll(): ArrayList<Client> {
        val clients: ArrayList<Client> = ArrayList()
        file.readLines().forEach {
            val clientSplit = it.split(",")
            val client = Client(
                clientSplit[0].toInt(),
                clientSplit[1],
                clientSplit[2],
                clientSplit[3],
                clientSplit[4],
                clientSplit[5].toBoolean()
                )
            clients.add(client)
        }
        return clients
    }

    fun getById(id: Int): Client? {
        file.readLines().forEach {
            val clientSplit = it.split(",")
            if (clientSplit[0].toInt() == id) {
                return Client(
                    clientSplit[0].toInt(),
                    clientSplit[1],
                    clientSplit[2],
                    clientSplit[3],
                    clientSplit[4],
                    clientSplit[5].toBoolean()
                )
            }
        }
        return null
    }

    fun create(client: Client){
        when {
            file.readText() == "" -> {
                client.setId(0)
            }
            else -> {
                val lastId = file.readLines().last().split(",")[0].toInt()
                client.setId(lastId+1)
            }
        }
        file.appendText(client.toString() + "\n")
    }

    fun update(client: Client): Boolean {
        //delete(client.getId())
        //file.appendText(client.toString() + "\n")
        file.readLines().find { it.split(",")[0].toInt() == client.getId() } ?: return false
        val clients: ArrayList<Client> = getAll()
        var strClients = ""
        clients.forEach {
            if(it.getId() == client.getId()){
                it.setIdentificationCard(client.getIdentificationCard())
                it.setName(client.getName())
                it.setPhone(client.getPhone())
                it.setResidence(client.getResidence())
                it.setIsPreferential(client.getIsPreferential())
            }
            strClients+=it.toString() + "\n"
        }
        file.writeText(strClients)
        return true
    }

    fun delete(id: Int): Boolean{
        if (getById(id) == null){
            return false
        }
        val clients: String = file.readLines()
            .filter { it.split(",")[0].toInt() != id }
            .joinToString("\n", postfix = "\n")
        //.also { file.writeText(it) }
        file.writeText(clients)
        return true
    }


}