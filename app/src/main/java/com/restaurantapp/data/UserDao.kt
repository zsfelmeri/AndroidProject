package com.restaurantapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.restaurantapp.data.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

//    @Query("SELECT * FROM user_table ORDER BY id ASC")
//    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE (email = :username OR phoneNumber = :username OR username = :username) AND password = :password")
    fun getUser(username: String, password: String): User

    @Query("SELECT count(*) FROM user_table WHERE email = :email AND username = :username AND phoneNumber = :phoneNum")
    fun findUser(email: String, username: String, phoneNum: String): Int

    @Query("SELECT count(*) FROM user_table WHERE (email = :username OR phoneNumber = :username OR username = :username) AND password = :password")
    fun loginUser(username: String, password: String): Int
}