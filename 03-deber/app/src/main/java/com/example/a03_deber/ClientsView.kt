package com.example.a03_deber

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
import com.example.a03_deber.dao.ClientDAO
import com.example.a03_deber.dao.PaymentDAO
import com.example.a03_deber.database.DataBase
import com.example.a03_deber.models.Client
import com.google.android.material.snackbar.Snackbar

class ClientsView : AppCompatActivity() {
    var idItemSelected = 0

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
        setContentView(R.layout.activity_main)

        //Base de datos sqlite
        DataBase.tableClient = ClientDAO(this)
        DataBase.tablePayment = PaymentDAO(this)

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
            DataBase.tableClient!!.getAll()
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
        adapter.clear()
        adapter.addAll(DataBase.tableClient!!.getAll())
        adapter.notifyDataSetChanged()
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
        intent.putExtra("idItemSelected", idItemSelected)
        startActivity(intent)
    }

    fun deleteDialog(){
        val builder = AlertDialog.Builder(this)
        val client = DataBase.tableClient!!.getAll()[idItemSelected]
        //val client = ClientDAO.getInstance().getAll()[idItemSelected]
        builder.setTitle("¿Desea eliminar el cliente ${client.name}?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val deleted = DataBase.tableClient!!.delete(client.id!!)
            if(deleted){
                showSnackbar("Cliente eliminado")
            }else{
                showSnackbar("Error al eliminar el cliente")
            }
            onResume()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog = builder.create()
        dialog.show()
    }

    fun showSnackbar(text: String){
        Snackbar.make(findViewById(R.id.cl_clients),text, Snackbar.LENGTH_LONG)
            .setAction("Action",null).show()
    }
}