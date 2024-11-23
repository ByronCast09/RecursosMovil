package com.example.myapplication
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.Podometro

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_MyApplication)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        navegarFormulario()
        navegarImagen()
        navegarGPS()
        //camara()
        navegarCamara()
        navegarPodometro()
    }
    fun navegarFormulario (){
        val btnFormulario = findViewById<Button>(R.id.btnForm)
        btnFormulario.setOnClickListener(){
            val saltarVentanaFormu:Intent=Intent(this,VentanaForm::class.java)
            startActivity(saltarVentanaFormu)
        }
    }
    fun navegarImagen (){
        val btnImagen = findViewById<Button>(R.id.btnImg)
        btnImagen.setOnClickListener(){
            val saltarVentanaImg:Intent=Intent(this,Imagen::class.java)
            startActivity(saltarVentanaImg)
        }
    }
    fun navegarGPS (){
        val btnGPS = findViewById<Button>(R.id.btnGPS)
        btnGPS.setOnClickListener(){
            val saltarVentanaGps:Intent=Intent(this,Gps::class.java)
            startActivity(saltarVentanaGps)
        }
    }
    fun navegarPodometro (){
        val btnPodometro = findViewById<Button>(R.id.btnPodometro)
        btnPodometro.setOnClickListener(){
            val saltarVentanaPodometro:Intent=Intent(this, Podometro::class.java)
            startActivity(saltarVentanaPodometro)
        }
    }

    fun navegarCamara (){
        val btnCamara = findViewById<Button>(R.id.btnCamara)
        btnCamara.setOnClickListener(){
            val saltarVentanaCamara:Intent=Intent(this, Camara::class.java)
            startActivity(saltarVentanaCamara)
        }
    }
    /*
    fun camara(){
        val btnCamera = findViewById<Button>(R.id.btnCamara)
        btnCamera.setOnClickListener(){
            startActivity(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }

     */

    /*
    fun cambiarMensaje(){
        val btn1 = findViewById<Button>(R.id.button1)
        val txtv1 = findViewById<TextView>(R.id.txtView_Titulo)
        btn1.setOnClickListener(){
            txtv1.setText("Este es el nuevo mensaje")
        }
    }
    fun mayorEdad(){
        val btn2 = findViewById<Button>(R.id.button2)
        val txtv2 = findViewById<TextView>(R.id.txtView_Titulo)
        btn2.setOnClickListener(){
            txtv2.setText("Mayor de Edad")
        }
    }
    fun menorEdad(){
        val btn3 = findViewById<Button>(R.id.button3)
        val txtv3 = findViewById<TextView>(R.id.txtView_Titulo)
        btn3.setOnClickListener(){
            txtv3.setText("Menor de Edad")
        }
    }
    /*
    fun navegar (){
        val btnRe = findViewById<Button>(R.id.btn_regresar)
        btnRe.setOnClickListener(){
            val saltarVentana:Intent=Intent(this.Ventana3::class.java)
            startActivity(saltarVentana)
        }
    }

     */

     */
}