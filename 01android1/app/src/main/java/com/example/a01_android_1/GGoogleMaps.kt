package com.example.a01_android_1

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions

class GGoogleMaps : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
        val boton = findViewById<Button>(R.id.btn_ir_carolina)
        boton.setOnClickListener {
            irCarolina()
        }
    }

    fun irCarolina(){
        val carolina = LatLng(-0.1825684318486696, -78.48447277600916)
        val zoom = 17f
        val tilt = 30f
        moverCamaraConZoom(carolina, zoom)
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync{ googleMap ->
            with(googleMap){  //googleMap != null
                mapa = googleMap
                establecerConfiguracionMapa()
                marcadorQuicentro()
                anadirPoliLinea()
                anadirPoligono()
                escucharListeners()
            }
        }
    }

    fun escucharListeners(){
        mapa.setOnPolygonClickListener {
            Log.i("mapa", "setOnPolygonClickListener ${it}")
            it.tag  //ID
        }
        mapa.setOnPolylineClickListener {
            Log.i("mapa", "setOnPolylineClickListener ${it}")
            it.tag  //ID
        }
        mapa.setOnMarkerClickListener {
            Log.i("mapa", "setOnMarkerClickListener ${it}")
            it.tag //ID
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            Log.i("mapa", "setOnCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
            Log.i("mapa", "setOnCameraMoveStartedListener ${it}")
        }
        mapa.setOnCameraIdleListener {
            Log.i("mapa", "setOnCameraIdleListener")
        }

    }

    fun anadirPoliLinea(){
        with(mapa){
            val poliLineaUno = mapa
                .addPolyline(
                    PolylineOptions().clickable(true)
                        .add(
                            LatLng(-0.17556788490271892, -78.48814901143776),
                            LatLng(-0.17556788490271892, -78.48814901143776),
                            LatLng(-0.17556788490271892, -78.48814901143776)
                        )
            )
            poliLineaUno.tag = "Linea-1" // <--ID
        }
    }

    fun anadirPoligono(){
        with(mapa){
            val poligonoUno = addPolygon(
                PolygonOptions().clickable(true)
                    .add(
                        LatLng(-0.17556788490271892, -78.48814901143776),
                        LatLng(-0.17556788490271892, -78.48814901143776),
                        LatLng(-0.17556788490271892, -78.48814901143776)
                    )
            )
            poligonoUno.fillColor = -0xc771c4
            poligonoUno.tag = "Poligono-2" // <--ID
        }
    }

    fun marcadorQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(-0.17556788490271892, -78.48814901143776)
        val titulo = "Quicentro"
        val markQuicentro = anadirMarcador(quicentro, titulo)
        markQuicentro.tag = titulo
        moverCamaraConZoom(quicentro)
    }

    fun anadirMarcador(latLng: LatLng, title: String): Marker {
        return mapa.addMarker(
            MarkerOptions()
                .position(latLng)
                .title(title)
        )!!
    }

    fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f){
        mapa.moveCamera(
            CameraUpdateFactory
                .newLatLngZoom(latLng, zoom)
        )
    }

    fun establecerConfiguracionMapa(){
        val contexto = this.applicationContext
        with(mapa){
            val permisosFineLocation = ContextCompat.checkSelfPermission(
                contexto,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true  //tenemos permisos
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisosFineLocation = ContextCompat.checkSelfPermission(
            contexto,
            //Permiso que van a revisar
            nombrePermisoFine
        )
        val tienePermisos = permisosFineLocation == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this,  //contexto
                arrayOf(    //Arreglo Permisos
                    nombrePermisoFine,
                    nombrePermisoCoarse
                ),
                1 //Código de petición de los permisos
            )
        }
    }


}