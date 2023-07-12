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
import androidx.appcompat.app.AlertDialog
import com.example.a01_examen.dao.ClientDAO
import com.example.a01_examen.dao.PaymentDAO
import com.example.a01_examen.models.Payment

class PaymentsView : AppCompatActivity() {
    var idItemSelected = 0
    var idClient = 0

    lateinit var listViewPayments: ListView
    lateinit var adapter : ArrayAdapter<Payment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payments_view)

        val client = ClientDAO.getInstance().getAll()[intent.getIntExtra("idItemSelected", 0)]
        idClient = client.id!!

        val payments = PaymentDAO.getInstance().getAllByClient(idClient)

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
            payments
        )
        listViewPayments.adapter = adapter
        adapter.notifyDataSetChanged()

        registerForContextMenu(listViewPayments)
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_client, menu)
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
        startActivity(intent)
    }

    fun deleteDialog(){
        val builder = AlertDialog.Builder(this)
        val payment = PaymentDAO.getInstance().getAllByClient(idClient)[idItemSelected]
        builder.setTitle("¿Desea eliminar el pago ${payment.id}?")
        builder.setPositiveButton("Aceptar") { dialog, which ->
            PaymentDAO.getInstance().delete(payment.id!!, idClient)
            adapter.notifyDataSetChanged()
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog = builder.create()
        dialog.show()
    }
}