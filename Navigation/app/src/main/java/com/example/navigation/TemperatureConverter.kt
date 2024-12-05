package com.example.navigation

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.navigation.databinding.ActivityMainBinding

class TemperatureConverter : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_temperature_converter)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var celsius: EditText = findViewById(R.id.edit_celsius)
        var result: TextView = findViewById(R.id.text_result)
        var convert: Button = findViewById(R.id.button_convert)

        convert.setOnClickListener {
            if (celsius.text.toString().isNotEmpty()) {
                val celsiusDouble = celsius.text.toString().toDouble()
                val fahrenheit = String.format("%.2f", celsiusDouble * 1.8 + 32)
                result.text = "$fahrenheit 'F"
            }else {
                result.text = "Temperatura Inválida"
            }
        }
    }
}