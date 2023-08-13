package com.example.a03_deber.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.a03_deber.models.Payment
import java.time.LocalDate

class PaymentDAO(
    context: Context?
): SQLiteOpenHelper(
    context,
    "03deber", //nombre BDD
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getAllByClient(idClient: Int): ArrayList<Payment>{
        val dbRead = readableDatabase
        /*val scriptRead = """
            SELECT * FROM PAYMENT WHERE clientId = ?
            """.trimIndent()
        val parameters = arrayOf(idClient.toString())
        val resultRead = dbRead.rawQuery(
            scriptRead,
            parameters
        )*/

        val scriptRead = """
            SELECT * FROM PAYMENT
            """.trimIndent()
        val parameters = arrayOf(idClient.toString())
        val resultRead = dbRead.rawQuery(
            scriptRead,
            null
        )

        val paymentExists = resultRead.moveToFirst()
        var paymentFound: Payment?
        val payments = ArrayList<Payment>()
        if(paymentExists){
            do{
                paymentFound = Payment(
                    resultRead.getInt(0),
                    resultRead.getString(1),
                    LocalDate.parse(resultRead.getString(2)),
                    resultRead.getDouble(3),
                    resultRead.getInt(4) == 1,
                    resultRead.getInt(5) == 1
                )
                payments.add(paymentFound)
            }while(resultRead.moveToNext())
        }
        resultRead.close()
        dbRead.close()
        return payments
    }

    fun getById(id: Int): Payment? {
        val dbRead = readableDatabase
        val scriptRead = """
            SELECT * FROM PAYMENT WHERE id = ?
            """.trimIndent()
        val parameters = arrayOf(id.toString())
        val resultRead = dbRead.rawQuery(
            scriptRead,
            parameters
        )

        val paymentExists = resultRead.moveToFirst()
        val paymentFound: Payment?
        if(paymentExists){
            val id = resultRead.getInt(0)
            val month = resultRead.getString(1)
            val date = LocalDate.parse(resultRead.getString(2))
            val amount = resultRead.getDouble(3)
            val inCash = resultRead.getInt(4) == 1
            val isLate = resultRead.getInt(5) == 1
            paymentFound = Payment(
                id,
                month,
                date,
                amount,
                inCash,
                isLate
            )
        }else{
            paymentFound = null
        }

        resultRead.close()
        dbRead.close()
        return paymentFound
    }

    fun create(payment: Payment, idClient: Int): Boolean{
        val dbWrite = writableDatabase
        val valuesToSave = ContentValues()
        valuesToSave.put("month", payment.month)
        valuesToSave.put("date", payment.date.toString())
        valuesToSave.put("amount", payment.amount)
        valuesToSave.put("inCash", payment.inCash)
        valuesToSave.put("isLate", payment.isLate)
        valuesToSave.put("clientId", idClient)
        val resultSave = dbWrite.insert(
            "PAYMENT",
            null,
            valuesToSave
        )
        dbWrite.close()
        return resultSave.toInt() !== -1
    }

    fun update(payment: Payment): Boolean{
        val dbWrite = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("month", payment.month)
        valuesToUpdate.put("date", payment.date.toString())
        valuesToUpdate.put("amount", payment.amount)
        valuesToUpdate.put("inCash", payment.inCash)
        valuesToUpdate.put("isLate", payment.isLate)
        val parameters = arrayOf(payment.id.toString())
        val resultUpdate = dbWrite.update(
            "PAYMENT",
            valuesToUpdate,
            "id=?",
            parameters
        )
        dbWrite.close()
        return resultUpdate.toInt() !== -1
    }

    fun delete(id: Int): Boolean{
        val dbWrite = writableDatabase
        val parameters = arrayOf(id.toString())
        val resultDelete = dbWrite.delete(
            "PAYMENT",
            "id=?",
            parameters
        )
        dbWrite.close()
        return resultDelete.toInt() !== -1
    }
}