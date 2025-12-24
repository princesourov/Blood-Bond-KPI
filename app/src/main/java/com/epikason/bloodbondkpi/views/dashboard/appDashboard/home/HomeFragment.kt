package com.epikason.bloodbondkpi.views.dashboard.appDashboard.home

import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    private val viewModel: HomeViewModel by viewModels()

    override fun setListener() {

    }
    override fun allObserver() {
        viewModel.getRequestResponse.observe(viewLifecycleOwner) { state ->
            when (state) {

                is DataState.Loading -> {
                    loadingDialog?.show()
                }

                is DataState.Error -> {
                    loadingDialog?.dismiss()
                    showEmptyState(true)
                }

                is DataState.Success -> {
                    loadingDialog?.dismiss()

                    val list = state.data ?: emptyList()

                    if (list.isEmpty()) {
                        showEmptyState(true)
                    } else {
                        showEmptyState(false)
                        setDataToRV(list)
                    }
                }
            }
        }
    }

    private fun setDataToRV(list: List<BloodRequest>) {
        binding.rvBloodRequest.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvBloodRequest.adapter =
            AllBloodRequestAdapter(list)
    }

    private fun showEmptyState(show: Boolean) {
        binding.layoutEmpty.visibility =
            if (show) View.VISIBLE else View.GONE
        binding.rvBloodRequest.visibility =
            if (show) View.GONE else View.VISIBLE
    }
}