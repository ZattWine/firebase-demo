package com.norm.firebasedemo

data class User(
    val name: String = "",
    val email: String = "",
    val bio: String = ""
) : Model()