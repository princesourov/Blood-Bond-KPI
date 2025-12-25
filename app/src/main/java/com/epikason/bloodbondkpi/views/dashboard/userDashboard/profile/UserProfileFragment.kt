package com.epikason.bloodbondkpi.views.dashboard.userDashboard.profile

import android.app.DatePickerDialog
import android.content.Intent
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class UserProfileFragment :
    BaseFragment<FragmentUserProfileBinding>(FragmentUserProfileBinding::inflate) {

    private val viewModel: UserProfileViewModel by viewModels()

    @Inject
    lateinit var qAuth: FirebaseAuth

    override fun setListener() {
        setupDropdown()
        setupDatePicker()

        binding.btnUpdate.setOnClickListener {
            val userId = qAuth.currentUser?.uid ?: return@setOnClickListener
            val lastDonate = binding.etLastDonate.text.toString().trim()
            val status = binding.spinnerStatus.text.toString().trim()

            if (lastDonate.isEmpty()) {
                binding.etLastDonate.error = "Required"
                return@setOnClickListener
            }

            viewModel.updateLastDonateAndStatus(userId, lastDonate, status)
        }

    }

    override fun allObserver() {
        val userID = qAuth.currentUser?.uid ?: return
        viewModel.getUserInfoByID(userID)

        viewModel.userResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Error -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }
                is DataState.Loading ->{
                    loadingDialog?.show()
                }

                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    val user = state.data?.firstOrNull()
                    user?.let {
                        with(binding) {
                            tvName.text = "Name: ${it.name ?: "No Name"}"
                            tvEmail.text = "Email: ${it.email ?: "No Email"}"
                            tvMobile.text = "Mobile: ${it.phon ?: "No Mobile"}"
                            tvDepartment.text = "Department: ${it.department ?: "No Department"}"
                            tvSeason.text = "Season: ${it.season ?: "No Season"}"
                            tvRoll.text = "Roll: ${it.roll ?: "No Roll"}"
                            tvDOB.text = "Date of Birth: ${it.dateOfBirth ?: "No Date Of Birth"}"
                            tvGender.text = "Gender: ${it.gender ?: "No Gender"}"

                            etLastDonate.setText(it.lastDonate)

                            val statusList = listOf("Interested", "Not Interested")
                            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, statusList)
                            spinnerStatus.setAdapter(adapter)
                            spinnerStatus.setText(it.status ?: "Interested", false)
                        }
                    }
                }
            }
        }

        viewModel.updateResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> {
                    loadingDialog?.show()
                }

                is DataState.Success -> {
                    Toast.makeText(
                        requireContext(),
                        state.data,
                        Toast.LENGTH_SHORT
                    ).show()
                    loadingDialog?.dismiss()
                }

                is DataState.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    loadingDialog?.dismiss()
                }

                else -> {}
            }
        }
    }

    private fun setupDropdown() {
        val statusList = listOf("Interested", "Not Interested")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, statusList)
        binding.spinnerStatus.setAdapter(adapter)
        binding.spinnerStatus.setText("Interested", false)
    }

    private fun setupDatePicker() {
        binding.etLastDonate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    binding.etLastDonate.setText("$day/${month + 1}/$year")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}
