package com.epikason.bloodbondkpi.views.dashboard.appDashboard.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.FragmentHomeBinding
import com.epikason.bloodbondkpi.views.dashboard.userDashboard.requestList.BloodRequestAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private val viewModel: HomeViewModel by viewModels ()
    override fun setListener() {

    }

    override fun allObserver() {
        viewModel.getRequestResponse.observe(viewLifecycleOwner){
            when(it) {
                is DataState.Error -> {
                    loadingDialog?.dismiss()
                }
                is DataState.Loading -> {
                    loadingDialog?.show()
                }
                is DataState.Success -> {
                    it.data?.let { it1->
                        setDataToRV(it1)
                    }
                    loadingDialog?.dismiss()
                }
            }
        }
    }
    private fun setDataToRV(list: List<BloodRequest>) {
        binding.rvBloodRequest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = AllBloodRequestAdapter(list)
        }
    }

}