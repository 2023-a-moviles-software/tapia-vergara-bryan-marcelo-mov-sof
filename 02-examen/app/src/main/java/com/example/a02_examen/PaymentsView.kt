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
import com.example.a03_deber.database.DataBase
import com.example.a03_deber.models.Payment
import com.google.android.material.snackbar.Snackbar

class PaymentsView : AppCompatActivity() {
    var idItemSelected = 0
    var idClient = 0

    lateinit var listViewPayments: ListView
    lateinit var adapter : ArrayAdapter<Payment>

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
        setContentView(R.layout.activity_payments_view)

        val client = DataBase.tableClient!!.getAll()[intent.getIntExtra("idItemSelected", 0)]
        idClient = client.id!!

        //val payments = DataBase.tablePayment!!.getAllByClient(idClient)

        //Create
        val buttonCreatePayment = findViewById<Button>(R.id.btn_create_payment)
        buttonCreatePayment.setOnClickListener {
            openActivityCreateEdit(CreateEditPayment::class.java, true)
        }

        //Adapter for Clients ListView
        listViewPayments = findViewById<ListView>(R.id.lv_payments)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            DataBase.tablePayment!!.getAllByClient(idClient)
        )
        listViewPayments.adapter = adapter
        adapter.notifyDataSetChanged()

        registerForContextMenu(listViewPayments)
    }

    override fun onResume() {
        super.onResume()
        adapter.clear()
        adapter.addAll(DataBase.tablePayment!!.getAllByClient(idClient))
        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_payment, menu)
        //Get ID of selected payment
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        idItemSelected = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_payment_edit ->{
                openActivityCreateEdit(CreateEditPayment::class.java, false)
                return true
            }
            R.id.mi_payment_delete ->{
                deleteDialog()
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    fun openActivityCreateEdit(
        classToOpen: Class<*>,
        create: Boolean
    ){
        val intent = Intent(this, classToOpen)
        intent.putExtra("create", create)
        intent.putExtra("idItemSelected", idItemSelected)
        intent.putExtra("idClient", idClient)
        callback.launch(intent)
        //startActivity(intent)
    }

    fun deleteDialog(){
        val builder = AlertDialog.Builder(this)
        val payment = DataBase.tablePayment!!.getAllByClient(idClient)[idItemSelected]
        builder.setTitle("¿Desea eliminar el pago ${payment.id}?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            val deleted = DataBase.tablePayment!!.delete(payment.id!!)
            if (deleted){
                showSnackbar("Pago eliminado")
            }else{
                showSnackbar("Error al eliminar el pago")
            }
            onResume()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog = builder.create()
        dialog.show()
    }

    fun showSnackbar(text: String){
        Snackbar.make(findViewById(R.id.cl_payments),text, Snackbar.LENGTH_LONG)
            .setAction("Action",null).show()
    }
}