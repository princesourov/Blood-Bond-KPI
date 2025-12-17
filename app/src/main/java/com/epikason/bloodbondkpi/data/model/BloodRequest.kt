package com.epikason.bloodbondkpi.data.model

data class BloodRequest(
    var bloodGroup: String = "",
    var units: String = "",
    var pName: String = "",
    var hName: String = "",
    var date: String = "",
    var time: String = "",
    var reason: String = "",
    var eLevel: String = "",
    var number: String = "",
    var userID: String = ""
)
