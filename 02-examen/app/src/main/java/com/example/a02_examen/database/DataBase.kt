package com.example.a03_deber.database

import com.example.a03_deber.dao.ClientDAO
import com.example.a03_deber.dao.PaymentDAO

class DataBase {
    companion object{
        var tableClient: ClientDAO? = null
        var tablePayment: PaymentDAO? = null
    }
}