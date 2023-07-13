package com.example.a01_examen.models

import java.time.LocalDate

class Client(
    var id: Int?,
    var identificationCard: String,
    var name: String,
    var phone: String,
    var residence: String,
    var isPreferential: Boolean,
    var payments: ArrayList<Payment> = arrayListOf<Payment>()
) {
    constructor() : this(null,"", "", "", "", false)

    override fun toString(): String {
        var preferential = "No preferencial"
        if (isPreferential){
            preferential = "Preferencial"
        }
        return "$id - $identificationCard - $name - $phone - $residence - $preferential"
    }
}