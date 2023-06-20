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
                file.appendText(client.toString())
            }
            else -> {
                val lastId = file.readLines().last().split(",")[0].toInt()
                client.setId(lastId+1)
                file.appendText("\n" + client.toString())
            }
        }

    }

    fun update(client: Client){
        var clients: ArrayList<Client> = getAll()
        var index = -1
        var strClients = ""

        clients.forEach {
            if(it.getId() == client.getId()){
                index = clients.indexOf(it)
            }
        }

        clients.set(index, client)
        var i = 0
        clients.forEach {
            if(i == 0){
                strClients+=it.toString()
            }else{
                strClients+="\n" + it.toString()
            }
            i++
        }
        file.writeText(strClients)
    }

    fun delete(id: Int): Boolean{
        if (!exists(id)){
            return false
        }
        val clients: String = file.readLines()
            .filter { it.split(",")[0].toInt() != id }
            .joinToString("\n")
        //.also { file.writeText(it) }
        file.writeText(clients)
        return true
    }

    fun exists(id: Int): Boolean{
        file.readLines().forEach {
            val client = it.split(",")[0].toInt()
            if (client == id) {
                return true
            }
        }
        return false
    }


}