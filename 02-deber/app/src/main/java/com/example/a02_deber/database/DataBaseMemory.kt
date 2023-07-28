package com.example.a02_deber.database

import com.example.a02_deber.R
import com.example.a02_deber.models.Restaurant
import com.example.a02_deber.models.StoreType

class DataBaseMemory {
    companion object {
        val arregloStoreType = arrayListOf<StoreType>(
            StoreType("Gas", R.drawable.img_gas),
            StoreType("Farmacia", R.drawable.img_farmacia),
            //StoreType("Mensajería", R.drawable.img_basket),
            StoreType("Mascotas", R.drawable.img_mascota),
            StoreType("Tiendas", R.drawable.img_tiendas)
        )

        val arregloMostRequested = arrayListOf<Restaurant>(
            Restaurant("KFC - Sta. María Sangolquí",4.5, 30, 45, 0.79, R.drawable.logo_pedidosya, R.drawable.img_kfc),
            Restaurant("El Hornero - San Pedro",4.5, 30, 45, 1.69, R.drawable.logo_pedidosya, R.drawable.img_kfc),
            Restaurant("Los Pollos De San Pedro",4.3, 30, 45, 0.39, R.drawable.logo_pedidosya, R.drawable.img_kfc),
            Restaurant("Pikazzo Pizzería",4.2, 30, 45, 1.69, R.drawable.logo_pedidosya, R.drawable.img_kfc),
            Restaurant("Texas Chicken",4.3, 30, 45, 0.0, R.drawable.logo_pedidosya, R.drawable.img_kfc),
        )
    }
}