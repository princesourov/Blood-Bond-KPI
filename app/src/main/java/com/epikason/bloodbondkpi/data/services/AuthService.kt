package com.epikason.bloodbondkpi.data.services

import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.data.model.UserLogIn
import com.epikason.bloodbondkpi.data.model.UserRegistration
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

interface AuthService {

    fun userRegistration(user: UserRegistration): Task<AuthResult>
    fun userLogin(user: UserLogIn) : Task<AuthResult>
    fun createUser()
}