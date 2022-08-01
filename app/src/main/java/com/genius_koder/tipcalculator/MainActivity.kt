package com.genius_koder.tipcalculator

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.genius_koder.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // Binding object instance with access to the views in the activity_main.xml layout
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout XML file and return a binding object instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // Set the contentView of the Activity to be the root view of the layout
        setContentView(binding.root)
        // a click listener on the calculate button to calculate tip amount
        binding.calculateButton.setOnClickListener { calculateTip() }
        // key listener on EditText to listen for "enter" button press
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    private fun calculateTip() {
        // converting cost of service to decimal value
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        // If the cost is null or 0, then display 0 tip and exit this function early.
        if (cost == null) {
            binding.tipResult.text = getString(
                R.string.tip_amount, NumberFormat.getCurrencyInstance(
                    Locale("en", "in")
                ).format(0.0)
            )
            return
        }

        // Getting tip Percentage based on which radio button is selected
        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_fifteen_percent -> 0.15
            else -> 0.10
        }

        // calculating tip value
        var tip = tipPercentage * cost.toInt()

        // if switch is on rounding off the tip amount
        if (binding.roundUpSwitch.isChecked) {
            tip = kotlin.math.ceil(tip)
        }

        // Displaying tip amount (in Indian Rupee)
        val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)

    }

    //  Key listener for hiding the keyboard when the "Enter" button is tapped.
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}

