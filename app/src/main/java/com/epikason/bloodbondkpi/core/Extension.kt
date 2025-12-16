package com.epikason.bloodbondkpi.core

import android.content.pm.PackageManager
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlin.collections.all
import kotlin.text.trim

fun EditText.extract(): String {
    return text.toString().trim()
}

fun Fragment.requestPermission(
    request: ActivityResultLauncher<Array<String>>,
    permissions: Array<String>
    ) {
    request.launch(permissions)
}

fun Fragment.areAllPermissionGranted(permission: Array<String>): Boolean {
    return permission.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }
}

