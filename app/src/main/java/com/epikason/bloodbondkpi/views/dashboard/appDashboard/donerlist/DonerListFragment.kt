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

    override fun setListener() {
        setupSpinner()
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

    // ---------------- Spinner Filter ---------------- //

    private fun setupSpinner() {
        val bloodGroups = listOf(
            "All",
            "A+", "A-",
            "B+", "B-",
            "O+", "O-",
            "AB+", "AB-"
        )

        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            bloodGroups
        )

        binding.spinnerFilter.adapter = spinnerAdapter

        binding.spinnerFilter.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterByBloodGroup(bloodGroups[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }

    private fun filterByBloodGroup(group: String) {

        if (!::adapter.isInitialized) return

        val filteredList = if (group == "All") {
            fullDonerList
        } else {
            fullDonerList.filter {
                it.bloodGroup?.trim() == group
            }
        }

        adapter.updateList(filteredList)
        toggleEmptyView(filteredList)
    }

    private fun toggleEmptyView(list: List<UserInfo>) {
        binding.lottieEmpty.visibility =
            if (list.isEmpty()) View.VISIBLE else View.GONE
    }
}
