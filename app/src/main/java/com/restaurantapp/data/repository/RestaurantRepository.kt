package com.restaurantapp.data.repository

import androidx.lifecycle.LiveData
import com.restaurantapp.data.RestaurantDao
import com.restaurantapp.data.model.Restaurant

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    val readAllData = restaurantDao.readAllData()

    suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }

    fun readRestaurantsByCity(city: String): LiveData<List<Restaurant>>{
        return restaurantDao.readRestaurantsByCity(city)
    }

    suspend fun updateFavorites(restaurant: Restaurant){
        restaurantDao.updateFavorites(restaurant)
    }

    fun getFavoriteById(id: Int): Boolean{
        return restaurantDao.getFavoriteById(id)
    }
}
