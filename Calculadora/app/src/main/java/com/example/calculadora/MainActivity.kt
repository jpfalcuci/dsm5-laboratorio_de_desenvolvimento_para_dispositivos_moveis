package com.example.calculadora

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private var currentExpression = ""
    private var lastNumeric = false
    private var lastDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.tvDisplay)

        // Configurar os botões
        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
            R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9
        )
        for (id in buttons) {
            findViewById<Button>(id).setOnClickListener { onDigitPressed((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btnAdd).setOnClickListener { onOperatorPressed("+") }
        findViewById<Button>(R.id.btnSubtract).setOnClickListener { onOperatorPressed("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { onOperatorPressed("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { onOperatorPressed("/") }
        findViewById<Button>(R.id.btnEquals).setOnClickListener { onEqualsPressed() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { onClear() }
    }

    private fun onDigitPressed(digit: String) {
        currentExpression += digit
        display.text = currentExpression
        lastNumeric = true
    }

    private fun onOperatorPressed(operator: String) {
        if (lastNumeric && !lastDot) {
            currentExpression += operator
            display.text = currentExpression
            lastNumeric = false
            lastDot = false
        }
    }

    private fun onEqualsPressed() {
        try {
            val result = evaluateExpression(currentExpression)
            // Verifica se o resultado é um número inteiro
            val displayResult = if (result == result.toInt().toDouble()) {
                result.toInt().toString() // Converte para inteiro e exibe sem o ".0"
            } else {
                result.toString() // Exibe como decimal
            }
            display.text = displayResult
            currentExpression = displayResult
        } catch (e: Exception) {
            display.text = "Erro"
        }
    }

    private fun onClear() {
        currentExpression = ""
        display.text = "0"
        lastNumeric = false
        lastDot = false
    }

    private fun evaluateExpression(expression: String): Double {
        val operators = Stack<Char>()
        val values = Stack<Double>()

        var i = 0
        while (i < expression.length) {
            val ch = expression[i]

            when {
                ch.isDigit() -> {
                    var num = ""
                    while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                        num += expression[i]
                        i++
                    }
                    values.push(num.toDouble())
                    i-- // Corrige o índice após sair do loop
                }
                ch == '(' -> operators.push(ch)
                ch == ')' -> {
                    while (operators.isNotEmpty() && operators.peek() != '(') {
                        values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
                    }
                    operators.pop()
                }
                ch == '+' || ch == '-' || ch == '*' || ch == '/' -> {
                    while (operators.isNotEmpty() && hasPrecedence(ch, operators.peek())) {
                        values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
                    }
                    operators.push(ch)
                }
            }
            i++
        }

        while (operators.isNotEmpty()) {
            values.push(applyOperation(operators.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    private fun hasPrecedence(op1: Char, op2: Char): Boolean {
        if (op2 == '(' || op2 == ')') return false
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false
        return true
    }

    private fun applyOperation(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            '*' -> a * b
            '/' -> {
                if (b == 0.0) throw UnsupportedOperationException("Cannot divide by zero")
                a / b
            }
            else -> 0.0
        }
    }

}
