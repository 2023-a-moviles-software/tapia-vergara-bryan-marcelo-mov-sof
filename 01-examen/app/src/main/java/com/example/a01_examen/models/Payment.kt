package com.example.a01_examen.models

import java.time.LocalDate

class Payment(
    private var id: Int?,
    private var month: String,
    private var date: LocalDate,
    private var amount: Double,
    private var inCash: Boolean,
    private var isLate: Boolean
){
    constructor() : this(null, "", LocalDate.now(), 0.0, true, false)

    fun getId(): Int? {
        return id
    }

    fun getMonth(): String {
        return month
    }

    fun getDate(): LocalDate {
        return date
    }

    fun getAmount(): Double {
        return amount
    }

    fun getInCash(): Boolean {
        return inCash
    }

    fun getIsLate(): Boolean {
        return isLate
    }

    fun setId(id: Int){
        this.id = id
    }

    fun setMonth(month: String){
        this.month = month
    }

    fun setDate(date: LocalDate){
        this.date = date
    }

    fun setAmount(amount: Double){
        this.amount = amount
    }

    fun setInCash(inCash: Boolean){
        this.inCash = inCash
    }

    fun setIsLate(isLate: Boolean){
        this.isLate = isLate
    }

    override fun toString(): String {
        return "$id,$month,$date,$amount,$inCash,$isLate"
    }
}