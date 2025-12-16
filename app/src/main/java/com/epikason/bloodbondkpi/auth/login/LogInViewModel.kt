package com.epikason.bloodbondkpi.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserLogIn
import com.epikason.bloodbondkpi.data.repository.AuthRepository

class LogInViewModel: ViewModel() {

    private val _logInResponse = MutableLiveData<DataState<UserLogIn>>()
    val logInResponse: LiveData<DataState<UserLogIn>> = _logInResponse

    fun userLogin(user: UserLogIn) {

        _logInResponse.postValue(DataState.Loading())

        val authService = AuthRepository()

        authService.userLogin(user).addOnSuccessListener {
            _logInResponse.postValue(DataState.Success(user))

        }.addOnFailureListener {
            _logInResponse.postValue(DataState.Error(it.message.toString()))

        }
    }
}