package com.example.myapplication.data.local

object UserDAO {
    private val users = listOf(
        User("admin", "123"),
        User("hoang", "456")
    )

    fun login(username: String, password: String): User? {
        return users.find {
            it.username == username &&
                    it.password == password
        }
    }
}