package com.epikason.bloodbondkpi.views.auth.userDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UserDetailsViewModel@Inject constructor(
    private val userService: UserRepository
) : ViewModel() {
    private val _userDetailsResponse = MutableLiveData<DataState<UserInfo>>()
    val userDetailsResponse: LiveData<DataState<UserInfo>> = _userDetailsResponse

    fun userDetails(user: UserInfo) {
        _userDetailsResponse.postValue(DataState.Loading())

        userService.userDetails(user).addOnSuccessListener {
            _userDetailsResponse.postValue(DataState.Success(user))
        }.addOnFailureListener { error ->
            _userDetailsResponse.postValue(DataState.Error("${error.message}"))
        }
    }
}