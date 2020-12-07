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
import androidx.navigation.navArgs
import androidx.navigation.ui.onNavDestinationSelected
import com.google.android.material.navigation.NavigationView
import com.restaurantapp.data.OpenTableAPI
import com.restaurantapp.data.model.Cities
import com.restaurantapp.data.model.ResponseData
import com.restaurantapp.data.model.Restaurant
import com.restaurantapp.data.viewmodel.RestaurantViewModel
import com.restaurantapp.databinding.ActivityMainBinding
import com.restaurantapp.fragments.LoginFragment
import com.restaurantapp.fragments.LoginFragment.Companion.USER_LOGIN
import com.restaurantapp.fragments.RegisterFragment
import kotlinx.coroutines.currentCoroutineContext
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)

//        if(!(supportFragmentManager.findFragmentById(R.id.main_fragment)?.tag.equals("fragment_login") ||
//                supportFragmentManager.findFragmentById(R.id.main_fragment)?.tag.equals("fragment_register"))){
//            menuInflater.inflate(R.menu.menu, menu)
//        }
        return true
    }

//    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
//        if(USER_LOGIN){
//            menu?.findItem(R.id.action_profile)?.isVisible = true
//            menu?.findItem(R.id.action_list)?.isVisible = true
//        }
//        else{
//            menu?.findItem(R.id.action_profile)?.isVisible = false
//            menu?.findItem(R.id.action_list)?.isVisible = false
//        }
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(supportFragmentManager.findFragmentById(R.id.loginFragment)?.isVisible == true) {
            item.isEnabled = false
        }
        else {
            item.isEnabled = true
            when (item.itemId) {
                R.id.action_profile -> {
//                navController.navigate(R.id.action_listFragment_to_profileFragment)
//                    Toast.makeText(this.applicationContext, "Profile", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.profileFragment)
                }
                R.id.action_list -> {
//                navController.navigate(R.id.action_profileFragment_to_listFragment)
//                    Toast.makeText(this.applicationContext, "List", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.listFragment)
                }
            }
        }
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun getDataFromAPI(){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://opentable.herokuapp.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val openTableAPI: OpenTableAPI = retrofit.create(OpenTableAPI::class.java)
//        val myCall = openTableAPI.getRestaurantsInChicago()
//        myCall.enqueue(object : Callback<ResponseData> {
//            override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
//                val restaurants = response.body()
//                if (restaurants != null) {
//                    for (restaurant in restaurants.restaurants) {
//                        val data = Restaurant(
//                            restaurant.id,
//                            restaurant.name,
//                            restaurant.address,
//                            restaurant.city,
//                            restaurant.state,
//                            restaurant.area,
//                            restaurant.postal_code,
//                            restaurant.country,
//                            restaurant.phone,
//                            restaurant.lat,
//                            restaurant.lng,
//                            restaurant.price,
//                            restaurant.reserve_url,
//                            restaurant.mobile_reserve_url,
//                            restaurant.image_url
//                        )
//                        mRestaurantViewModel.addRestaurant(data)
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
//                Log.e("err", t.message.toString())
//            }
//        })

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
                                                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged."
                                            )
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