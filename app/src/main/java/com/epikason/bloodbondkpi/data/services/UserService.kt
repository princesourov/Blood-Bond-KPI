package com.epikason.bloodbondkpi.data.services

import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

interface UserService {
    fun userDetails(user: UserInfo):Task<Void>
    fun bloodRequest(user: BloodRequest):Task<Void>
    fun getBloodRequestByUserID(userID : String): Task<QuerySnapshot>
    fun getAllRequest(): Task<QuerySnapshot>
    fun getAllDoner(): Task<QuerySnapshot>
}