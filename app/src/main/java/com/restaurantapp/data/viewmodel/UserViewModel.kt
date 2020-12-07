package com.restaurantapp.data.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.restaurantapp.data.UserDatabase
import com.restaurantapp.data.repository.UserRepository
import com.restaurantapp.data.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

//    private val readAllData: LiveData<List<User>>
    private val repository: UserRepository

    init{
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
//        readAllData = repository.readAllData
    }

    fun getUser(username: String, password: String): User {
        return repository.getUser(username, password)
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun findUser(email: String, username: String, phoneNum: String): Int {
        return repository.findUser(email, username, phoneNum)
    }

    fun loginUser(username: String, password: String): Int {
        return repository.loginUser(username, password)
    }
}
