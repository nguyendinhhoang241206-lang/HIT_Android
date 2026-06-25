package com.example.myapplication.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.local.User
import com.example.myapplication.data.respository.LoginRespository

class LoginViewModel : ViewModel() {
    private val repository = LoginRespository()

    private val _currentUser = MutableLiveData<String>()
    val currentUser: LiveData<String> = _currentUser

    fun login(username: String, password: String) {
        val user = repository.login(username, password)
        if(user != null){
            _currentUser.value = "Xin chao ${user?.username}"
        } else{
            _currentUser.value = "Khong ton tai!"
        }
    }
}