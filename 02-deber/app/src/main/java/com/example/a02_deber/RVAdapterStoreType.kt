package com.example.a02_deber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a02_deber.models.StoreType

class RVAdapterStoreType (
    private val context: MainActivity,
    private val listaStoreType: List<StoreType>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<RVAdapterStoreType.MyViewHolder>() {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val nameTextView: TextView
        val imageView: ImageView

        init {
            nameTextView = view.findViewById(R.id.tv_store_type)
            imageView = view.findViewById(R.id.iv_image_store_type)
        }
    }
    //Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.recycler_view_store_types,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }
    //Setear datos iteración al iniciar el adaptador
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeTypeActual = this.listaStoreType[position]
        holder.nameTextView.text = storeTypeActual.name
        holder.imageView.setImageResource(storeTypeActual.image)
    }
    //Tamaño del arreglo
    override fun getItemCount(): Int {
        return this.listaStoreType.size
    }
}