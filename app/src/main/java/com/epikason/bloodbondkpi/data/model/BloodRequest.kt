package com.epikason.bloodbondkpi.data.model

data class BloodRequest(
    val bloodGroup : String,
    val units : String,
    val pName : String,
    val hName : String,
    val date : String,
    val time : String,
    val reason : String,
    val eLevel : String,
    val number : String,
    var userID : String
)
