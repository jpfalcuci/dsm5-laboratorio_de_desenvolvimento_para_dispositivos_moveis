package com.example.navigation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CurrencyConverter : AppCompatActivity() {

    private val exchangeRateUSD = 0.18  // BRL to USD
    private val exchangeRateEUR = 0.17  // BRL to EUR
    private val exchangeRateARS = 51.85 // BRL to ARS

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_currency_converter)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editBrl = findViewById<EditText>(R.id.edit_brl)
        val buttonUsd = findViewById<Button>(R.id.button_usd)
        val buttonEur = findViewById<Button>(R.id.button_eur)
        val buttonArs = findViewById<Button>(R.id.button_ars)
        val textResult = findViewById<TextView>(R.id.text_result)

        buttonUsd.setOnClickListener {
            val inputValue = editBrl.text.toString()
            if (inputValue.isNotEmpty()) {
                val valueInBrl = inputValue.toDouble()
                val result = valueInBrl * exchangeRateUSD
                textResult.text = "USD: %.2f".format(result)
            } else {
                Toast.makeText(this, "Por favor, insira um valor em BRL", Toast.LENGTH_SHORT).show()
            }
        }

        buttonEur.setOnClickListener {
            val inputValue = editBrl.text.toString()
            if (inputValue.isNotEmpty()) {
                val valueInBrl = inputValue.toDouble()
                val result = valueInBrl * exchangeRateEUR
                textResult.text = "EUR: %.2f".format(result)
            } else {
                Toast.makeText(this, "Por favor, insira um valor em BRL", Toast.LENGTH_SHORT).show()
            }
        }

        buttonArs.setOnClickListener {
            val inputValue = editBrl.text.toString()
            if (inputValue.isNotEmpty()) {
                val valueInBrl = inputValue.toDouble()
                val result = valueInBrl * exchangeRateARS
                textResult.text = "ARS: %.2f".format(result)
            } else {
                Toast.makeText(this, "Por favor, insira um valor em BRL", Toast.LENGTH_SHORT).show()
            }
        }
    }
}