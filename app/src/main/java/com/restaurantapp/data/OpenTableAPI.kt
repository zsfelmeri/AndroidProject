package com.restaurantapp.data

import retrofit2.Call
import com.restaurantapp.data.model.Cities
import com.restaurantapp.data.model.ResponseData
import com.restaurantapp.data.model.Restaurant
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface OpenTableAPI {
    @GET("restaurants?")
    fun getRestaurantByCity(@Query("city") city: String?): Call<ResponseData>

    @GET("restaurants/city=Chicago")
    fun getRestaurantsInChicago(): Call<ResponseData>

    @GET("cities")
    fun getCities(): Call<Cities>

    @GET("restaurants/{id}")
    fun getRestaurantById(@Path("id") id: Int?): Call<Restaurant>
}