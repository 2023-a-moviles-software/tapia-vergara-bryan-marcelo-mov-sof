package dao

import models.Payment
import java.io.File
import java.time.LocalDate

class PaymentDAO {
    private var file: File = File("src/main/data/payments.txt").also {
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
            return null
        }
        return null
    }

    fun getPaymentByClient(idClient: Int): ArrayList<Payment>{
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
        val lastId = file.readLines().last().split(",")[0].toInt()
        if(lastId != null ){
            payment.setId(lastId+1)
        }else{
            payment.setId(0)
        }
        file.appendText(payment.toString() + "\n")
    }

    fun update(payment: Payment): Boolean {
        file.readLines().find { it.split(",")[0].toInt() == payment.getId() } ?: return false
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
        file.writeText(strPayments)
        return true
    }

    fun delete(id: Int){
        val payments: String = file.readLines()
            .filter { it.split(",")[0].toInt() != id }
            .joinToString("\n", postfix = "\n")
        //.also { file.writeText(it) }
        file.writeText(payments)
    }
}