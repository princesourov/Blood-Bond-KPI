package com.epikason.bloodbondkpi.views.dashboard.appDashboard.tools

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.databinding.ActivityAboutAppBinding

class AboutApp : AppCompatActivity() {
    lateinit var binding: ActivityAboutAppBinding
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        with(binding) {
            icEmail.setOnClickListener {
                openUrl("mailto:princesourov41@gmail.com")
            }
            icInstagram.setOnClickListener {
                openUrl("https://www.instagram.com/sourovsphere/")
            }
            icFacebook.setOnClickListener {
                openUrl("https://www.facebook.com/AIHS.PrinceM")
            }
            icWebsite.setOnClickListener {
                openUrl("https://epikason.weebly.com")
            }
            powerByPage.setOnClickListener {
                openUrl("https://www.facebook.com/MSInnovationsOfficials")
            }
        }
    }
}