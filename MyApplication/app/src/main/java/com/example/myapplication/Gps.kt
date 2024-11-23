package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Gps : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gps)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val btnGpsCalcular = findViewById<Button>(R.id.btnObtGps)
        val tvMensaje = findViewById<TextView>(R.id.textViewGps)

        btnGpsCalcular.setOnClickListener {
            if (checkPermissions()) {
                if (isLocationEnabled()) {
                    findLocation(tvMensaje)
                } else {
                    Toast.makeText(this, "Por favor, activa el GPS.", Toast.LENGTH_SHORT).show()
                }
            } else {
                requestPermissions()
            }
        }

        navegarGPS()
    }

    private fun navegarGPS() {
        val btnRegresar = findViewById<Button>(R.id.btnRegresarGps)
        btnRegresar.setOnClickListener {
            val regresar = Intent(this, MainActivity::class.java)
            startActivity(regresar)
        }
    }

    private fun findLocation(tvMensaje: TextView) {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions()
            return
        }

        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val dire = getAddress(location.latitude, location.longitude)
                tvMensaje.text = "Lat: ${location.latitude}, Lng: ${location.longitude} - $dire"
                Toast.makeText(this, "Ubicación obtenida", Toast.LENGTH_SHORT).show()
            } else {
                // Si no hay última ubicación, solicitamos una nueva
                val locationRequest = com.google.android.gms.location.LocationRequest.Builder(
                    com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY, 5000
                ).build()

                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    object : com.google.android.gms.location.LocationCallback() {
                        override fun onLocationResult(result: com.google.android.gms.location.LocationResult) {
                            val updatedLocation = result.lastLocation
                            if (updatedLocation != null) {
                                val updatedDire = getAddress(
                                    updatedLocation.latitude,
                                    updatedLocation.longitude
                                )
                                tvMensaje.text =
                                    "Lat: ${updatedLocation.latitude}, Lng: ${updatedLocation.longitude} - $updatedDire"
                                Toast.makeText(this@Gps, "Ubicación obtenida", Toast.LENGTH_SHORT).show()
                            } else {
                                tvMensaje.text = "No se pudo obtener la ubicación."
                            }
                        }
                    },
                    null
                )
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Error al obtener la ubicación: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAddress(lat: Double, lng: Double): String {
        return try {
            val geocoder = Geocoder(this)
            val list = geocoder.getFromLocation(lat, lng, 1)
            list?.get(0)?.getAddressLine(0) ?: "Dirección no disponible"
        } catch (e: Exception) {
            "Error al obtener la dirección"
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
            101
        )
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permisos concedidos. Intenta nuevamente.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show()
        }
    }
}
