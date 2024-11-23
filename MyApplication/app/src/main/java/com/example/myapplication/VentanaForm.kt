package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class VentanaForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ventana_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navegarFormulario()
        layoutForm()
    }

    fun navegarFormulario() {
        val btnRegres = findViewById<Button>(R.id.buttonRegresar)
        btnRegres.setOnClickListener() {
            val regresarHome: Intent = Intent(this, MainActivity::class.java)
            startActivity(regresarHome)
        }

    }
    fun layoutForm() {
        val btn_gps = findViewById<Button>(R.id.btn_start)
        val tv_nombre = findViewById<TextView>(R.id.txt_nombre)
        btn_gps.setOnClickListener() {
            val message = "Nombre: ${tv_nombre.text}, Es estudiante"
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}