package com.example.paycalculator

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.OnApplyWindowInsetsListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale
import androidx.core.graphics.Insets



class MainActivity : AppCompatActivity() {

    // Inputs
    private lateinit var etHours: EditText
    private lateinit var etRate: EditText
    private lateinit var etTaxRate: EditText

    // Outputs
    private lateinit var tvPay: TextView
    private lateinit var tvOvertimePay: TextView
    private lateinit var tvTotalPay: TextView
    private lateinit var tvTax: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById<View?>(R.id.main),
            OnApplyWindowInsetsListener { v: View?, insets: WindowInsetsCompat? ->
                val bars: Insets =
                    insets!!.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())
                v!!.setPadding(bars.left, bars.top, bars.right, bars.bottom)
                insets
            })

        etHours = findViewById(R.id.etHours)
        etRate = findViewById(R.id.etRate)
        etTaxRate = findViewById(R.id.etTaxRate)

        tvPay = findViewById(R.id.tvPay)
        tvOvertimePay = findViewById(R.id.tvOvertimePay)
        tvTotalPay = findViewById(R.id.tvTotalPay)
        tvTax = findViewById(R.id.tvTax)

        findViewById<Button>(R.id.btnCalculate).setOnClickListener { calculate() }
    }

    private fun calculate() {
        // 1. Read input (stop if any field is empty or invalid)
        val hours = readNumber(etHours) ?: return
        val hourlyRate = readNumber(etRate) ?: return
        val taxPercent = readNumber(etTaxRate) ?: return
        val taxRate = taxPercent / 100   // user types 13 for 13% -> 0.13

        // 2. Calculate pay and overtime
        val pay: Double
        val overtimePay: Double
        if (hours <= 40) {
            pay = hours * hourlyRate
            overtimePay = 0.0
        } else {
            pay = 40 * hourlyRate
            overtimePay = (hours - 40) * hourlyRate * 1.5
        }
        val totalPay = pay + overtimePay

        // 3. Calculate tax (formula from the assignment: tax = pay * tax_rate)
        val tax = pay * taxRate

        // 4. Show results
        tvPay.text = getString(R.string.result_pay, money(pay))
        tvOvertimePay.text = getString(R.string.result_overtime, money(overtimePay))
        tvTotalPay.text = getString(R.string.result_total, money(totalPay))
        tvTax.text = getString(R.string.result_tax, money(tax))
    }

    /** Returns the field's value as a Double, or shows an error and returns null. */
    private fun readNumber(field: EditText): Double? {
        val value = field.text.toString().trim().toDoubleOrNull()
        if (value == null || value < 0) {
            field.error = getString(R.string.error_invalid)
            field.requestFocus()
            return null
        }
        return value
    }

    private fun money(amount: Double): String =
        String.format(Locale.CANADA, "$%,.2f", amount)

    // ----- Menu: opens AboutActivity -----

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
