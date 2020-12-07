package com.restaurantapp.data.repository

import com.restaurantapp.data.UserDao
import com.restaurantapp.data.model.User

class UserRepository(private val userDao: UserDao) {

//    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    fun getUser(username: String, password: String): User {
        return userDao.getUser(username, password)
    }

    fun findUser(email: String, username: String, phoneNum: String): Int {
        return userDao.findUser(email, username, phoneNum)
    }

    fun loginUser(username: String, password: String): Int {
        return userDao.loginUser(username, password)
    }
}