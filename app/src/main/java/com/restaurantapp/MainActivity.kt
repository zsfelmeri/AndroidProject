package com.restaurantapp

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.restaurantapp.data.OpenTableAPI
import com.restaurantapp.data.model.Cities
import com.restaurantapp.data.model.ResponseData
import com.restaurantapp.data.model.Restaurant
import com.restaurantapp.data.viewmodel.RestaurantViewModel
import com.restaurantapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    companion object {
        var CITIES: List<String> = mutableListOf("All", "Favorites")
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var mRestaurantViewModel: RestaurantViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)

        navController = findNavController(R.id.main_fragment)

        synchronized(this){
            getDataFromAPI()
        }
    }

    private fun getDataFromAPI(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentable.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val openTableAPI: OpenTableAPI = retrofit.create(OpenTableAPI::class.java)

//      get all restaurants from the api
        val myCallCities: Call<Cities> = openTableAPI.getCities()
        myCallCities.enqueue(object: Callback<Cities> {
            override fun onResponse(call: Call<Cities>, response: Response<Cities>) {
                val cities = response.body()
                if (cities != null) {
                    for(city in cities.cities){
                        CITIES = CITIES.plus(city)
                        val myCallRestaurants: Call<ResponseData> = openTableAPI.getRestaurantByCity(city)
                        myCallRestaurants.enqueue(object: Callback<ResponseData> {
                            override fun onResponse(
                                call: Call<ResponseData>,
                                response: Response<ResponseData>
                            ) {
                                val restaurants = response.body()
                                if (restaurants != null) {
                                    for(restaurant in restaurants.restaurants){
                                        if(checkNotNullParam(restaurant.name) && checkNotNullParam(restaurant.address) && checkNotNullParam(restaurant.city) && checkNotNullParam(restaurant.state) &&
                                                checkNotNullParam(restaurant.area) && checkNotNullParam(restaurant.postal_code) && checkNotNullParam(restaurant.country) &&
                                            checkNotNullParam(restaurant.phone) && checkNotNullParam(restaurant.reserve_url) && checkNotNullParam(restaurant.mobile_reserve_url) &&
                                            checkNotNullParam(restaurant.image_url)) {
                                            val data = Restaurant(
                                                restaurant.id,
                                                restaurant.name,
                                                restaurant.address,
                                                restaurant.city,
                                                restaurant.state,
                                                restaurant.area,
                                                restaurant.postal_code,
                                                restaurant.country,
                                                restaurant.phone,
                                                restaurant.lat,
                                                restaurant.lng,
                                                restaurant.price,
                                                restaurant.reserve_url,
                                                restaurant.mobile_reserve_url,
                                                restaurant.image_url,
                                                    false,
                                                    "ID: ${restaurant.id}\n Name: ${restaurant.name}\n Address: ${restaurant.address}\n City: ${restaurant.city}\n State: ${restaurant.state}\n Postal Code: ${restaurant.postal_code}\n Phone: ${restaurant.phone}\n Price: ${restaurant.price}\n"
                                            )
//                                            add restaurant to database
                                            mRestaurantViewModel.addRestaurant(data)
                                        }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                                Log.e("RESTAURANTERR", t.message.toString())
                            }
                        })
                    }
                }
            }

            override fun onFailure(call: Call<Cities>, t: Throwable) {
                Log.e("CITYERR", t.message.toString())
            }
        })
    }

    private fun checkNotNullParam(param: String?): Boolean {
        return param != null
    }
}