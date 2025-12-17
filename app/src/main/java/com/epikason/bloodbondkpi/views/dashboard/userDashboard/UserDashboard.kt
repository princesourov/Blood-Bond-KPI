package com.epikason.bloodbondkpi.views.dashboard.userDashboard

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.databinding.ActivityUserDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDashboard : AppCompatActivity() {
    lateinit var binding: ActivityUserDashboardBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.fragmentContainerView2)

        binding.bottomNavigationView.setupWithNavController(navController)

    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)
        return true
    }
}