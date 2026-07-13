package com.convertix.app

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    // Tasas de cambio teniendo al Dólar (USD) como base fija
    private val tasasEnUSD = mapOf(
        "USD" to 1.0, "MXN" to 17.50, "EUR" to 0.88, "CAD" to 1.38, "JPY" to 155.20,
        "GBP" to 0.77, "CNY" to 7.25, "AUD" to 1.50, "CHF" to 0.90, "NOK" to 10.60,
        "AED" to 3.67, "SAR" to 3.75, "ZAR" to 18.20, "TRY" to 33.10, "DKK" to 6.55,
        "HKD" to 7.80, "SGD" to 1.35, "SEK" to 10.50, "VES" to 36.40, "NZD" to 1.63,
        "BOB" to 6.91, "PYG" to 7550.0, "UYU" to 39.50, "KRW" to 1380.0, "INR" to 83.50,
        "ARS" to 915.0, "CLP" to 930.0, "PEN" to 3.74
    )

    private val listaMonedas = arrayOf(
        "🇲🇽 MXN - Peso Mexicano", "🇺🇸 USD - Dólar EE.UU.", "🇪🇺 EUR - Euro", "🇨🇦 CAD - Dólar Canadiense",
        "🇯🇵 JPY - Yen Japonés", "🇬🇧 GBP - Libra Esterlina", "🇨🇳 CNY - Yuan Chino", "🇦🇺 AUD - Dólar Australiano",
        "🇨🇭 CHF - Franco Suizo", "🇳🇴 NOK - Corona Noruega", "🇦🇪 AED - Emiratos Árabes", "🇸🇦 SAR - Arabia Saudita",
        "🇿🇦 ZAR - Rand Sudafricano", "🇹🇷 TRY - Lira Turca", "🇩🇰 DKK - Corona Danesa", "🇭🇰 HKD - Dólar de Hong Kong",
        "🇸🇬 SGD - Dólar de Singapur", "🇸🇪 SEK - Corona Sueca", "🇻🇪 VES - Bolívar (Venezuela)", "🇳🇿 NZD - Dólar Neozelandés",
        "🇧🇴 BOB - Boliviano (Bolivia)", "🇵🇾 PYG - Guaraní (Paraguay)", "🇺🇾 UYU - Peso Uruguayo", "🇰🇷 KRW - Won Surcoreano",
        "🇮🇳 INR - Rupia India", "🇦🇷 ARS - Peso Argentino", "🇨🇱 CLP - Peso Chileno", "🇵🇪 PEN - Sol Peruano"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutPrincipal = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 50, 50, 50)

            setBackgroundColor(android.graphics.Color.parseColor("#F8F9FA"))
        }

        val titulo = TextView(this).apply {
            text = "🧮 Convertix"
            textSize = 28f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.parseColor("#1E272E"))
            gravity = android.view.Gravity.CENTER
            setPadding(0, 20, 0, 40)
        }
        layoutPrincipal.addView(titulo)

        val inputCantidad = EditText(this).apply {
            hint = "Escribe la cantidad aquí (ej. 60)"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            textSize = 18f
            setPadding(30, 30, 30, 30)
            setTextColor(android.graphics.Color.parseColor("#1E272E"))
        }
        layoutPrincipal.addView(inputCantidad)

        val txtOrigen = TextView(this).apply {
            text = "\nTengo esta moneda:"
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.parseColor("#485460"))
        }
        layoutPrincipal.addView(txtOrigen)

        val spinnerOrigen = Spinner(this)
        layoutPrincipal.addView(spinnerOrigen)

        val txtDestino = TextView(this).apply {
            text = "\nQuiero transformarla a:"
            textSize = 16f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.parseColor("#485460"))
        }
        layoutPrincipal.addView(txtDestino)

        val spinnerDestino = Spinner(this)
        layoutPrincipal.addView(spinnerDestino)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listaMonedas)
        spinnerOrigen.adapter = adapter
        spinnerDestino.adapter = adapter
        spinnerOrigen.setSelection(0)
        spinnerDestino.setSelection(1)

        val botonCalcular = Button(this).apply {
            text = "Calcular Conversión"
            textSize = 18f
            setBackgroundColor(android.graphics.Color.parseColor("#1E272E"))
            setTextColor(android.graphics.Color.WHITE)
        }

        val espacioBtn = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            setMargins(0, 50, 0, 50)
        }
        layoutPrincipal.addView(botonCalcular, espacioBtn)

        val cajaResultado = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)

            setBackgroundColor(android.graphics.Color.WHITE)
            visibility = View.GONE
        }

        val textoExplicativo = TextView(this).apply {
            textSize = 15f
            setTextColor(android.graphics.Color.parseColor("#7F8C8D"))
            gravity = android.view.Gravity.CENTER
        }
        cajaResultado.addView(textoExplicativo)

        val textoResultado = TextView(this).apply {
            textSize = 26f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(android.graphics.Color.parseColor("#1E272E"))
            gravity = android.view.Gravity.CENTER
        }
        cajaResultado.addView(textoResultado)
        layoutPrincipal.addView(cajaResultado)

        setContentView(layoutPrincipal)

        botonCalcular.setOnClickListener {
            val cantidadTexto = inputCantidad.text.toString()
            if (cantidadTexto.isEmpty()) {
                Toast.makeText(this, "Por favor, escribe una cantidad", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val numero = cantidadTexto.toDoubleOrNull()
            if (numero == null || numero <= 0) {
                Toast.makeText(this, "Introduce una cantidad válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val itemOrigen = spinnerOrigen.selectedItem.toString()
            val itemDestino = spinnerDestino.selectedItem.toString()

            // Corrige la extracción de las 3 letras de la moneda ignorando los emojis
            val codOrigen = itemOrigen.substring(itemOrigen.indexOf(" ") + 1, itemOrigen.indexOf(" ") + 4)
            val codDestino = itemDestino.substring(itemDestino.indexOf(" ") + 1, itemDestino.indexOf(" ") + 4)

            val tasaOrigen = tasasEnUSD[codOrigen] ?: 1.0
            val tasaDestino = tasasEnUSD[codDestino] ?: 1.0

            val cantidadEnUSD = numero / tasaOrigen
            val valorFinal = cantidadEnUSD * tasaDestino

            val nombreOrigenCompleto = itemOrigen.substring(itemOrigen.indexOf("-") + 2)
            val banderaDestino = itemDestino.substring(0, 2)

            textoExplicativo.text = "$numero $nombreOrigenCompleto equivalen a:"
            textoResultado.text = String.format(Locale.US, "%s %.2f %s", banderaDestino, valorFinal, codDestino)
            cajaResultado.visibility = View.VISIBLE
        }
    }
}
