package com.epikason.bloodbondkpi.views.dashboard.appDashboard.tools

import android.content.Intent
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.databinding.FragmentToolsBinding
import com.epikason.bloodbondkpi.views.dashboard.userDashboard.UserDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolsFragment : BaseFragment<FragmentToolsBinding>(FragmentToolsBinding::inflate) {
    override fun setListener() {

        with(binding) {

            btnCheckBMI.setOnClickListener {
                startActivity(Intent(requireContext(), BMICalculatorActivity::class.java))
            }

        }

    }

    override fun allObserver() {

    }
}