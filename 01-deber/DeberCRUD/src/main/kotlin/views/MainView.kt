package views

import dao.ClientDAO

class MainView {
    init {
        displayMenu()
    }

    private fun displayMenu() {
        val menu = "\nSeleccione la entidad con la que desea trabajar\n" +
                "1. Cliente\n" +
                "2. Pagos\n" +
                "3. Salir"
        println(menu)
        val option = readln().toInt()
        when(option){
            1 -> ClientView()
            2 -> {
                if (ClientDAO.getInstance().getAll().isEmpty()){
                    println("No existen clientes, para acceder a los pagos debes crear uno.")
                    displayMenu()
                }else{
                    PaymentView()
                }
            }
            3 -> {}
            else -> {
                println("Opción no válida, escoge otra.")
                displayMenu()
            }
        }
    }
}