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
        return "$id,$month,$date,$amount,$inCash,$isLate"
    }
}