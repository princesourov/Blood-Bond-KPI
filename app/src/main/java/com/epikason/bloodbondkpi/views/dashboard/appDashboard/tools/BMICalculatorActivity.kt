package com.epikason.bloodbondkpi.views.dashboard.appDashboard.tools

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.epikason.bloodbondkpi.databinding.ActivityBmicalculatorBinding
import kotlin.math.roundToInt

class BMICalculatorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBmicalculatorBinding
    private val feetToMeter = 0.3048
    private val MAX_BMI = 40.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBmicalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.calcButton.setOnClickListener {
            calculateBMI()
        }
    }

    private fun calculateBMI() {
        val weightStr = binding.weightInput.text.toString()
        val feetStr = binding.feetInput.text.toString()
        val inchStr = binding.inchInput.text.toString()

        if (weightStr.isEmpty() || feetStr.isEmpty()) {
            toast("Weight ও Feet অবশ্যই দিন")
            return
        }

        val weight = weightStr.toDouble()
        val feet = feetStr.toInt()
        val inch = if (inchStr.isEmpty()) 0 else inchStr.toInt()

        if (inch >= 12) {
            toast("Inch 12 এর বেশি হতে পারে না")
            return
        }

        val heightFeet = feet + (inch / 12.0)
        val heightMeter = heightFeet * feetToMeter

        val bmi = weight / (heightMeter * heightMeter)
        val bmiRounded = ((bmi * 100).roundToInt() / 100f)

        val quint = getBMICategory(bmi)

        animateCardColor(quint.color)
        animateBMICounter(bmiRounded)

        binding.resultCard.visibility = View.VISIBLE
        binding.bmiValue.text = "BMI: $bmiRounded"
        binding.bmiCategory.text = "${quint.emoji} ${quint.category}"
        binding.bmiAdvice.text = quint.advice
        binding.bmiRisk.text = "Risk: ${quint.risk}"

        val progress = ((bmi.coerceAtMost(MAX_BMI) / MAX_BMI) * 100).roundToInt()
        binding.bmiProgress.setProgress(progress, true)
    }

    private fun getBMICategory(bmi: Double): Quint {
        return when {
            bmi < 18.5 -> Quint("Underweight", "Eat healthy food 🍎", "Low", 0xFF03A9F4.toInt(), "⚠️")
            bmi < 25 -> Quint("Normal", "Keep fit 🏃‍♂️", "Low", 0xFF4CAF50.toInt(), "✅")
            bmi < 30 -> Quint("Overweight", "Exercise more 🏋️‍♂️", "Moderate", 0xFFFFC107.toInt(), "⚠️")
            else -> Quint("Obese", "Consult doctor 🩺", "High", 0xFFF44336.toInt(), "❌")
        }
    }

    private fun animateCardColor(color: Int) {
        ValueAnimator.ofObject(ArgbEvaluator(), Color.WHITE, color).apply {
            duration = 500
            addUpdateListener {
                binding.resultCard.setCardBackgroundColor(it.animatedValue as Int)
            }
            start()
        }
    }

    private fun animateBMICounter(finalBMI: Float) {
        ValueAnimator.ofFloat(0f, finalBMI).apply {
            duration = 800
            addUpdateListener {
                binding.bmiValue.text = "BMI: ${String.format("%.2f", it.animatedValue)}"
            }
            start()
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    data class Quint(
        val category: String,
        val advice: String,
        val risk: String,
        val color: Int,
        val emoji: String
    )
}
