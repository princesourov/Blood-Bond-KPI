package com.epikason.bloodbondkpi.views.dashboard.appDashboard.donerlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class DonerListViewModel @Inject constructor(
    private val userService: UserRepository
) : ViewModel() {
    private val _getDonerResponse = MutableLiveData<DataState<List<UserInfo>>>()
    val getDonerResponse: LiveData<DataState<List<UserInfo>>> = _getDonerResponse

    init {
        getAllDoner()
    }


    fun getAllDoner() {
        _getDonerResponse.postValue(DataState.Loading())

        userService.getAllDoner().addOnSuccessListener { document ->

            val donerList = mutableListOf<UserInfo>()

            document.documents.forEach { doc ->

                doc.toObject(UserInfo::class.java)?.let {
                    donerList.add(it)
                }
            }
            _getDonerResponse.postValue(DataState.Success(donerList))
        }.addOnFailureListener {
            _getDonerResponse.postValue(DataState.Error("${it.message}"))
        }

    }

}