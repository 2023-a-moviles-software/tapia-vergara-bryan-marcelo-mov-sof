package com.example.a03_deber

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.a03_deber.dao.PaymentDAO
import com.example.a03_deber.database.DataBase
import com.example.a03_deber.models.Payment
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.util.Calendar

class CreateEditPayment : AppCompatActivity() {
    private var datePickerDialog: DatePickerDialog? = null
    lateinit var date: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_payment)

        val create = intent.getBooleanExtra("create", true)
        val idClient = intent.getIntExtra("idClient", 0)

        val title = findViewById<TextView>(R.id.tv_payment_title)
        val month = findViewById<Spinner>(R.id.sp_payment_month)
        date = findViewById<Button>(R.id.btn_payment_date)
        initDatePicker()
        date.text = LocalDate.now().toString()

        val amount = findViewById<EditText>(R.id.txt_payment_amount)
        val inCash = findViewById<CheckBox>(R.id.cb_payment_cash)
        val isLate = findViewById<CheckBox>(R.id.cb_payment_late)
        val buttonCreateEditPayment = findViewById<Button>(R.id.btn_payment_create_edit)

        if (create) {
            buttonCreateEditPayment.setOnClickListener {
                val payment = Payment()

                payment.month = month.selectedItem.toString()
                payment.date = LocalDate.parse(date.text.toString())

                payment.inCash = inCash.isChecked
                payment.isLate = isLate.isChecked
                if(amount.text.toString() == ""){
                    //payment.amount = 0.0
                    showSnackbar("Debe ingresar un monto")
                }else{
                    payment.amount = amount.text.toString().toDouble()
                    val created = DataBase.tablePayment!!.create(payment, idClient)
                    if (created){
                        returnMessage("Pago creado")
                    }else{
                        returnMessage("Error al crear el pago")
                    }
                }
            }
        }else{
            val payment = DataBase.tablePayment!!.getAllByClient(idClient)[intent.getIntExtra("idItemSelected", 0)]
            title.text = "Editar el pago del mes: ${payment.month}"
            buttonCreateEditPayment.text = "Actualizar"
            month.setSelection(resources.getStringArray(R.array.months).indexOf(payment.month))
            date.text = payment.date.toString()
            amount.setText(payment.amount.toString())
            inCash.isChecked = payment.inCash
            isLate.isChecked = payment.isLate

            buttonCreateEditPayment.setOnClickListener {
                payment.month = month.selectedItem.toString()
                payment.date = LocalDate.parse(date.text.toString())
                payment.amount = amount.text.toString().toDouble()
                payment.inCash = inCash.isChecked
                payment.isLate = isLate.isChecked

                val updated = DataBase.tablePayment!!.update(payment)
                if (updated){
                    returnMessage("Pago actualizado")
                }else{
                    returnMessage("Error al actualizar el pago")
                }
            }
        }
    }

    private fun initDatePicker() {
        val dateSetListener: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener {
                datePicker, year, month, dayOfMonth ->
            val date:String = makeDateString(dayOfMonth,month,year)
            this.date.text = date
        }
        val cal: Calendar = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)

        val style = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(this,style,dateSetListener,year,month,day)
    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return "" + year + "-" +
                month.plus(1).toString().padStart(2,'0') + "-" +
                day.toString().padStart(2,'0')
    }

    fun openDatePicker(view: View?) {
        datePickerDialog!!.show()
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

    fun showSnackbar(text: String){
        Snackbar.make(findViewById(R.id.cl_payment_create_edit),text, Snackbar.LENGTH_LONG)
            .setAction("Action",null).show()
    }
}