package com.epikason.bloodbondkpi.auth.login


import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.isEmpty
import com.epikason.bloodbondkpi.databinding.FragmentLoginBinding

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun setListener() {

        with(binding) {
            btnLogin.setOnClickListener {
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etEmail.isEmpty() && !etPassword.isEmpty()) {

                    loadingDialog?.show()
                }
            }
            btnRegister.setOnClickListener {
                findNavController().navigate(
                    R.id.action_loginFragment_to_registerFragment,
                    null,
                    NavOptions.Builder()
                        .setPopUpTo(R.id.loginFragment, true)
                        .build()
                )
            }
        }

    }

    override fun allObserver() {

    }
}