package com.example.a01_examen.models

import java.time.LocalDate

class Payment(
    var id: Int?,
    var month: String,
    var date: LocalDate,
    var amount: Double,
    var inCash: Boolean,
    var isLate: Boolean
){
    constructor() : this(null, "", LocalDate.now(), 0.0, true, false)

    override fun toString(): String {
        var cash = "Tarjeta"
        var late = "A tiempo"
        if (inCash){
            cash = "Efectivo"
        }
        if (isLate){
            late = "Atrasado"
        }
        return "$id - $month - $date - $amount - $cash - $late"
    }
}