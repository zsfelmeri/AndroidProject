package com.restaurantapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.restaurantapp.data.model.User
import kotlinx.coroutines.Job

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: User)

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT count(*) FROM user_table WHERE email = :email AND username = :username AND phoneNumber = :phoneNum")
    suspend fun findUser(email: String, username: String, phoneNum: String): LiveData<Int>

    @Query("SELECT count(*) FROM user_table WHERE (email = :username OR phoneNumber = :username OR username = :username) AND password = :password")
    suspend fun loginUser(username: String, password: String): LiveData<Int>
}