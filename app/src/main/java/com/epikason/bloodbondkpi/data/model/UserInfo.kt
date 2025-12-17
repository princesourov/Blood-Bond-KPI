package com.epikason.bloodbondkpi.data.model

data class UserInfo(
    val name : String,
    val email : String,
    val bloodGroup : String,
    val gender : String,
    val dateOfBirth : String,
    val season : String,
    val department : String,
    val roll : String,
    val phon : String,
    val bio : String="",
    var userID : String
)
