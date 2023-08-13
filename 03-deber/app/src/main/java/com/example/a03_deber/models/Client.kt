package com.example.a03_deber.models

class Client(
    var id: Int?,
    var identificationCard: String,
    var name: String,
    var phone: String,
    var residence: String,
    var isPreferential: Boolean
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