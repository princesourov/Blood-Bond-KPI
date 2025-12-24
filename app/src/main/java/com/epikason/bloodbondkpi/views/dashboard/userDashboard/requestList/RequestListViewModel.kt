package com.epikason.bloodbondkpi.views.dashboard.userDashboard.requestList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class RequestListViewModel @Inject constructor(
    private val userService: UserRepository
) : ViewModel() {

    private val _getRequestResponse = MutableLiveData<DataState<List<BloodRequest>>>()
    val getRequestResponse: LiveData<DataState<List<BloodRequest>>> = _getRequestResponse

    fun getRequestByID(userID: String) {
        _getRequestResponse.postValue(DataState.Loading())

        userService.getBloodRequestByUserID(userID)
            .addOnSuccessListener { document ->

                val list = mutableListOf<BloodRequest>()

                document.documents.forEach { doc ->
                    doc.toObject(BloodRequest::class.java)?.let {
                        it.documentId = doc.id
                        list.add(it)
                    }
                }

                _getRequestResponse.postValue(DataState.Success(list))
            }
            .addOnFailureListener { e ->
                _getRequestResponse.postValue(
                    DataState.Error(e.message ?: "Error")
                )
            }
    }
}
