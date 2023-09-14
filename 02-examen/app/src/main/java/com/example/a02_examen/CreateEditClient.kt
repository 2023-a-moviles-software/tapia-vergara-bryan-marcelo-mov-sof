package com.example.a02_examen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.a02_examen.database.Database
import com.example.a02_examen.models.Client
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CreateEditClient : AppCompatActivity() {

    var create:Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_client)

        create = intent.getBooleanExtra("create", true)

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
                client.id = (System.currentTimeMillis() % 10000).toInt()
                client.name = name.text.toString()
                client.identificationCard = idCard.text.toString()
                client.phone = phone.text.toString()
                client.residence = residence.text.toString()
                client.isPreferential = preferential.isChecked

                Database.listClients.add(client)
                createOrUpdate(client)
            }
        }else{
            val client = Database.listClients[intent.getIntExtra("idItemSelected", 0)]
            buttonCreateEditClient.text = "Actualizar"

            title.text = "Editar el cliente: ${client.name}"
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

                Database.listClients[intent.getIntExtra("idItemSelected", 0)] = client
                createOrUpdate(client)

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

    fun createOrUpdate(client: Client){
        val db = Firebase.firestore
        val clients = db.collection("clients")

        val data = hashMapOf(
            "id" to client.id,
            "identificationCard" to client.identificationCard,
            "name" to client.name,
            "phone" to client.phone,
            "residence" to client.residence,
            "isPreferential" to client.isPreferential
        )
        clients.document(client.id.toString())
            .set(data)
            .addOnSuccessListener {
                if (create){
                    returnMessage("Cliente creado")
                }else{
                    returnMessage("Cliente actualizado")
                }
            }
            .addOnFailureListener {
                if (create){
                    returnMessage("Error al crear el cliente")
                }else{
                    returnMessage("Error al actualizar el cliente")
                }
            }
    }
}