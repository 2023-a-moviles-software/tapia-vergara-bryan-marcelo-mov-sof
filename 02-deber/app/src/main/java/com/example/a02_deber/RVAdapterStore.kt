package com.example.a02_deber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a02_deber.models.Store

class RVAdapterStore(
    private val context: MainActivity,
    private val listaStore: List<Store>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<RVAdapterStore.MyViewHolder>()  {
    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val storeTextView: TextView
        val logo: ImageView
        val time: TextView
        init {
            storeTextView = view.findViewById(R.id.tv_name_store)
            logo = view.findViewById(R.id.iv_logo_store)
            time = view.findViewById(R.id.tv_time_store)
        }
    }
    //Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.recycler_view_store,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }
    //Setear datos iteración al iniciar el adaptador
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeActual = this.listaStore[position]
        holder.storeTextView.text = storeActual.name
        holder.logo.setImageResource(storeActual.logo)
        holder.time.text = "${storeActual.minTime}-${storeActual.maxTime} min"
    }
    //Tamaño del arreglo
    override fun getItemCount(): Int {
        return this.listaStore.size
    }
}