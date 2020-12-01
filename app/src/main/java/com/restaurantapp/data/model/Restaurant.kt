package com.restaurantapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "restaurant_table")
data class Restaurant(
        @PrimaryKey(autoGenerate = false)
        val id: Int,
        val address: String,
        val phone: String,
        val description: String
        //...
): Parcelable
