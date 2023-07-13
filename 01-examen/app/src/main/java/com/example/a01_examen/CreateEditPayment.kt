package com.example.a01_examen

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.example.a01_examen.dao.PaymentDAO
import com.example.a01_examen.models.Payment
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

class CreateEditPayment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_edit_payment)

        val create = intent.getBooleanExtra("create", true)
        val idClient = intent.getIntExtra("idClient", 0)

        val title = findViewById<TextView>(R.id.tv_payment_title)
        val month = findViewById<Spinner>(R.id.sp_payment_month)
        val date = findViewById<EditText>(R.id.txt_payment_date)
        val amount = findViewById<EditText>(R.id.txt_payment_amount)
        val inCash = findViewById<CheckBox>(R.id.cb_payment_cash)
        val isLate = findViewById<CheckBox>(R.id.cb_payment_late)
        val buttonCreateEditPayment = findViewById<Button>(R.id.btn_payment_create_edit)

        if (create) {
            buttonCreateEditPayment.setOnClickListener {
                val payment = Payment()
                payment.month = month.selectedItem.toString()
                payment.date = LocalDate.parse(date.text.toString())
                payment.amount = amount.text.toString().toDouble()
                payment.inCash = inCash.isChecked
                payment.isLate = isLate.isChecked

                PaymentDAO.getInstance().create(payment, idClient)
                finish()
            }
        }else{
            val payment = PaymentDAO.getInstance().getAllByClient(idClient)[intent.getIntExtra("idItemSelected", 0)]
            title.text = "Editar el pago del mes: ${payment.month}"
            buttonCreateEditPayment.text = "Actualizar"
            month.setSelection(resources.getStringArray(R.array.months).indexOf(payment.month))
            date.setText(payment.date.toString())
            amount.setText(payment.amount.toString())
            inCash.isChecked = payment.inCash
            isLate.isChecked = payment.isLate

            buttonCreateEditPayment.setOnClickListener {
                payment.month = month.selectedItem.toString()
                payment.date = LocalDate.parse(date.text.toString())
                payment.amount = amount.text.toString().toDouble()
                payment.inCash = inCash.isChecked
                payment.isLate = isLate.isChecked

                PaymentDAO.getInstance().update(payment, idClient)
                finish()
            }
        }
    }
}