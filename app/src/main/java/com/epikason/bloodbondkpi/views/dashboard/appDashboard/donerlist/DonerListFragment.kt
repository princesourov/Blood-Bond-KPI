package com.epikason.bloodbondkpi.views.dashboard.appDashboard.donerlist


import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.databinding.FragmentDonerListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonerListFragment : BaseFragment<FragmentDonerListBinding>(FragmentDonerListBinding::inflate) {

    private val viewModel: DonerListViewModel by viewModels ()

    override fun setListener() {

    }

    override fun allObserver() {
        viewModel.getDonerResponse.observe(viewLifecycleOwner){
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
    private fun setDataToRV(list: List<UserInfo>) {
        binding.rvBloodRequest.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DonerListAdapter(list)
        }
    }

}