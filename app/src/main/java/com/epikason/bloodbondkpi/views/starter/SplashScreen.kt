package com.epikason.bloodbondkpi.views.starter

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.epikason.bloodbondkpi.databinding.ActivitySplashScreenBinding
import com.epikason.bloodbondkpi.views.dashboard.appDashboard.MainActivity

class SplashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        binding.splashLogo.startAnimation(fadeIn)

        Handler(Looper.getMainLooper()).postDelayed({
            checkInternetAndProceed()
        }, 1000)
    }

    private fun checkInternetAndProceed() {
        if (isInternetAvailable(this)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        } else {
            with(binding) {
                splashLogo.visibility = ImageView.GONE
                from.visibility = TextView.GONE
                companyName.visibility = TextView.GONE
                appName.visibility = TextView.GONE
                lottieNoInternet.visibility = LottieAnimationView.VISIBLE
                textNoInternet.visibility = TextView.VISIBLE
                retryButton.visibility = Button.VISIBLE

                retryButton.setOnClickListener {
                    checkInternetAndProceed()
                }

            }

        }
    }

    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
