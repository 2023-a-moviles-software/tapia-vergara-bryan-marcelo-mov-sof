package views

import dao.ClientDAO
import models.Client

class ClientView {
    init {
        displayMenu()
    }

    private fun displayMenu() {
        var flag = true
        val menu = "\nSeleccione una opción:\n" +
                "1. Listar todos los clientes\n" +
                "2. Crear un cliente\n" +
                "3. Actualizar un cliente\n" +
                "4. Buscar un cliente\n" +
                "5. Eliminar un cliente\n" +
                "6. Regresar al menú principal"
        do {
            println(menu)
            val option = readln().toInt()
            when(option){
                1 -> {
                    //List
                    val clients = ClientDAO.getInstance().getAll()
                    if(clients.isEmpty()){
                        println("No existen clientes.")
                    }else{
                        println("ID\tCédula\tNombre\tTeléfono\tResidencia\tPreferencial")
                        clients.forEach{
                            println(it.toPrint())
                        }
                    }
                }
                2 -> {
                    //Create
                    var client:Client = Client()
                    client.run {
                        println("Ingrese los datos del cliente nuevo:")
                        println("Cédula:")
                        setIdentificationCard(readln())
                        println("Nombre:")
                        setName(readln())
                        println("Teléfono:")
                        setPhone(readln())
                        println("Residencia:")
                        setResidence(readln())
                        println("Preferencial (No: 0, Sí: 1):")
                        if(readln().toInt() == 0){
                            setIsPreferential(false)
                        }else{
                            setIsPreferential(true)
                        }
                    }
                    ClientDAO.getInstance().create(client)
                }
                3 -> {
                    //Update
                    println("Ingrese el ID del cliente que desea actualizar:")
                    val id = readln().toInt()
                    if (ClientDAO.getInstance().exists(id)){
                        var client:Client = Client()
                        client.run {
                            setId(id)
                            println("Ingrese los datos actualizados del cliente:")
                            println("Cédula:")
                            setIdentificationCard(readln())
                            println("Nombre:")
                            setName(readln())
                            println("Teléfono:")
                            setPhone(readln())
                            println("Residencia:")
                            setResidence(readln())
                            println("Preferencial (No: 0, Sí: 1):")
                            if(readln().toInt() == 0){
                                setIsPreferential(false)
                            }else{
                                setIsPreferential(true)
                            }
                        }
                        ClientDAO.getInstance().update(client)
                    }else{
                        println("El cliente que desea actualizar no existe.")
                    }
                }
                4 -> {
                    //GetOneClient
                    println("Ingrese el ID del cliente que desea ver:")
                    val id = readln().toInt()
                    val client = ClientDAO.getInstance().getById(id)
                    if(client != null){
                        println("ID\tCédula\tNombre\tTeléfono\tResidencia\tPreferencial")
                        println(client.toPrint())
                    }else{
                        println("El cliente que desea ver no existe.")
                    }

                }
                5 -> {
                    //Delete
                    println("Ingrese el ID del cliente que desea eliminar:")
                    val id = readln().toInt()
                    val isDeleted = ClientDAO.getInstance().delete(id)
                    if (isDeleted){
                        println("Cliente eliminado.")
                    }else{
                        println("El cliente que desea eliminar no existe.")
                    }
                }
                6 -> {
                    MainView()
                }
                else -> {
                    println("Opción no válida, escoge otra.")
                }
            }
        }while (flag)




    }
}