package com.epikason.bloodbondkpi.data.repository

import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.data.model.UserLogIn
import com.epikason.bloodbondkpi.data.model.UserRegistration
import com.epikason.bloodbondkpi.data.services.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository : AuthService {
    override fun userRegistration(user: UserRegistration): Task<AuthResult> {
        val qAuth = FirebaseAuth.getInstance()
        return qAuth.createUserWithEmailAndPassword(user.email, user.password)
    }

    override fun userLogin(user: UserLogIn): Task<AuthResult> {
        val qAuth = FirebaseAuth.getInstance()
        return qAuth.signInWithEmailAndPassword(user.email, user.password)
    }

    override fun createUser() {
    }
}