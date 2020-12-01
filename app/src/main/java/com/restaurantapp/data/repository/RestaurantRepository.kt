package com.restaurantapp.data.repository

import com.restaurantapp.data.RestaurantDao
import com.restaurantapp.data.model.Restaurant

class RestaurantRepository(private val restaurantDao: RestaurantDao) {
    val readAllData = restaurantDao.readAllData()

    suspend fun addRestaurant(restaurant: Restaurant) {
        restaurantDao.addRestaurant(restaurant)
    }
}
