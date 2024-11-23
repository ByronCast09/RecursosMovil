package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

class Camara : AppCompatActivity() {
    private lateinit var imageView: ImageView

    companion object {
        const val CAMERA_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camara)

        val buttonOpenCamera: Button = findViewById(R.id.btn_open_camera)
        imageView = findViewById(R.id.imageView)

        // Verificar permisos y abrir cámara
        buttonOpenCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }
        }


        findViewById<Button>(R.id.btnRegresarCam).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
    }

    // Obtener la foto y mostrarla en el ImageView
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val foto: Bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(foto)
        }
    }

    // Manejar el resultado de los permisos
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Permiso para usar la cámara denegado", Toast.LENGTH_SHORT).show()
        }
    }
}
