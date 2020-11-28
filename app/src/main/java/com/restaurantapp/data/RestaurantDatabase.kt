package com.restaurantapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.restaurantapp.data.model.Restaurant

@Database(entities = [Restaurant::class], version = 1, exportSchema = false)
abstract class RestaurantDatabase {
    abstract fun restaurantDao(): RestaurantDao

    companion object{
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        RestaurantDatabase::class.java,
                        "restaurant_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}