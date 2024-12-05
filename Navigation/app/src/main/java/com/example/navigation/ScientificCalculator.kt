package com.example.navigation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.*

class ScientificCalculator : AppCompatActivity() {

    private lateinit var inputNumber: EditText
    private lateinit var resultText: TextView
    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scientific_calculator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inputNumber = findViewById(R.id.inputNumber)
        resultText = findViewById(R.id.resultText)

        // Números
        findViewById<Button>(R.id.button1).setOnClickListener { appendToInput("1") }
        findViewById<Button>(R.id.button2).setOnClickListener { appendToInput("2") }
        findViewById<Button>(R.id.button3).setOnClickListener { appendToInput("3") }
        findViewById<Button>(R.id.button4).setOnClickListener { appendToInput("4") }
        findViewById<Button>(R.id.button5).setOnClickListener { appendToInput("5") }
        findViewById<Button>(R.id.button6).setOnClickListener { appendToInput("6") }
        findViewById<Button>(R.id.button7).setOnClickListener { appendToInput("7") }
        findViewById<Button>(R.id.button8).setOnClickListener { appendToInput("8") }
        findViewById<Button>(R.id.button9).setOnClickListener { appendToInput("9") }
        findViewById<Button>(R.id.button0).setOnClickListener { appendToInput("0") }
        findViewById<Button>(R.id.buttonDot).setOnClickListener { appendToInput(".") }

        // Operações
        findViewById<Button>(R.id.buttonAdd).setOnClickListener { appendToInput("+") }
        findViewById<Button>(R.id.buttonSubtract).setOnClickListener { appendToInput("-") }
        findViewById<Button>(R.id.buttonMultiply).setOnClickListener { appendToInput("*") }
        findViewById<Button>(R.id.buttonDivide).setOnClickListener { appendToInput("/") }
        findViewById<Button>(R.id.buttonPower).setOnClickListener {
            if (!currentInput.contains("^")) {
                appendToInput("^")
            } else {
                calculatePower()
            }
        }

        // Operações científicas
        findViewById<Button>(R.id.buttonSqrt).setOnClickListener { calculateSqrt() }
        findViewById<Button>(R.id.buttonSin).setOnClickListener { calculateTrigFunction("sin") }
        findViewById<Button>(R.id.buttonCos).setOnClickListener { calculateTrigFunction("cos") }
        findViewById<Button>(R.id.buttonTan).setOnClickListener { calculateTrigFunction("tan") }

        // Limpar e calcular
        findViewById<Button>(R.id.buttonC).setOnClickListener { clearInput() }

        // Calcular
        findViewById<Button>(R.id.buttonEquals).setOnClickListener { calculateExpression() }
    }

    private fun appendToInput(value: String) {
        if (value in listOf("+", "-", "*", "/", "^")) {
            currentInput += " $value "
        } else {
            currentInput += value
        }
        inputNumber.setText(currentInput)
    }

    private fun clearInput() {
        currentInput = ""
        inputNumber.text.clear()
        resultText.text = "Resultado: "
    }

    private fun calculateSqrt() {
        try {
            val number = currentInput.toDouble()
            val result = sqrt(number)
            showResult(result)
        } catch (e: Exception) {
            resultText.text = "Erro"
        }
    }

    private fun calculateExpression() {
        try {
            val result = evaluateExpression(currentInput)
            showResult(result)
        } catch (e: Exception) {
            resultText.text = "Erro"
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.split(" ")
        var result = tokens[0].toDouble()
        var i = 1
        while (i < tokens.size) {
            val operator = tokens[i]
            val nextOperand = tokens[i + 1].toDouble()
            result = when (operator) {
                "+" -> result + nextOperand
                "-" -> result - nextOperand
                "*" -> result * nextOperand
                "/" -> result / nextOperand
                "^" -> result.pow(nextOperand)
                else -> result
            }
            i += 2
        }
        return result
    }

    private fun calculateTrigFunction(function: String) {
        try {
            val angle = currentInput.toDouble()
            val result = when (function) {
                "sin" -> sin(Math.toRadians(angle))
                "cos" -> cos(Math.toRadians(angle))
                "tan" -> tan(Math.toRadians(angle))
                else -> 0.0
            }
            showResult(result)
        } catch (e: Exception) {
            resultText.text = "Erro"
        }
    }

    private fun calculatePower() {
        try {
            val parts = currentInput.split("^")
            if (parts.size == 2) {
                val base = parts[0].toDouble()
                val exponent = parts[1].toDouble()
                val result = base.pow(exponent)
                showResult(result)
            } else {
                resultText.text = "Erro"
            }
        } catch (e: Exception) {
            resultText.text = "Erro"
        }
    }

    private fun showResult(result: Double) {
        resultText.text = "Resultado: $result"
        currentInput = result.toString()
    }
}