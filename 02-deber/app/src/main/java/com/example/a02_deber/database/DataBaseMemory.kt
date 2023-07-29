package com.example.a02_deber.database

import com.example.a02_deber.R
import com.example.a02_deber.models.Restaurant
import com.example.a02_deber.models.Store
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

        val arregloBestRestaurants = arrayListOf<Restaurant>(
            Restaurant("Los HotDogs de la González...",4.5, 30, 45, 0.79, R.drawable.logo_hotdogs, R.drawable.img_hotdogs),
            Restaurant("KFC",4.5, 30, 45, 0.79, R.drawable.logo_kfc, R.drawable.img_kfc),
            Restaurant("Los Pollos De San Bartolo",4.3, 30, 45, 0.39, R.drawable.img_polloalhorno, R.drawable.img_polloalhorno),
            Restaurant("Texas Chicken",4.3, 30, 45, 0.0, R.drawable.logo_texaschicken, R.drawable.img_texaschicken),
        )

        val arregloMostRequested = arrayListOf<Restaurant>(
            Restaurant("KFC - Sta. María Sangolquí",4.5, 30, 45, 0.79, R.drawable.logo_kfc, R.drawable.img_kfc),
            Restaurant("El Hornero - San Pedro",4.5, 30, 45, 1.69, R.drawable.logo_elhornero, R.drawable.img_elhornero),
            Restaurant("Los Pollos De San Pedro",4.3, 30, 45, 0.39, R.drawable.img_polloalhorno, R.drawable.img_polloalhorno),
            Restaurant("Pikazzo Pizzería",4.2, 30, 45, 1.69, R.drawable.img_pizza, R.drawable.img_pizza),
            Restaurant("Texas Chicken",4.3, 30, 45, 0.0, R.drawable.logo_texaschicken, R.drawable.img_texaschicken),
        )

        val arregloPharmacy = arrayListOf<Store>(
            Store(R.drawable.logo_fybeca, "Fybeca - San Rafael", 25, 40),
            Store(R.drawable.logo_medicity, "Farmacias Medicity", 30, 45),
            Store(R.drawable.logo_farmaciaseconomicas, "Farmacias Económicas", 15, 30),
        )
    }
}