package models

import java.time.LocalDate
import java.time.Month
import java.time.Year


class Payment (
    private var id: Int?,
    private var month: String,
    private var date: LocalDate,
    private var amount: Double,
    private var inCash: Boolean,
    private var isLate: Boolean,
    private var client: Client
) {
    constructor() : this(null, "", LocalDate.now(), 0.0, true, false, Client())
    constructor(
        id: Int,
        month: String,
        date: LocalDate,
        amount: Double,
        inCashCode: Int,
        isLateCode: Int,
        client: Client
    ) : this(id,month, date, amount, inCashCode != 0, isLateCode!= 0, client)

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

    fun getClient(): Client {
        return client
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

    fun setClient(client: Client){
        this.client = client
    }

    override fun toString(): String {
        return "$id,$month,$date,$amount,$inCash,$isLate,${client.getId()}"
    }

    fun toPrint(): String{
        var cash = "No"
        var late = "No"
        if (inCash){
            cash = "Sí"
        }
        if (isLate){
            late = "Sí"
        }
        return "$id\t$month\t$date\t$amount\t$cash\t$late\t${client.getName()}"
    }
}