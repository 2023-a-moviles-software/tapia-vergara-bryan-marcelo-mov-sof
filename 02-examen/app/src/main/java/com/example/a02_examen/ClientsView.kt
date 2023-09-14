package com.example.a02_examen

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.a02_examen.database.Database
import com.example.a02_examen.models.Client
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ClientsView : AppCompatActivity() {
    var idItemSelected = 0
    //val listClients = ArrayList<Client>()

    lateinit var listViewClients: ListView
    lateinit var adapter : ArrayAdapter<Client>

    val callback = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                //Lógica negocio
                val data = result.data
                showSnackbar("${data?.getStringExtra("message")}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clients_view)

        //Create
        val buttonCreateClient = findViewById<Button>(R.id.btn_create_client)
        buttonCreateClient.setOnClickListener {
            openActivityCreateEdit(CreateEditClient::class.java, true)
        }

        //Adapter for Clients ListView
        listViewClients = findViewById<ListView>(R.id.lv_clients)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            Database.listClients
        )
        listViewClients.adapter = adapter
        adapter.notifyDataSetChanged()

        registerForContextMenu(listViewClients)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_client, menu)
        //Get ID of selected client
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSelected = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_client_edit ->{
                openActivityCreateEdit(CreateEditClient::class.java, false)
                return true
            }
            R.id.mi_client_delete ->{
                deleteDialog()
                return true
            }
            R.id.mi_client_payments ->{
                openActivityPayments(PaymentsView::class.java)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        getAll()
    }

    fun openActivityCreateEdit(
        classToOpen: Class<*>,
        create: Boolean
    ){
        val intent = Intent(this, classToOpen)
        intent.putExtra("create", create)
        intent.putExtra("idItemSelected", idItemSelected)
        callback.launch(intent)
        //startActivity(intent)
    }

    fun openActivityPayments(
        classToOpen: Class<*>
    ){
        val intent = Intent(this, classToOpen)

        //val client = Database.listClients[idItemSelected]
        intent.putExtra("idItemSelected", idItemSelected)
        startActivity(intent)
    }

    fun deleteDialog(){
        val builder = AlertDialog.Builder(this)
        val client = Database.listClients[idItemSelected]
        builder.setTitle("¿Desea eliminar el cliente ${client.name}?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val client = Database.listClients.removeAt(idItemSelected)
            adapter.notifyDataSetChanged()
            delete(client.id!!)
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog = builder.create()
        dialog.show()
    }

    fun showSnackbar(text: String){
        Snackbar.make(findViewById(R.id.cl_clients),text, Snackbar.LENGTH_LONG)
            .setAction("Action",null).show()
    }

    fun getAll(){
        val db = Firebase.firestore
        val clients = db.collection("clients")
        Database.listClients.clear()
        clients.get()
            .addOnSuccessListener { result ->
                for (queryDocumentSnapshot in result){
                    val client = Client()
                    client.id = queryDocumentSnapshot.data["id"].toString().toInt()
                    client.name = queryDocumentSnapshot.data["name"].toString()
                    client.identificationCard = queryDocumentSnapshot.data["identificationCard"].toString()
                    client.phone = queryDocumentSnapshot.data["phone"].toString()
                    client.residence = queryDocumentSnapshot.data["residence"].toString()
                    client.isPreferential = queryDocumentSnapshot.data["isPreferential"].toString().toBoolean()
                    Database.listClients.add(client)
                }
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener {  }
    }

    fun delete(id: Int){
        val db = Firebase.firestore
        val clients = db.collection("clients")
        clients.document(id.toString())
            .delete()
            .addOnSuccessListener {
                showSnackbar("Cliente eliminado")
            }
            .addOnFailureListener {
                showSnackbar("Error al eliminar el cliente")
            }
    }
}