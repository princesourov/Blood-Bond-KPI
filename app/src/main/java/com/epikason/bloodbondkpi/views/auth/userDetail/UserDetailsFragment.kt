package com.epikason.bloodbondkpi.views.auth.userDetail

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.core.extract
import com.epikason.bloodbondkpi.core.isEmpty
import com.epikason.bloodbondkpi.data.model.UserInfo
import com.epikason.bloodbondkpi.databinding.FragmentUserDetailsBinding
import com.epikason.bloodbondkpi.views.dashboard.userDashboard.UserDashboard
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class UserDetailsFragment :
    BaseFragment<FragmentUserDetailsBinding>(FragmentUserDetailsBinding::inflate) {
    private val viewModel: UserDetailsViewModel by viewModels()
    private lateinit var name: String

    val currentUser = FirebaseAuth.getInstance().currentUser
    val uid = currentUser?.uid ?: ""
    val email = currentUser?.email ?: ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get name from RegisterFragment
        name = arguments?.getString("name").orEmpty()}


    override fun setListener() {
        setupDropdowns()
        setupDatePicker()
        setupSubmitButton()
    }

    override fun allObserver() {
        registrationResponse()
    }

    private fun setupDropdowns() {
        val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        val bloodAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, bloodGroups)
        binding.spinnerBloodGroup.setAdapter(bloodAdapter)

        val genders = listOf("Male", "Female","Others")
        val genderAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, genders)
        binding.spinnerGender.setAdapter(genderAdapter)

        val departments = listOf("CST", "ET","ENT", "MT", "Civil", "AIDT", "Construction")
        val deptAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, departments)
        binding.spinnerDepartment.setAdapter(deptAdapter)
    }

    private fun setupDatePicker() {
        binding.etDOB.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    binding.etDOB.setText("$day/${month + 1}/$year")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupSubmitButton() {

        with(binding) {
            btnSubmitDonor.setOnClickListener {
                spinnerBloodGroup.isEmpty()
                spinnerGender.isEmpty()
                etDOB.isEmpty()
                etSeason.isEmpty()
                spinnerDepartment.isEmpty()
                etRoll.isEmpty()
                etPhone.isEmpty()
                if (!spinnerBloodGroup.isEmpty() && !spinnerGender.isEmpty() && !etDOB.isEmpty()
                    && !etSeason.isEmpty() && !spinnerDepartment.isEmpty() && !etRoll.isEmpty()
                    && !etPhone.isEmpty() ){
                    val user= UserInfo(
                        name,
                        email,
                        spinnerBloodGroup.extract(),
                        spinnerGender.extract(),
                        etDOB.extract(),
                        etSeason.extract(),
                        spinnerDepartment.extract(),
                        etRoll.extract(),
                        etPhone.extract(),
                        "",
                        "",
                        uid

                    )
                    viewModel.userDetails(user)
                }
            }}

    }
    private fun registrationResponse() {
        viewModel.userDetailsResponse.observe(viewLifecycleOwner) {
            when(it){is DataState.Error -> {
                loadingDialog?.dismiss()
                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            }
                is DataState.Loading -> {
                    loadingDialog?.show()
                }
                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    val intent = Intent(requireContext(), UserDashboard::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                }
            }
        }
    }
}
