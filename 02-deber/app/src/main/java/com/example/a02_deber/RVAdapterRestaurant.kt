package com.example.a02_deber

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a02_deber.models.Restaurant

class RVAdapterRestaurant(
    private val context: MainActivity,
    private val listaRestaurant: List<Restaurant>,
    private val recyclerView: RecyclerView
): RecyclerView.Adapter<RVAdapterRestaurant.MyViewHolder>()  {

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view){
        val restaurantTextView: TextView
        val imageView: ImageView
        val logo: ImageView
        val rating: TextView
        val timeDelivery: TextView
        init {
            restaurantTextView = view.findViewById(R.id.tv_name_restaurant)
            imageView = view.findViewById(R.id.iv_image_restaurant)
            logo = view.findViewById(R.id.iv_logo)
            rating = view.findViewById(R.id.tv_rating)
            timeDelivery = view.findViewById(R.id.tv_time_delivery)
        }
    }
    //Setear el layout que vamos a utilizar
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.recycler_view_restaurants,
                parent,
                false
            )
        return MyViewHolder(itemView)
    }
    //Setear datos iteración al iniciar el adaptador
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurantActual = this.listaRestaurant[position]
        holder.restaurantTextView.text = restaurantActual.name
        holder.imageView.setImageResource(restaurantActual.image)
        holder.logo.setImageResource(restaurantActual.logo)
        holder.rating.text = restaurantActual.rating.toString()
        holder.timeDelivery.text = "${restaurantActual.minTime}-${restaurantActual.maxTime} min  ∙  Envío $ ${restaurantActual.delivery}"
    }
    //Tamaño del arreglo
    override fun getItemCount(): Int {
        return this.listaRestaurant.size
    }
}