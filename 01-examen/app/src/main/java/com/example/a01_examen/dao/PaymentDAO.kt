package com.example.a01_examen.dao

import com.example.a01_examen.models.Payment

class PaymentDAO {

    companion object{
        private var instance: PaymentDAO = PaymentDAO()
        fun getInstance(): PaymentDAO {
            return instance
        }
    }

    fun getAllByClient(idClient: Int): ArrayList<Payment>{
        val client = ClientDAO.getInstance().getById(idClient)
        return client!!.payments
    }

    fun getById(id: Int, idClient: Int): Payment? {
        val payments = getAllByClient(idClient)
        return payments.find { it.id == id }
    }

    fun create(payment: Payment, idClient: Int){
        val payments = getAllByClient(idClient)
        if (payments.isEmpty()){
            payment.id = 0
        }else{
            val lastId = payments.last().id?.plus(1)
            payment.id = lastId
        }

        payments.add(payment)
    }

    fun update(payment: Payment, idClient: Int){
        val paymentUpdated = getById(payment.id!!, idClient)
        paymentUpdated!!.month = payment.month
        paymentUpdated.date = payment.date
        paymentUpdated.amount = payment.amount
        paymentUpdated.inCash = payment.inCash
        paymentUpdated.isLate = payment.isLate
    }

    fun delete(id: Int, idClient: Int): Boolean{
        val payments = getAllByClient(idClient)
        val payment = getById(id, idClient) ?: return false
        return payments.remove(payment)
    }
}