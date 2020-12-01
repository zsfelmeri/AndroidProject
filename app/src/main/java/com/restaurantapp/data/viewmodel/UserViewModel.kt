package com.restaurantapp.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.restaurantapp.data.UserDatabase
import com.restaurantapp.data.repository.UserRepository
import com.restaurantapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

//    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository
//    lateinit var checkLogin: LiveData<Int>
//    lateinit var checkRegister: LiveData<Int>

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
//        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun findUser(email: String, username: String, phoneNum: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.findUser(email, username, phoneNum)
        }
    }

    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.loginUser(username, password)
        }
    }
}