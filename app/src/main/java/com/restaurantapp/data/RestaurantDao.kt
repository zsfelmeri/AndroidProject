package com.restaurantapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.restaurantapp.data.model.Restaurant

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM restaurant_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Restaurant>>

    @Query("SELECT * FROM restaurant_table WHERE city = :city")
    fun readRestaurantsByCity(city: String): LiveData<List<Restaurant>>

    @Update
    suspend fun updateFavorites(restaurant: Restaurant)

    @Query("SELECT favorite FROM restaurant_table WHERE id = :id")
    fun getFavoriteById(id: Int): Boolean
}