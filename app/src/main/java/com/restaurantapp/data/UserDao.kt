package com.restaurantapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.restaurantapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table WHERE (email = :username OR phoneNumber = :username OR username = :username) AND password = :password")
    fun getUser(username: String, password: String): User

    @Query("SELECT count(*) FROM user_table WHERE (email = :username OR phoneNumber = :username OR username = :username) AND password = :password")
    fun loginUser(username: String, password: String): Int

    @Delete
    fun deleteUser(user: User)
}