package models

class Client (
    private var id: Int?,
    private var identificationCard: String,
    private var name: String,
    private var phone: String,
    private var residence: String,
    private var isPreferential: Boolean = false,
    //private var payments: ArrayList<Payment>? = null
){
    constructor() : this(null,"", "", "", "", false)

    constructor(
        id: Int?,
        identificationCard: String,
        name: String,
        phone: String,
        residence: String,
        isPreferentialCode: Int,
    ) : this(id,identificationCard, name, phone, residence, isPreferentialCode != 0)

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
        return "id=$id, identificationCard='$identificationCard', name='$name', phone='$phone', residence='$residence', isPreferential=$isPreferential"
    }


}