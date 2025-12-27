package com.epikason.bloodbondkpi.views.dashboard.appDashboard.donerlist

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.databinding.FragmentDonerListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonerListFragment :
    BaseFragment<FragmentDonerListBinding>(FragmentDonerListBinding::inflate) {

    private val viewModel: DonerListViewModel by viewModels()

    private lateinit var adapter: DonerListAdapter
    private var fullDonerList = listOf<UserInfo>()

    private var selectedBloodGroup = "All"
    private var selectedStatus = "All"

    override fun setListener() {
        setupSpinners()
    }

    override fun allObserver() {
        viewModel.getDonerResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> loadingDialog?.show()
                is DataState.Error -> loadingDialog?.dismiss()
                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    it.data?.let { list ->
                        fullDonerList = list
                        setDataToRV(list)
                    }
                }
            }
        }
    }
    private fun setDataToRV(list: List<UserInfo>) {
        if (!::adapter.isInitialized) {
            adapter = DonerListAdapter(list.toMutableList())
            binding.rvBloodRequest.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = this@DonerListFragment.adapter
            }
        } else {
            adapter.updateList(list)
        }
        toggleEmptyView(list)
    }

    private fun setupSpinners() {
        val bloodGroups = listOf("All", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        val statusList = listOf("All", "Interested", "Not Interested", "Not Set")

        binding.spinnerBloodGroup.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                bloodGroups
            )
        binding.spinnerBloodGroup.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedBloodGroup = bloodGroups[position]
                    applyFilters()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }

        binding.spinnerStatus.adapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                statusList
            )
        binding.spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedStatus = statusList[position]
                applyFilters()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun applyFilters() {
        if (!::adapter.isInitialized) return

        val filteredList = fullDonerList.filter { donor ->
            val bloodMatch =
                (selectedBloodGroup == "All") || (donor.bloodGroup?.trim() == selectedBloodGroup)
            val statusMatch = (selectedStatus == "All") || (donor.status?.trim() == selectedStatus)
            bloodMatch && statusMatch
        }

        adapter.updateList(filteredList)
        toggleEmptyView(filteredList)
    }

    private fun toggleEmptyView(list: List<UserInfo>) {
        binding.layoutEmpty.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}
