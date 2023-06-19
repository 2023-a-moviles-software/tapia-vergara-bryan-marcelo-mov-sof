package models

class Client (
    private val id: Int,
    private var identificationCard: String,
    private var name: String,
    private var phone: String,
    private var residence: String,
    private var isPreferential: Boolean = false,
    //private var payments: ArrayList<Payment>? = null
){
    constructor() : this(-1,"", "", "", "", false)

    constructor(
        id: Int,
        identificationCard: String,
        name: String,
        phone: String,
        residence: String,
        isPreferentialCode: Int,
        payments: ArrayList<Payment>
    ) : this(id,identificationCard, name, phone, residence, isPreferentialCode != 0) {

    }

    public fun getId(): Int{
        return id
    }

    public fun getIdentificationCard(): String{
        return identificationCard
    }

    public fun getName():String{
        return name
    }

    public fun getPhone():String{
        return phone
    }

    public fun getResidence():String{
        return residence
    }

    public fun getIsPreferential():Boolean{
        return isPreferential
    }
    /*
    public fun addPayment(payment: Payment){
        this.payments?.add(payment)
    }

    public fun removePayment(payment: Payment){
        this.payments?.add(payment)
    }*/

    public fun setIdentificationCard(identificationCard: String){
        this.identificationCard = identificationCard
    }

    public fun setName(name: String){
        this.name = name
    }

    public fun setPhone(phone: String){
        this.phone = phone
    }

    public fun setResidence(residence: String){
        this.residence = residence
    }

    public fun setIsPreferential(isPreferential: Boolean){
        this.isPreferential = isPreferential
    }

    override fun toString(): String {
        return "id=$id, identificationCard='$identificationCard', name='$name', phone='$phone', residence='$residence', isPreferential=$isPreferential"
    }


}