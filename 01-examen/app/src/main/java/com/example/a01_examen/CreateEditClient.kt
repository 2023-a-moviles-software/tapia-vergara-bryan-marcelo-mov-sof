package com.example.a01_examen

import android.content.Intent
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
        val residence = findViewById<EditText>(R.id.txt_client_id_card)
        val preferential = findViewById<CheckBox>(R.id.cb_client_preferential)
        val buttonCreateEditClient = findViewById<Button>(R.id.btn_create_edit_client)

        if (create){
            buttonCreateEditClient.setOnClickListener {
                val client = Client()
                client.setName(name.text.toString())
                client.setIdentificationCard(idCard.text.toString())
                client.setPhone(phone.text.toString())
                client.setResidence(residence.text.toString())
                client.setIsPreferential(preferential.isChecked)

                ClientDAO.getInstance().create(client)
                finish()
            }
        }else{
            tittle.setText("Editar ")
            buttonCreateEditClient.setText("Actualizar")
        }
    }
}