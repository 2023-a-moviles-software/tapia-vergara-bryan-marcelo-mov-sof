package models

class Client (
    private var id: Int?,
    private var identificationCard: String,
    private var name: String,
    private var phone: String,
    private var residence: String,
    private var isPreferential: Boolean
){
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
    /*
    public fun addPayment(payment: Payment){
        this.payments?.add(payment)
    }

    public fun removePayment(payment: Payment){
        this.payments?.add(payment)
    }*/

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

    fun toPrint(): String{
        var preferential = "No"
        if (isPreferential){
            preferential = "Sí"
        }
        return "$id\t$identificationCard\t$name\t$phone\t$residence\t$preferential"
    }

}