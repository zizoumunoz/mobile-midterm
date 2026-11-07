package com.example.midterm

data class User(
    val username: String,
    val password: String,
    val todos: MutableList<String> = mutableListOf()
)
