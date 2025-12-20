package com.epikason.bloodbondkpi.views.dashboard.userDashboard.profile

import android.content.Intent
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.databinding.FragmentUserProfileBinding
import com.epikason.bloodbondkpi.views.auth.AuthActivity
import com.epikason.bloodbondkpi.views.dashboard.userDashboard.UserDashboard
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment : BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {

    @Inject
    lateinit var qAuth: FirebaseAuth
    override fun setListener() {

        binding.btnLogout.setOnClickListener {
            qAuth.signOut()
            val intent = Intent(requireContext(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

    }

    override fun allObserver() {

    }
}