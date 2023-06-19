package models

import java.util.Date

class Payment (
    private val id: Int?,
    private var month: Int?,
    private var date: Date?,
    private var amount: Double?,
    private var inCash: Boolean = true,
    private var isLate: Boolean = false,
    private var client: Client?
) {
    constructor() : this(null, null, null, null, true, false, null)
    constructor(
        id: Int,
        month: Int,
        date: Date,
        amount: Double?,
        inCashCode: Int,
        isLateCode: Int,
        client: Client
    ) : this(id,month, date, amount, inCashCode != 0, isLateCode!= 0, client)

    override fun toString(): String {
        return "$id,$month,$date,$amount,$inCash,$isLate"
    }


}