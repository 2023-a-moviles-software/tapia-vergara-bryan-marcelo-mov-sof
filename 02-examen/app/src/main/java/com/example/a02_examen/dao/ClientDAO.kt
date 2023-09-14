package com.example.a03_deber.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.a03_deber.models.Client

class ClientDAO(
    context: Context?,
): SQLiteOpenHelper(
    context,
    "03deber", //nombre BDD
    null,
    1
) {

    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCreateTableClient =
            """
                CREATE TABLE CLIENT(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    identificationCard VARCHAR(10),
                    name VARCHAR(50),
                    phone VARCHAR(50),
                    residence VARCHAR(100),
                    isPreferential BOOLEAN
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCreateTableClient)
        val scriptSQLCreateTablePayment =
            """
                CREATE TABLE PAYMENT(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    month VARCHAR(50),
                    date DATE,
                    amount DOUBLE,
                    inCash BOOLEAN,
                    isLate BOOLEAN,
                    clientId INTEGER,
                    FOREIGN KEY(clientId) REFERENCES CLIENT(id) ON DELETE CASCADE
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCreateTablePayment)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun getAll(): ArrayList<Client>{
        val dbRead = readableDatabase
        val scriptRead = """
            SELECT * FROM CLIENT
            """.trimIndent()
        val resultRead = dbRead.rawQuery(
            scriptRead,
            null
        )

        val clientExists = resultRead.moveToFirst()
        var clientFound: Client?
        val clients = ArrayList<Client>()
        if(clientExists){
            do {
                val id = resultRead.getInt(0)
                val identificationCard = resultRead.getString(1)
                val name = resultRead.getString(2)
                val phone = resultRead.getString(3)
                val residence = resultRead.getString(4)
                val isPreferential = resultRead.getInt(5) == 1
                clientFound = Client(
                    id,
                    identificationCard,
                    name,
                    phone,
                    residence,
                    isPreferential
                )
                clients.add(clientFound)
            }while (resultRead.moveToNext())
        }
        resultRead.close()
        dbRead.close()
        return clients
    }

    fun getById(id: Int): Client? {
        val dbRead = readableDatabase
        val scriptRead = """
            SELECT * FROM CLIENT WHERE id = ?
            """.trimIndent()
        val parameters = arrayOf(id.toString())
        val resultRead = dbRead.rawQuery(
            scriptRead,
            parameters
        )

        val clientExists = resultRead.moveToFirst()
        val clientFound: Client?
        if(clientExists){
            val id = resultRead.getInt(0)
            val identificationCard = resultRead.getString(1)
            val name = resultRead.getString(2)
            val phone = resultRead.getString(3)
            val residence = resultRead.getString(4)
            val isPreferential = resultRead.getInt(5) == 1
            clientFound = Client(
                id,
                identificationCard,
                name,
                phone,
                residence,
                isPreferential
            )
        }else{
            clientFound = null
        }
        resultRead.close()
        dbRead.close()
        return clientFound
    }

    fun create(client: Client): Boolean{
        val dbWrite = writableDatabase
        val valuesToSave = ContentValues()
        valuesToSave.put("identificationCard", client.identificationCard)
        valuesToSave.put("name", client.name)
        valuesToSave.put("phone", client.phone)
        valuesToSave.put("residence", client.residence)
        valuesToSave.put("isPreferential", client.isPreferential)
        val resultSave = dbWrite.insert(
            "CLIENT",
            null,
            valuesToSave
        )
        dbWrite.close()
        return resultSave.toInt() !== -1
    }

    fun update(client: Client): Boolean{
        val dbWrite = writableDatabase
        val valuesToUpdate = ContentValues()
        valuesToUpdate.put("identificationCard", client.identificationCard)
        valuesToUpdate.put("name", client.name)
        valuesToUpdate.put("phone", client.phone)
        valuesToUpdate.put("residence", client.residence)
        valuesToUpdate.put("isPreferential", client.isPreferential)
        val parameters = arrayOf(client.id.toString())
        val resultUpdate = dbWrite.update(
            "CLIENT",
            valuesToUpdate,
            "id = ?",
            parameters
        )
        dbWrite.close()
        return resultUpdate.toInt() !== -1
    }

    fun delete(id: Int): Boolean{
        val dbWrite = writableDatabase
        val parameters = arrayOf(id.toString())
        val resultDelete = dbWrite.delete(
            "CLIENT",
            "id = ?",
            parameters
        )
        dbWrite.close()
        return resultDelete.toInt() !== -1
    }
}