package com.example.appviagem

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val distancia = findViewById<EditText>(R.id.editDistancia)
        val pedagio = findViewById<EditText>(R.id.editPedagio)
        val velocidade = findViewById<EditText>(R.id.editVelocidade)
        val resultado = findViewById<TextView>(R.id.textResultado)
        val btn = findViewById<Button>(R.id.buttonCalcular)

        btn.setOnClickListener {
            if (distancia.text.isEmpty() || pedagio.text.isEmpty() || velocidade.text.isEmpty()) {
                Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val kilometragem = distancia.text.toString().replace(",", ".").toFloat()
            val qtdPedagio = pedagio.text.toString().toInt()
            val velocidadeMedia = velocidade.text.toString().replace(",", ".").toFloat()

            if (velocidadeMedia !in 60f..130f) {
                Toast.makeText(this, "A velocidade deve estar entre 60 e 130 km/h", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val precoCombustivel = 5.85
            val precoPedagio = 10.50
            val rendimentoCarro = 12

            val litros = kilometragem / rendimentoCarro
            val custoCombustivel = litros * precoCombustivel
            val custoPedagio = qtdPedagio * precoPedagio
            val custoTotal = custoCombustivel + custoPedagio
            val tempoGasto = kilometragem / velocidadeMedia

            resultado.text = "Distância total: ${String.format("%.2f", kilometragem)} km\n" +
                    "Consumo de combustível ($rendimentoCarro Km/L): ${String.format("%.2f", litros)} litros\n" +
                    "Custo de combustível (R$$precoCombustivel /L): R$${String.format("%.2f", custoCombustivel)}\n" +
                    "Quantidade de pedágios: $qtdPedagio\n" +
                    "Custo com pedágio (R$$precoPedagio /un): R$${String.format("%.2f", custoPedagio)}\n" +
                    "Custo total: R$${String.format("%.2f", custoTotal)}\n" +
                    "Velocidade média: ${String.format("%.2f", velocidadeMedia)} km/h\n" +
                    "Tempo de viagem: ${String.format("%.2f", tempoGasto)} h"
        }
    }
}