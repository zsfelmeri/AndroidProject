package com.restaurantapp.data.repository

import androidx.lifecycle.LiveData
import com.restaurantapp.data.UserDao
import com.restaurantapp.data.model.User

class UserRepository(private val userDao: UserDao) {

//    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun findUser(email: String, username: String, phoneNum: String): Int {
        return userDao.findUser(email, username, phoneNum)
    }
}