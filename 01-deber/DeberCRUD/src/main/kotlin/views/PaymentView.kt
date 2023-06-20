package views

import dao.ClientDAO
import dao.PaymentDAO
import models.Payment
import java.time.LocalDate

class PaymentView {
    init {
        displayMenu()
    }

    private fun displayMenu() {
        var flag = true
        val menu = "\nMenú de Pago:\n" +
                "1. Listar todos los pagos\n" +
                "2. Crear un pago\n" +
                "3. Actualizar un pago\n" +
                "4. Buscar un pago\n" +
                "5. Buscar los pagos de un cliente\n" +
                "6. Eliminar un pago\n" +
                "7. Regresar al menú principal\n" +
                "8. Salir\n" +
                "Seleccione una opción:"
        do {
            println(menu)
            val option = readln().toInt()
            when(option){
                1 -> {
                    //List
                    val payments = PaymentDAO.getInstance().getAll()
                    if(payments.isEmpty()){
                        println("No existen pagos.")
                    }else{
                        println("ID\tMes\tFecha\tMonto\tEfectivo\tTarde\tCliente")
                        payments.forEach{
                            println(it.toPrint())
                        }
                    }
                }
                2 -> {
                    //Create
                    var payment: Payment = Payment()
                    payment.run {
                        println("Ingrese los datos del pago nuevo:")
                        println("Mes:")
                        setMonth(readln())
                        println("Fecha (aaaa-mm-dd):")
                        setDate(LocalDate.parse(readln()))
                        println("Monto:")
                        setAmount(readln().toDouble())
                        println("Efectivo (No: 0, Sí: 1):")
                        if(readln().toInt() == 0){
                            setInCash(false)
                        }else{
                            setInCash(true)
                        }
                        println("Tarde (No: 0, Sí: 1):")
                        if(readln().toInt() == 0){
                            setIsLate(false)
                        }else{
                            setIsLate(true)
                        }
                        println("Clientes: ")
                        val clients = ClientDAO.getInstance().getAll()
                        println("ID\tCédula\tNombre\tTeléfono\tResidencia\tPreferencial")
                        clients.forEach{
                            println(it.toPrint())
                        }
                        println("Cliente (ID):")
                        val client = ClientDAO.getInstance().getById(readln().toInt())
                        if(client == null){
                            println("El cliente ingresado no existe.")
                        }else{
                            setClient(client)
                        }
                    }
                    PaymentDAO.getInstance().create(payment)
                }
                3 -> {
                    //Update
                    println("Ingrese el ID del pago que desea actualizar:")
                    val id = readln().toInt()
                    if (PaymentDAO.getInstance().exists(id)){
                        var payment: Payment = Payment()
                        payment.run {
                            setId(id)
                            println("Mes:")
                            setMonth(readln())
                            println("Fecha (aaaa-mm-dd):")
                            setDate(LocalDate.parse(readln()))
                            println("Monto:")
                            setAmount(readln().toDouble())
                            println("Efectivo (No: 0, Sí: 1):")
                            if(readln().toInt() == 0){
                                setInCash(false)
                            }else{
                                setInCash(true)
                            }
                            println("Tarde (No: 0, Sí: 1):")
                            if(readln().toInt() == 0){
                                setIsLate(false)
                            }else{
                                setIsLate(true)
                            }
                            println("Clientes: ")
                            val clients = ClientDAO.getInstance().getAll()
                            println("ID\tCédula\tNombre\tTeléfono\tResidencia\tPreferencial")
                            clients.forEach{
                                println(it.toPrint())
                            }
                            println("Cliente (ID):")
                            val client = ClientDAO.getInstance().getById(readln().toInt())
                            if(client == null){
                                println("El cliente ingresado no existe.")
                            }else{
                                setClient(client)
                            }
                        }
                        PaymentDAO.getInstance().update(payment)
                    }else{
                        println("El pago que desea actualizar no existe.")
                    }
                }
                4 -> {
                    //GetOnePayment
                    println("Ingrese el ID del pago que desea ver:")
                    val id = readln().toInt()
                    val payment = PaymentDAO.getInstance().getById(id)
                    if(payment != null){
                        println("ID\tMes\tFecha\tMonto\tEfectivo\tTarde\tCliente")
                        println(payment.toPrint())
                    }else{
                        println("El pago que desea ver no existe.")
                    }
                }
                5 -> {
                    //GetByClient
                    println("Clientes: ")
                    val clients = ClientDAO.getInstance().getAll()
                    println("ID\tCédula\tNombre\tTeléfono\tResidencia\tPreferencial")
                    clients.forEach{
                        println(it.toPrint())
                    }
                    println("Ingrese el ID del cliente de quien desea ver los pagos:")
                    val idClient = readln().toInt()
                    val paymentsByClient = PaymentDAO.getInstance().getByClient(idClient)
                    if(paymentsByClient != null){
                        if (!paymentsByClient.isEmpty()){
                            println("Pagos del cliente:")
                            println("ID\tMes\tFecha\tMonto\tEfectivo\tTarde\tCliente")
                            paymentsByClient.forEach{
                                println(it.toPrint())
                            }
                        }else{
                            println("No existen pagos asociados a ese cliente")
                        }
                    }else{
                        println("El cliente de quien desea ver los pagos no existe.")
                    }
                }
                6 -> {
                    //Delete
                    println("Ingrese el ID del pago que desea eliminar:")
                    val id = readln().toInt()
                    val isDeleted = PaymentDAO.getInstance().delete(id)
                    if (isDeleted){
                        println("Pago eliminado.")
                    }else{
                        println("El pago que desea eliminar no existe.")
                    }
                }
                7 -> {
                    MenuView()
                }
                8 -> {
                    flag = false
                }
                else -> {
                    println("Opción no válida, escoge otra.")
                }
            }
        }while (flag)
    }
}