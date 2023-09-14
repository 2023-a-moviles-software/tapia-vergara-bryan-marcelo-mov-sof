package com.example.a02_examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.a03_deber.database.DataBase
import com.example.a03_deber.models.Client

class CreateEditClient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_client)

        val create:Boolean = intent.getBooleanExtra("create", true)

        val title = findViewById<TextView>(R.id.tv_client_title)
        val name = findViewById<EditText>(R.id.txt_client_name)
        val idCard = findViewById<EditText>(R.id.txt_client_id_card)
        val phone = findViewById<EditText>(R.id.txt_client_phone)
        val residence = findViewById<EditText>(R.id.txt_client_residence)
        val preferential = findViewById<CheckBox>(R.id.cb_client_preferential)
        val buttonCreateEditClient = findViewById<Button>(R.id.btn_client_create_edit)

        if (create){
            buttonCreateEditClient.setOnClickListener {
                val client = Client()
                client.name = name.text.toString()
                client.identificationCard = idCard.text.toString()
                client.phone = phone.text.toString()
                client.residence = residence.text.toString()
                client.isPreferential = preferential.isChecked

                val created = DataBase.tableClient!!.create(client)
                if (created){
                    returnMessage("Cliente creado")
                }else{
                    returnMessage("Error al crear el cliente")
                }
            }
        }else{
            val client = DataBase.tableClient!!.getAll()[intent.getIntExtra("idItemSelected", 0)]
            title.text = "Editar el cliente: ${client.name}"
            buttonCreateEditClient.text = "Actualizar"
            name.setText(client.name)
            idCard.setText(client.identificationCard)
            phone.setText(client.phone)
            residence.setText(client.residence)
            preferential.isChecked = client.isPreferential

            buttonCreateEditClient.setOnClickListener {
                client.name = name.text.toString()
                client.identificationCard = idCard.text.toString()
                client.phone = phone.text.toString()
                client.residence = residence.text.toString()
                client.isPreferential = preferential.isChecked

                val updated = DataBase.tableClient!!.update(client)
                if(updated){
                    returnMessage("Cliente actualizado")
                }else{
                    returnMessage("Error al actualizar el cliente")
                }
            }
        }
    }

    fun returnMessage(message: String){
        val intent = Intent()
        intent.putExtra("message", message)
        setResult(
            RESULT_OK,
            intent
        )
        finish()
    }
}