package com.epikason.bloodbondkpi.data.model

data class UserRegistration(
    val name : String,
    val email : String,
    val password : String,
    var userID : String,
)