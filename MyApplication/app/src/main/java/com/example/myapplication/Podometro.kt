package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myapplication.R

class Podometro : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f

    private lateinit var tvStepsTaken: TextView
    private lateinit var btnStart: Button
    private lateinit var btnStop: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_podometro)

        // Inicializar vistas
        tvStepsTaken = findViewById(R.id.txt_Podo)
        btnStart = findViewById(R.id.btn_start)
        btnStop = findViewById(R.id.btn_Cancelar)

        loadData()

        // Configuración del SensorManager
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // Solicitar permisos si es necesario
        requestPermissions()

        // Configurar botones
        btnStart.setOnClickListener {
            startCounting() // Inicia el conteo de pasos
        }

        btnStop.setOnClickListener {
            stopCounting() // Detiene el conteo de pasos
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACTIVITY_RECOGNITION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
                    101
                )
            }
        }
    }

    private fun startCounting() {
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(this, "Sensor no disponible en este dispositivo", Toast.LENGTH_SHORT).show()
            return
        }

        running = true
        sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        Toast.makeText(this, "Comenzando conteo de pasos", Toast.LENGTH_SHORT).show()
    }

    private fun stopCounting() {
        if (running) {
            sensorManager?.unregisterListener(this)
            running = false
            Toast.makeText(this, "Conteo detenido", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if (running) {
            startCounting()
        }
    }

    override fun onPause() {
        super.onPause()
        stopCounting()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (running && event?.values != null && event.values.isNotEmpty()) {
            totalSteps = event.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()

            // Actualizar la interfaz de usuario con los pasos actuales
            tvStepsTaken.text = currentSteps.toString()
        }
    }

    private fun resetSteps() {
        tvStepsTaken.setOnLongClickListener {
            previousTotalSteps = totalSteps
            tvStepsTaken.text = "0"
            saveData()
            Toast.makeText(this, "Pasos reiniciados", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("key1", previousTotalSteps)
        editor.apply()
    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        previousTotalSteps = sharedPreferences.getFloat("key1", 0f)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // No es necesario manejar la precisión para este caso.
    }
}
