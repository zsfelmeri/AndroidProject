package com.restaurantapp.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table", primaryKeys = ["email", "username"])
data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val phoneNumber: String,
    val password: String
): Parcelable