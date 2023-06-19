package dao

import models.Client
import java.io.File

class ClientDAO {
    private var file: File = File("src/main/data/clients.txt").also {
        if (!it.exists()){
            it.createNewFile()
        }
    }

    public fun getAll(): ArrayList<Client> {
        var clients: ArrayList<Client> = ArrayList()
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

    public fun getById(id: Int): Client? {
        file.readLines().forEach {
            val clientSplit = it.split(",")
            if (clientSplit[0].toInt() == id){
                val client = Client(
                    clientSplit[0].toInt(),
                    clientSplit[1],
                    clientSplit[2],
                    clientSplit[3],
                    clientSplit[4],
                    clientSplit[5].toBoolean()
                )
                return client
            }
        }
        return null
    }

    public fun create(client: Client){
        file.appendText(client.toString() + "\n")
    }

    public fun update(client: Client): Boolean {
        //delete(client.getId())
        //file.appendText(client.toString() + "\n")
        file.readLines().find { it.split(",")[0].toInt() == client.getId() } ?: return false
        var clients: ArrayList<Client> = getAll()
        var strClients: String = ""
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

    public fun delete(id: Int){
        val clients: String = file.readLines()
            .filter { it.split(",")[0].toInt() != id }
            .joinToString("\n", postfix = "\n")
            //.also { file.writeText(it) }
        file.writeText(clients)
    }


}