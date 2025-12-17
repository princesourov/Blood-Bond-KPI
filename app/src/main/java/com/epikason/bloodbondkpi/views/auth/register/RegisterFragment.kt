package com.epikason.bloodbondkpi.views.auth.register

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.core.extract
import com.epikason.bloodbondkpi.core.isEmpty
import com.epikason.bloodbondkpi.data.model.UserRegistration
import com.epikason.bloodbondkpi.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    private val viewModel: RegistrationViewModel by viewModels()
    override fun setListener() {
        with(binding) {
            btnRegister.setOnClickListener {
                etName.isEmpty()
                etEmail.isEmpty()
                etPassword.isEmpty()
                if (!etName.isEmpty() && !etEmail.isEmpty() && !etPassword.isEmpty()) {
                    val user = UserRegistration(
                        etName.extract(),
                        etEmail.extract(),
                        etPassword.extract(),
                        "",
                    )

                    viewModel.userRegistration(user)
                }
            }
            btLogin.setOnClickListener {
                findNavController().navigate(
                    R.id.action_registerFragment_to_loginFragment,
                    null,
                    NavOptions
                        .Builder()
                        .setPopUpTo(R.id.registerFragment, true)
                        .build()
                )
            }
        }

    }

    override fun allObserver() {
        registrationResponse()

    }

    private fun registrationResponse() {

        viewModel.registrationResponse.observe(viewLifecycleOwner) {

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
                    Toast.makeText(context, "Registration Successful", Toast.LENGTH_SHORT).show()

                    val bundle = Bundle().apply {
                        putString("name", binding.etName.extract())
                    }
                    findNavController().navigate(
                        R.id.action_registerFragment_to_userDetailsFragment,
                        bundle,
                        NavOptions
                            .Builder()
                            .setPopUpTo(R.id.registerFragment, true)
                            .build()
                    )

                }
            }
        }
    }
}