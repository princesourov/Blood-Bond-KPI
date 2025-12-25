package com.epikason.bloodbondkpi.views.dashboard.userDashboard.addRequest

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.base.BaseFragment
import com.epikason.bloodbondkpi.core.DataState
import com.epikason.bloodbondkpi.core.extract
import com.epikason.bloodbondkpi.core.isEmpty
import com.epikason.bloodbondkpi.data.model.BloodRequest
import com.epikason.bloodbondkpi.databinding.FragmentAddRequestBinding
import com.epikason.bloodbondkpi.views.dashboard.userDashboard.profile.UserProfileViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddRequestFragment :
    BaseFragment<FragmentAddRequestBinding>(FragmentAddRequestBinding::inflate) {

    private val viewModel: AddRequestViewModel by viewModels()
    private val profileViewModel: UserProfileViewModel by viewModels()

    private var userName = ""
    private var userEmail = ""
    private var userMobile = ""
    private var userDepartment = ""
    private var userSeason = ""
    private var userRoll = ""
    private var gender = ""
    private var bloodGroup = ""
    private val autoDate = getCurrentDate()
    private val autoTime = getCurrentTime()


    private val uid: String by lazy {
        FirebaseAuth.getInstance().currentUser?.uid ?: ""
    }

    override fun setListener() {
        setupDropdowns()
        setupDatePicker()
        setupTimePicker()
        setupSubmit()
    }

    override fun allObserver() {

        if (uid.isNotEmpty()) {
            profileViewModel.getUserInfoByID(uid)
        }

        observeAddRequest()
        observeUserProfile()
    }


    private fun setupDropdowns() {
        val bloodGroups = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")
        val emergencyLevels = listOf("Normal", "Urgent", "Emergency")

        binding.spinnerBloodGroup.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, bloodGroups)
        )

        binding.spinnerEmergency.setAdapter(
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, emergencyLevels)
        )
    }

    private fun setupDatePicker() {
        binding.etDate.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    binding.etDate.setText("$day/${month + 1}/$year")
                },
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupTimePicker() {
        binding.etTime.setOnClickListener {
            val cal = Calendar.getInstance()
            TimePickerDialog(
                requireContext(),
                { _, hour, minute ->
                    val amPm = if (hour >= 12) "PM" else "AM"
                    val hour12 = if (hour % 12 == 0) 12 else hour % 12
                    binding.etTime.setText(
                        String.format("%02d:%02d %s", hour12, minute, amPm)
                    )
                },
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                false
            ).show()
        }
    }


    private fun setupSubmit() {
        binding.btnSubmitRequest.setOnClickListener {

            binding.spinnerBloodGroup.isEmpty()
            binding.etUnits.isEmpty()
            binding.etPatientName.isEmpty()
            binding.etHospitalName.isEmpty()
            binding.etDate.isEmpty()
            binding.etTime.isEmpty()
            binding.etReason.isEmpty()
            binding.spinnerEmergency.isEmpty()
            binding.etPhone.isEmpty()

            if (userName.isEmpty()) {
                Toast.makeText(requireContext(), "User data not loaded yet", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (
                !binding.spinnerBloodGroup.isEmpty() &&
                !binding.etUnits.isEmpty() &&
                !binding.etPatientName.isEmpty() &&
                !binding.etHospitalName.isEmpty() &&
                !binding.etDate.isEmpty() &&
                !binding.etTime.isEmpty() &&
                !binding.etReason.isEmpty() &&
                !binding.spinnerEmergency.isEmpty() &&
                !binding.etPhone.isEmpty()
            ) {

                val request = BloodRequest(
                    binding.spinnerBloodGroup.extract(),
                    binding.etUnits.extract(),
                    binding.etPatientName.extract(),
                    binding.etHospitalName.extract(),
                    binding.etDate.extract(),
                    binding.etTime.extract(),
                    binding.etReason.extract(),
                    binding.spinnerEmergency.extract(),
                    binding.etPhone.extract(),
                    userName,
                    userEmail,
                    userMobile,
                    userDepartment,
                    userSeason,
                    userRoll,
                    bloodGroup,
                    gender,
                    autoDate,
                    autoTime,
                    uid
                )
                viewModel.addRequest(request)
            }
        }
    }


    private fun observeAddRequest() {
        viewModel.addRequestResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> loadingDialog?.show()
                is DataState.Error -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    findNavController().navigate(
                        R.id.action_addRequestFragment_to_requestListFragment2,
                        null,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.addRequestFragment, true)
                            .build()
                    )
                }
            }
        }
    }

    private fun observeUserProfile() {
        profileViewModel.userResponse.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DataState.Loading -> loadingDialog?.show()

                is DataState.Error -> {
                    loadingDialog?.dismiss()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                }

                is DataState.Success -> {
                    loadingDialog?.dismiss()
                    val user = state.data?.firstOrNull()
                    if (user != null) {
                        userName = user.name
                        userEmail = user.email
                        userMobile = user.phon
                        userDepartment = user.department
                        userSeason = user.season
                        userRoll = user.roll
                        bloodGroup = user.bloodGroup
                        gender = user.roll
                    }
                }
            }
        }
    }
    private fun getCurrentDate(): String {
        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val month = cal.get(Calendar.MONTH) + 1
        val year = cal.get(Calendar.YEAR)
        return "$day/$month/$year"
    }

    private fun getCurrentTime(): String {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val amPm = if (hour >= 12) "PM" else "AM"
        val hour12 = if (hour % 12 == 0) 12 else hour % 12

        return String.format("%02d:%02d %s", hour12, minute, amPm)
    }

}
