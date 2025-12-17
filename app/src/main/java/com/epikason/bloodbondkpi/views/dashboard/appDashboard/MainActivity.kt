package com.epikason.bloodbondkpi.views.dashboard.appDashboard

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.views.auth.AuthActivity
import com.epikason.bloodbondkpi.views.dashboard.userDashboard.UserDashboard
import com.epikason.bloodbondkpi.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var qAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        qAuth = FirebaseAuth.getInstance()

        binding.btnGoProfile.setOnClickListener {
            val currentUser = qAuth.currentUser
            val intent = if (currentUser != null) {
                Intent(this, UserDashboard::class.java)
            } else {
                Intent(this, AuthActivity::class.java)
            }
            startActivity(intent)
        }

        navController = findNavController(R.id.fragmentContainerView)
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
