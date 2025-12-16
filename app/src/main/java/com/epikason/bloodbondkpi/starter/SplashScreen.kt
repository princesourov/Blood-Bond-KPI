package com.epikason.bloodbondkpi.starter

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.epikason.bloodbondkpi.R
import com.epikason.bloodbondkpi.dashboard.allUser.MainActivity

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)


        val splashLogo: ImageView = findViewById(R.id.splash_logo)
        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        splashLogo.startAnimation(fadeIn)


        object : CountDownTimer(1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                startActivity(Intent(this@SplashScreen, MainActivity::class.java))
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()
            }
        }.start()
    }
}