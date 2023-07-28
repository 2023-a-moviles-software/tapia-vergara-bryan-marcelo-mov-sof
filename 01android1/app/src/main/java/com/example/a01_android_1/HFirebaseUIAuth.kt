package com.example.a01_android_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

class HFirebaseUIAuth : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)
    }
    //Callback del intent de login
    private val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ){res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK){
            if (res.idpResponse != null){
                //Lógica de negocio
                selogeo(res.idpResponse!!)
            }
        }
    }

    fun selogeo(
        res: IdpResponse
    ){
        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.visibility = Button.VISIBLE
        btnLogin.visibility = Button.INVISIBLE
        if (res.isNewUser == true) {
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun registrarUsuarioPorPrimeraVez(res: IdpResponse){/*usuario.email; usuario.phoneNumber; usuario.user.name;*/}
}