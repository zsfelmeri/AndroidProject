package com.restaurantapp.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.restaurantapp.R
import com.restaurantapp.data.model.Restaurant
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    private var restaurantList = emptyList<Restaurant>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = restaurantList[position]
        holder.itemView.imageView.setImageResource(R.drawable.food)
        holder.itemView.tv_address.text = currentItem.address
        holder.itemView.tv_phoneNum.text = currentItem.phone

        holder.itemView.rowLayout.setOnClickListener {
//            val action = ListFragmentDirections.actionListFragmentToDescriptionFragment(currentItem)
            holder.itemView.findNavController().navigate(R.id.action_listFragment_to_descriptionFragment)
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