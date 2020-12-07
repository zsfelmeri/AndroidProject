package com.restaurantapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.restaurantapp.R
import com.restaurantapp.data.model.Restaurant
import com.restaurantapp.data.model.User
import com.restaurantapp.data.viewmodel.RestaurantViewModel
import kotlinx.android.synthetic.main.custom_row.view.*
import kotlin.coroutines.coroutineContext

class ListAdapter(val currentUser: User): RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var restaurantList = emptyList<Restaurant>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = restaurantList[position]
        holder.itemView.imageView.setImageResource(R.drawable.kane_5)
        holder.itemView.tv_address.text = currentItem.address
        holder.itemView.tv_phoneNum.text = currentItem.phone
        holder.itemView.tv_city.text = currentItem.city
        holder.itemView.tv_price.text = currentItem.price.toString()
        if (currentItem.favorite){
            holder.itemView.iv_favorite.setImageResource(R.drawable.filled_start)
        }
        else{
            holder.itemView.iv_favorite.setImageResource(R.drawable.empty_star)
        }

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToDescriptionFragment(currentItem, currentUser)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList.size
    }

    fun setData(restaurant: List<Restaurant>){
        this.restaurantList = restaurant
        notifyDataSetChanged()
    }
}