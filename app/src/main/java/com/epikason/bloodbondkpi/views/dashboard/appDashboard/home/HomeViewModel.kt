package com.epikason.bloodbondkpi.views.dashboard.appDashboard.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userService: UserRepository
) : ViewModel() {
    private val _getRequestResponse = MutableLiveData<DataState<List<BloodRequest>>>()
    val getRequestResponse: LiveData<DataState<List<BloodRequest>>> = _getRequestResponse

    init {
        getAllRequest()
    }


    fun getAllRequest(){
        _getRequestResponse.postValue(DataState.Loading())

        userService.getAllRequest().addOnSuccessListener { document->

            val productList = mutableListOf<BloodRequest>()

            document.documents.forEach { doc->

                doc.toObject(BloodRequest::class.java)?.let {
                    productList.add(it)
                }
            }
            _getRequestResponse.postValue(DataState.Success(productList))
        }.addOnFailureListener {
            _getRequestResponse.postValue(DataState.Error("${it.message}"))
        }

    }

}