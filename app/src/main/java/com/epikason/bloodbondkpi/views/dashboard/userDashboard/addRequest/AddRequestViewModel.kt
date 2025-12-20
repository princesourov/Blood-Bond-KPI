package com.epikason.bloodbondkpi.views.dashboard.userDashboard.addRequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class AddRequestViewModel @Inject constructor(
    private val userService: UserRepository
) : ViewModel() {
    private val _addRequestResponse = MutableLiveData<DataState<BloodRequest>>()
    val addRequestResponse: LiveData<DataState<BloodRequest>> = _addRequestResponse

    fun addRequest(user: BloodRequest) {
        _addRequestResponse.postValue(DataState.Loading())

        userService.bloodRequest(user).addOnSuccessListener {
            _addRequestResponse.postValue(DataState.Success(user))
        }.addOnFailureListener { error ->
            _addRequestResponse.postValue(DataState.Error("${error.message}"))
        }
    }
}