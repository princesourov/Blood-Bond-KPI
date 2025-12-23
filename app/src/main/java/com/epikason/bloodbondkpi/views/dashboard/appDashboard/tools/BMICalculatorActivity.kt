package com.epikason.bloodbondkpi.views.dashboard.appDashboard.tools

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.epikason.bloodbondkpi.databinding.ActivityBmicalculatorBinding
import kotlin.math.roundToInt

class BMICalculatorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmicalculatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val feet = 0.3048

        binding.calcButton.setOnClickListener {
            val weight = binding.weightInput.text.toString().toFloatOrNull()
            val heightfeet = binding.heightInput.text.toString().toFloatOrNull()

            if (weight == null || heightfeet == null) {
                Toast.makeText(this, "Enter valid numbers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val heightM = (heightfeet * 12 ) * 0.0254f
            val bmi = weight / (heightM * heightM)
            val bmiRounded = String.format("%.2f", bmi)

            // Determine category, advice, risk, color, and emoji
            val (category, advice, risk, color, emoji) = when {
                bmi < 18.5 -> Quint("Underweight", "Eat more nutritious food 🍎", "Low Risk", Color.parseColor("#03A9F4"), "⚠️")
                bmi in 18.5..24.9 -> Quint("Normal", "Maintain your healthy lifestyle 🏃‍♂️", "Low Risk", Color.parseColor("#4CAF50"), "✅")
                bmi in 25.0..29.9 -> Quint("Overweight", "Exercise regularly and control diet 🏋️‍♂️", "Moderate Risk", Color.parseColor("#FFC107"), "⚠️")
                else -> Quint("Obese", "Consult doctor and improve lifestyle 🩺", "High Risk", Color.parseColor("#F44336"), "❌")
            }

            // Update UI
            binding.bmiValue.text = "BMI: $bmiRounded"
            binding.bmiCategory.text = "$emoji $category"
            binding.bmiAdvice.text = advice
            binding.bmiRisk.text = "Risk Level: $risk"

            // Animate card background color
            val colorAnim = ValueAnimator.ofArgb(Color.WHITE, color)
            colorAnim.duration = 500
            colorAnim.addUpdateListener { animator ->
                binding.resultCard.setCardBackgroundColor(animator.animatedValue as Int)
            }
            colorAnim.start()

            // Show card
            binding.resultCard.visibility = View.VISIBLE

            // Update circular progress (0-40 BMI scale)
            val progress = ((bmi.coerceAtMost(40f) / 40f) * 100).roundToInt()
            binding.bmiProgress.setProgress(progress, true)
        }
    }

    // Data class with emoji
    data class Quint(
        val category: String,
        val advice: String,
        val risk: String,
        val color: Int,
        val emoji: String
    )
}
