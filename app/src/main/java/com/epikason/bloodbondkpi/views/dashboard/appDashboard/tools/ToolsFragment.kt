package com.epikason.bloodbondkpi.views.dashboard.appDashboard.tools

import android.content.Intent
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.databinding.FragmentToolsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToolsFragment : BaseFragment<FragmentToolsBinding>(FragmentToolsBinding::inflate) {
    override fun setListener() {

        with(binding) {

            btnCheckBMI.setOnClickListener {
                startActivity(Intent(requireContext(), BMICalculatorActivity::class.java))
            }
            btnAbout.setOnClickListener {
                startActivity(Intent(requireContext(), AboutApp::class.java))
            }
            btnAboutBlood.setOnClickListener {
                startActivity(Intent(requireContext(), AboutBloodDonetionActivity::class.java))
            }
        }
    }
    override fun allObserver() {

    }
}