package com.example.a02_deber.database

import com.example.a02_deber.R
import com.example.a02_deber.models.Restaurant
import com.example.a02_deber.models.StoreType

class DataBaseMemory {
    companion object {
        val arregloStoreType = arrayListOf<StoreType>(
            StoreType("Gas", R.drawable.logo_pedidosya_48px),
            StoreType("Farmacia", R.drawable.logo_pedidosya_48px),
            StoreType("Mensajería", R.drawable.logo_pedidosya_48px),
            StoreType("Mascotas", R.drawable.logo_pedidosya_48px),
            StoreType("Tiendas", R.drawable.logo_pedidosya_48px)
        )

        val arregloMostRequested = arrayListOf<Restaurant>(
            Restaurant("KFC - Sta. María Sangolquí",4.5, 30, 45, 0.79, R.drawable.logo_pedidosya_48px, R.drawable.kfc),
            Restaurant("El Hornero - San Pedro",4.5, 30, 45, 1.69, R.drawable.logo_pedidosya_48px, R.drawable.kfc),
            Restaurant("Los Pollos De San Pedro",4.3, 30, 45, 0.39, R.drawable.logo_pedidosya_48px, R.drawable.kfc),
            Restaurant("Pikazzo Pizzería",4.2, 30, 45, 1.69, R.drawable.logo_pedidosya_48px, R.drawable.kfc),
            Restaurant("Texas Chicken",4.3, 30, 45, 0.0, R.drawable.logo_pedidosya_48px, R.drawable.kfc),
        )
    }
}