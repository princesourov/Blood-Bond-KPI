package com.epikason.bloodbondkpi.views.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserRegistration
import com.epikason.bloodbondkpi.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authService: AuthRepository
) : ViewModel() {

    private val _registrationResponse = MutableLiveData<DataState<UserRegistration>>()
    val registrationResponse: LiveData<DataState<UserRegistration>> = _registrationResponse

    fun userRegistration(user: UserRegistration) {

        _registrationResponse.postValue(DataState.Loading())


        authService.userRegistration(user).addOnSuccessListener {

            it.user?.let { createdUser ->
                user.userID = createdUser.uid

                authService.createUser(user).addOnSuccessListener {
                    _registrationResponse.postValue(DataState.Success(user))
                }.addOnFailureListener { error ->
                    _registrationResponse.postValue(DataState.Error("${error.message}"))

                }
            }
        }.addOnFailureListener { error ->
            _registrationResponse.postValue(DataState.Error("${error.message}"))
        }
    }
}

