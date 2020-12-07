package com.restaurantapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.restaurantapp.data.RestaurantDatabase
import com.restaurantapp.data.model.Restaurant
import com.restaurantapp.data.repository.RestaurantRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantViewModel(application: Application): AndroidViewModel(application) {
    val readAllData: LiveData<List<Restaurant>>
    private val repository: RestaurantRepository
//    val filteredRestaurants: LiveData<List<Restaurant>>

    init {
        val restaurantDao = RestaurantDatabase.getDatabase(application).restaurantDao()
        repository = RestaurantRepository(restaurantDao)
        readAllData = repository.readAllData
//        filteredRestaurants = repository.readRestaurantsByCity()
    }

    fun addRestaurant(restaurant: Restaurant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addRestaurant(restaurant)
        }
    }

    fun readRestaurantsByCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.readRestaurantsByCity(city)
        }
    }

    fun updateFavorites(restaurant: Restaurant){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorites(restaurant)
        }
    }

    fun getFavoriteById(id: Int): Boolean {
        return repository.getFavoriteById(id)
    }
}