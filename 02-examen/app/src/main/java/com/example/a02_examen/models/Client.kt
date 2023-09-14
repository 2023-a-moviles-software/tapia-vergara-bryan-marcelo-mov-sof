package com.example.a02_examen.models

import android.os.Parcel
import android.os.Parcelable

class Client(
    var id: Int?,
    var identificationCard: String,
    var name: String,
    var phone: String,
    var residence: String,
    var isPreferential: Boolean,
    //var payments: ArrayList<Payment>
) {
    //constructor() : this(null,"", "", "", "", false, ArrayList<Payment>())
    constructor() : this(null, "", "", "", "", false)

    override fun toString(): String {
        var preferential = "No preferencial"
        if (isPreferential){
            preferential = "Preferencial"
        }
        //return "$id - $identificationCard - $name - $phone - $residence - $preferential"
        return "$identificationCard - $name - $phone - $residence - $preferential"
    }
}