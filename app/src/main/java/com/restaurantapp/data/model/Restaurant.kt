package com.restaurantapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurant_table")
data class Restaurant(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val address: String,
        val phone: String
        //...
)