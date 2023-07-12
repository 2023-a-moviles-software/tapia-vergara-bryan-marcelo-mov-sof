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
        return client!!.getPayments()
    }

    fun getById(id: Int, idClient: Int): Payment? {
        val payments = getAllByClient(idClient)
        return payments.find { it.getId() == id }
    }

    fun create(payment: Payment, idClient: Int){
        val payments = getAllByClient(idClient)
        if (payments.isEmpty()){
            payment.setId(0)
        }else{
            val lastId = payments.last().getId()?.plus(1)
            payment.setId(lastId!!)
        }

        payments.add(payment)
    }

    fun update(payment: Payment, idClient: Int){
        val paymentUpdated = getById(payment.getId()!!, idClient)
        paymentUpdated?.setMonth(payment.getMonth())
        paymentUpdated?.setDate(payment.getDate())
        paymentUpdated?.setAmount(payment.getAmount())
        paymentUpdated?.setInCash(payment.getInCash())
        paymentUpdated?.setIsLate(payment.getIsLate())

    }

    fun delete(id: Int, idClient: Int): Boolean{
        val payments = getAllByClient(idClient)
        val payment = getById(id, idClient) ?: return false
        return payments.remove(payment)
    }
}