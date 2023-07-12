package com.example.a01_examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.a01_examen.dao.ClientDAO
import com.example.a01_examen.models.Client

class CreateEditClient : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_client)

        val create:Boolean = intent.getBooleanExtra("create", true)

        val tittle = findViewById<TextView>(R.id.txt_client_tittle)
        val name = findViewById<EditText>(R.id.txt_client_name)
        val idCard = findViewById<EditText>(R.id.txt_client_id_card)
        val phone = findViewById<EditText>(R.id.txt_client_phone)
        val residence = findViewById<EditText>(R.id.txt_client_residence)
        val preferential = findViewById<CheckBox>(R.id.cb_client_preferential)
        val buttonCreateEditClient = findViewById<Button>(R.id.btn_create_edit_client)

        if (create){
            buttonCreateEditClient.setOnClickListener {
                val client = Client()
                client.name = name.text.toString()
                client.identificationCard = idCard.text.toString()
                client.phone = phone.text.toString()
                client.residence = residence.text.toString()
                client.isPreferential = preferential.isChecked

                ClientDAO.getInstance().create(client)
                finish()
            }
        }else{
            val client = ClientDAO.getInstance().getAll()[intent.getIntExtra("idItemSelected", 0)]
            tittle.text = "Editar el cliente: ${client.name}"
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

                ClientDAO.getInstance().update(client)
                finish()
            }
        }
    }
}