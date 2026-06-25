package com.example.myapplication.data.respository

import com.example.myapplication.data.local.User
import com.example.myapplication.data.local.UserDAO

class LoginRespository {
    fun login(
        username: String,
        password: String
    ): User? {

        return UserDAO.login(
            username,
            password
        )
    }
}