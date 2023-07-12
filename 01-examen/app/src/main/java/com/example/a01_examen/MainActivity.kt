package com.example.a01_examen

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
import com.example.a01_examen.DataBase.DataBaseMemory

class MainActivity : AppCompatActivity() {
    val clients = DataBaseMemory.clients
    var idItemSelected = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Create
        val buttonCreateClient = findViewById<Button>(R.id.btn_create_client)
        buttonCreateClient.setOnClickListener {
            openActivityCreateEdit(CreateEditClient::class.java, true)
        }

        //Adapter for Clients ListView
        val listViewClients = findViewById<ListView>(R.id.lv_clients)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            clients
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
        inflater.inflate(R.menu.menu, menu)
        //Obtener el id del ArrayList seleccionado
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSelected = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_edit ->{
                "Hacer algo con ${idItemSelected}"
                openActivityCreateEdit(CreateEditClient::class.java, false, idItemSelected)
                return true
            }
            R.id.mi_delete ->{
                "Hacer algo con ${idItemSelected}"
                return true
            }
            R.id.mi_payments ->{
                "Hacer algo con ${idItemSelected}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun openActivity(
        classToOpen: Class<*>
    ){
        val intent = Intent(this, classToOpen)
        startActivity(intent)
    }

    fun openActivityCreateEdit(
        classToOpen: Class<*>,
        create: Boolean,
        idSelected: Int = 0
    ){
        val intent = Intent(this, classToOpen)
        intent.putExtra("create", create)
        startActivity(intent)
    }
}