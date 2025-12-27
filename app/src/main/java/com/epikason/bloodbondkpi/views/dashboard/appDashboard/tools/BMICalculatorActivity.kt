package com.epikason.bloodbondkpi.views.dashboard.appDashboard.tools

import android.animation.ArgbEvaluator
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
    private val feetToMeter = 0.3048f
    private val MAX_BMI = 40f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcButton.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val weightKg = binding.weightInput.text.toString().toFloatOrNull()
        val heightFeet = binding.heightInput.text.toString().toFloatOrNull()

        if (weightKg == null || weightKg <= 0f || heightFeet == null || heightFeet <= 0f) {
            Toast.makeText(this, "Enter valid positive numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val heightMeter = heightFeet * feetToMeter
        val bmi = weightKg / (heightMeter * heightMeter)
        val bmiRounded = String.format("%.2f", bmi).toFloat()

        val quint = getBMICategory(bmi)

        animateCardColor(quint.color)
        animateBMICounter(bmiRounded)

        binding.resultCard.visibility = View.VISIBLE
        binding.bmiCategory.text = "${quint.emoji} ${quint.category}"
        binding.bmiAdvice.text = quint.advice
        binding.bmiRisk.text = "Risk Level: ${quint.risk}"

        val progress = ((bmi.coerceAtMost(MAX_BMI) / MAX_BMI) * 100).roundToInt()
        binding.bmiProgress.setProgress(progress, true)
    }

    private fun getBMICategory(bmi: Float): Quint {
        return when {
            bmi < 18.5 -> Quint(
                "Underweight",
                "Eat more nutritious food 🍎",
                "Low Risk",
                0xFF03A9F4.toInt(),
                "⚠️"
            )

            bmi in 18.5..24.9 -> Quint(
                "Normal",
                "Maintain your healthy lifestyle 🏃‍♂️",
                "Low Risk",
                0xFF4CAF50.toInt(),
                "✅"
            )

            bmi in 25.0..29.9 -> Quint(
                "Overweight",
                "Exercise regularly and control diet 🏋️‍♂️",
                "Moderate Risk",
                0xFFFFC107.toInt(),
                "⚠️"
            )

            else -> Quint(
                "Obese",
                "Consult doctor and improve lifestyle 🩺",
                "High Risk",
                0xFFF44336.toInt(),
                "❌"
            )
        }
    }

    private fun animateCardColor(color: Int) {
        val colorAnim = ValueAnimator.ofObject(ArgbEvaluator(), Color.WHITE, color)
        colorAnim.duration = 500
        colorAnim.addUpdateListener { animator ->
            binding.resultCard.setCardBackgroundColor(animator.animatedValue as Int)
        }
        colorAnim.start()
    }

    private fun animateBMICounter(finalBMI: Float) {
        val animator = ValueAnimator.ofFloat(0f, finalBMI)
        animator.duration = 800
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            binding.bmiValue.text = "BMI: ${String.format("%.2f", value)}"
        }
        animator.start()
    }

    data class Quint(
        val category: String,
        val advice: String,
        val risk: String,
        val color: Int,
        val emoji: String
    )
}
