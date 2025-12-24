package com.epikason.bloodbondkpi.views.dashboard.userDashboard.requestList

import android.view.View
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
class RequestListFragment : BaseFragment<FragmentRequestListBinding>(FragmentRequestListBinding::inflate) {
    private val viewModel: RequestListViewModel by viewModels()

    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getRequestByID(it.uid)
        }
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
            BloodRequestAdapter(list.toMutableList()) {
                showEmptyState(true)
            }
    }

    private fun showEmptyState(show: Boolean) {
        with(binding) {
            layoutEmpty.visibility =
                if (show) View.VISIBLE else View.GONE
            rvBloodRequest.visibility =
                if (show) View.GONE else View.VISIBLE
        }

    }
}
