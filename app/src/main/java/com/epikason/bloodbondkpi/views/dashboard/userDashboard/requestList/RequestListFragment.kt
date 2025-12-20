package com.epikason.bloodbondkpi.views.dashboard.userDashboard.requestList

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.FragmentRequestListBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class RequestListFragment :
    BaseFragment<FragmentRequestListBinding>(FragmentRequestListBinding::inflate) {
    private val viewModel: RequestListViewModel by viewModels()

    override fun setListener() {

        FirebaseAuth.getInstance().currentUser?.let {

            viewModel.getRequestByID(it.uid)
        }

    }

    override fun allObserver() {
        viewModel.getRequestResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Error -> {
                    loadingDialog?.dismiss()
            }
                is DataState.Loading -> {
                    loadingDialog?.show()
                }
                is DataState.Success -> {
                    it.data?.let { it->
                        setDataToRV(it)
                    }
                    loadingDialog?.dismiss()
                }
            }

        }
    }
    private fun setDataToRV(list: List<BloodRequest>) {
        binding.rvBloodRequest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = BloodRequestAdapter(list as MutableList<BloodRequest>)
        }
    }

}