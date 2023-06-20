package dao

import models.Payment
import java.io.File
import java.time.LocalDate

class PaymentDAO {
    private var file: File = File("src/main/kotlin/data/payments.txt").also {
        if (!it.exists()){
            it.createNewFile()
        }
    }

    companion object{
        private var instance: PaymentDAO = PaymentDAO()
        fun getInstance(): PaymentDAO {
            return instance
        }
    }

    fun getAll(): ArrayList<Payment> {
        val payments: ArrayList<Payment> = ArrayList()
        file.readLines().forEach {
            val paymentSplit = it.split(",")
            val payment = Payment(
                paymentSplit[0].toInt(),
                paymentSplit[1],
                LocalDate.parse(paymentSplit[2]),
                paymentSplit[3].toDouble(),
                paymentSplit[4].toBoolean(),
                paymentSplit[5].toBoolean(),
                ClientDAO.getInstance().getById(paymentSplit[6].toInt())
            )
            payments.add(payment)
        }
        return payments
    }

    fun getById(id: Int): Payment? {
        file.readLines().forEach {
            val paymentSplit = it.split(",")
            if (paymentSplit[0].toInt() == id) {
                return Payment(
                    paymentSplit[0].toInt(),
                    paymentSplit[1],
                    LocalDate.parse(paymentSplit[2]),
                    paymentSplit[3].toDouble(),
                    paymentSplit[4].toBoolean(),
                    paymentSplit[5].toBoolean(),
                    ClientDAO.getInstance().getById(paymentSplit[6].toInt())
                )
            }
        }
        return null
    }

    fun getPaymentByClient(idClient: Int): ArrayList<Payment>? {
        if (ClientDAO.getInstance().getById(idClient) == null){
            return null
        }
        val payments: ArrayList<Payment> = ArrayList()
        file.readLines().forEach {
            val paymentSplit = it.split(",")
            if (paymentSplit[6].toInt() == idClient) {
                val payment = Payment(
                    paymentSplit[0].toInt(),
                    paymentSplit[1],
                    LocalDate.parse(paymentSplit[2]),
                    paymentSplit[3].toDouble(),
                    paymentSplit[4].toBoolean(),
                    paymentSplit[5].toBoolean(),
                    ClientDAO.getInstance().getById(paymentSplit[6].toInt())
                )
                payments.add(payment)
            }
        }
        return payments
    }

    fun create(payment: Payment){

        /*val lastId = file.readLines().last().split(",")[0].toInt()
        if(lastId != null ){
            payment.setId(lastId+1)
        }else{
            payment.setId(0)
        }
        file.appendText(payment.toString() + "\n")*/

        when {
            file.readText() == "" -> {
                payment.setId(0)
                file.appendText(payment.toString())
            }
            else -> {
                val lastId = file.readLines().last().split(",")[0].toInt()
                payment.setId(lastId+1)
                file.appendText("\n" + payment.toString())
            }
        }
    }

    fun update(payment: Payment){
        var payments: ArrayList<Payment> = getAll()
        var index = -1
        var strPayments = ""

        payments.forEach {
            if(it.getId() == payment.getId()){
                index = payments.indexOf(it)
            }
        }

        payments.set(index, payment)

        payments.forEach {
            strPayments+=it.toString() + "\n"
        }
        strPayments.removeSuffix("\n")
        file.writeText(strPayments)

        /*
        val payments: ArrayList<Payment> = getAll()
        var strPayments = ""
        payments.forEach {
            if(it.getId() == payment.getId()){
                it.setMonth(payment.getMonth())
                it.setDate(payment.getDate()!!)
                it.setAmount(payment.getAmount())
                it.setInCash(payment.getInCash())
                it.setIsLate(payment.getIsLate())
                it.setClient(payment.getClient()!!)
            }
            strPayments+=it.toString() + "\n"
        }
        file.writeText(strPayments)*/
    }

    fun delete(id: Int): Boolean{
        if (!exists(id)){
            return false
        }
        val payments: String = file.readLines()
            .filter { it.split(",")[0].toInt() != id }
            .joinToString("\n")
        //.also { file.writeText(it) }
        file.writeText(payments)
        return true
    }

    fun exists(id: Int): Boolean{
        file.readLines().forEach {
            val payment = it.split(",")[0].toInt()
            if (payment == id) {
                return true
            }
        }
        return false
    }
}