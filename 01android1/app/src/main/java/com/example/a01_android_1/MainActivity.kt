package com.example.a01_android_1

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    val callbackContenidoIntentExplicito = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        result ->
        if(result.resultCode == Activity.RESULT_OK){
            if(result.data != null){
                //Lógica negocio
                val data = result.data
                "${data?.getStringExtra("nombreModificado")}"
            }
        }
    }

    val callbackIntentPickUri = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
            result ->
        if(result.resultCode === RESULT_OK){
            if(result.data != null){
                if(result.data!!.data != null){
                    val uri: Uri = result.data!!.data!!
                    val cursor = contentResolver.query(uri, null, null, null, null)
                    cursor?.moveToFirst()
                    val indiceTelefono = cursor?.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                    )
                    val telefono = cursor?.getString(indiceTelefono!!)
                    cursor?.close()
                    "Telefono ${telefono}"
                }
            }
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Base de datos sqlite
        //Main Activity
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)

        val botonCicloVida = findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonCicloVida.setOnClickListener {
            irActividad(AACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(
            R.id.btn_ir_list_view
        )
        botonListView.setOnClickListener {
            irActividad(BListView::class.java)
        }

        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito.setOnClickListener {
            abrirActividadConParametros(CIntentExplicitoParametros::class.java)
        }

        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite.setOnClickListener {
            irActividad(ECrudEntrenador::class.java)
        }

        val botonRView = findViewById<Button>(R.id.btn_recycler_view)
        botonRView.setOnClickListener {
            irActividad(FRecyclerView::class.java)
        }

        val botonGoogleMaps = findViewById<Button>(R.id.btn_google_maps)
        botonGoogleMaps.setOnClickListener {
            irActividad(GGoogleMaps::class.java)
        }

        val botonUIAuth = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonUIAuth.setOnClickListener {
            irActividad(HFirebaseUIAuth::class.java)
        }

        val botonFirestore = findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore.setOnClickListener{
            irActividad(IFirestore::class.java)
        }
    }

    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this, clase)
        //No recibimos respuesta
        startActivity(intent)
        // this.startActivity()
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentExplicito = Intent(this, clase)
        //Enviar parámetros
        //Recibimos primitivos
        intentExplicito.putExtra("nombre", "Bryan")
        intentExplicito.putExtra("apellido", "Tapia")
        intentExplicito.putExtra("edad", 21)
        intentExplicito.putExtra("entrenador", BEntrenador(1, "Bryan", "Tapia"))
        //Enviamos el intent con RESPUESTA
        //RECIBIMOS RESPUESTA
        callbackContenidoIntentExplicito.launch(intentExplicito)


    }
}