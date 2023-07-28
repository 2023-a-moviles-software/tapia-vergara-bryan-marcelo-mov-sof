package com.example.a02_deber

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a02_deber.database.DataBaseMemory
import com.example.a02_deber.models.Restaurant


class MainActivity : AppCompatActivity() {
    val arregloStoreType = DataBaseMemory.arregloStoreType
    val arregloRestaurants = DataBaseMemory.arregloMostRequested
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRVStoreType()
        initRVRestaurant(R.id.rv_most_requested, arregloRestaurants)

    }

    fun initRVStoreType(){
        val recyclerView = findViewById<RecyclerView>(R.id.rv_store_type)
        val adapter = RVAdapterStoreType(
            this,
            arregloStoreType,
            recyclerView
        )
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter.notifyDataSetChanged()
    }

    fun initRVRestaurant(reference: Int, arreglo: List<Restaurant>){
        val recyclerView = findViewById<RecyclerView>(reference)
        val adapter = RVAdapterRestaurant(
            this,
            arreglo,
            recyclerView
        )
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter.notifyDataSetChanged()
    }
}