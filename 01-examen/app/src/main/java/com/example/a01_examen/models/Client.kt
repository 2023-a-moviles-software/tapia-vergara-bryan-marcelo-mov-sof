package com.example.a01_examen.models

import java.time.LocalDate

class Client(
    private var id: Int?,
    private var identificationCard: String,
    private var name: String,
    private var phone: String,
    private var residence: String,
    private var isPreferential: Boolean,
    private var payments: ArrayList<Payment> = arrayListOf<Payment>()
) {
    constructor() : this(null,"", "", "", "", false)

    fun getId(): Int? {
        return id
    }

    fun getIdentificationCard(): String{
        return identificationCard
    }

    fun getName():String{
        return name
    }

    fun getPhone():String{
        return phone
    }

    fun getResidence():String{
        return residence
    }

    fun getIsPreferential():Boolean{
        return isPreferential
    }

    fun getPayments(): ArrayList<Payment>{
        return payments
    }

    fun setId(id: Int){
        this.id = id
    }

    fun setIdentificationCard(identificationCard: String){
        this.identificationCard = identificationCard
    }

    fun setName(name: String){
        this.name = name
    }

    fun setPhone(phone: String){
        this.phone = phone
    }

    fun setResidence(residence: String){
        this.residence = residence
    }

    fun setIsPreferential(isPreferential: Boolean){
        this.isPreferential = isPreferential
    }

    override fun toString(): String {
        return "$id,$identificationCard,$name,$phone,$residence,$isPreferential"
    }
}