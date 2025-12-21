package com.epikason.bloodbondkpi.views.dashboard.userDashboard.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
    private val userService: UserRepository
) : ViewModel() {

    private val _userResponse = MutableLiveData<DataState<List<UserInfo>>>()
    val userResponse: LiveData<DataState<List<UserInfo>>> = _userResponse

    private val _updateResponse = MutableLiveData<DataState<String>>()
    val updateResponse: LiveData<DataState<String>> = _updateResponse

    fun getUserInfoByID(userID: String) {
        _userResponse.postValue(DataState.Loading())
        userService.userDetailsByUserID(userID)
            .addOnSuccessListener { document ->
                val userInfoList = mutableListOf<UserInfo>()
                document.documents.forEach { doc ->
                    doc.toObject(UserInfo::class.java)?.let {
                        userInfoList.add(it)
                    }
                }
                _userResponse.postValue(DataState.Success(userInfoList))
            }
            .addOnFailureListener { error ->
                _userResponse.postValue(DataState.Error(error.message ?: "Error"))
            }
    }

    fun updateLastDonateAndStatus(userId: String, lastDonate: String, status: String) {
        _updateResponse.postValue(DataState.Loading())
        val map = hashMapOf<String, Any>(
            "lastDonate" to lastDonate,
            "status" to status
        )
        userService.updateUser(userId, map)
            .addOnSuccessListener { _updateResponse.postValue(DataState.Success("Profile Updated")) }
            .addOnFailureListener { _updateResponse.postValue(DataState.Error(it.message ?: "Update failed")) }
    }
}
