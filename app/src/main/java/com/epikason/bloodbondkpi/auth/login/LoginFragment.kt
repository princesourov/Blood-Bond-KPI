package com.epikason.bloodbondkpi.auth.login


import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.core.extract
import com.epikason.bloodbondkpi.core.isEmpty
import com.epikason.bloodbondkpi.dashboard.userDashboard.UserDashboardActivity
import com.epikason.bloodbondkpi.data.model.UserLogIn
import com.epikason.bloodbondkpi.databinding.FragmentLoginBinding
import kotlin.getValue

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    private val viewModel: LogInViewModel by viewModels()
    override fun setListener() {
        with(binding) {
            btnLogin.setOnClickListener {
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etEmail.isEmpty() && !etPassword.isEmpty()) {
                    var user = UserLogIn(etEmail.extract(), etPassword.extract())

                    viewModel.userLogin(user)
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
        logInResponse()
    }


    private fun logInResponse() {
        viewModel.logInResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Error -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is DataState.Loading -> {
                    loadingDialog?.show()
                }

                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    val intent = Intent(requireContext(), UserDashboardActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
    }
}