package com.example.examen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge

import androidx.appcompat.app.AppCompatActivity


class contenidoNoticiasActivity: AppCompatActivity() {
    private val airplaneReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("ModoAvion", "Entra en onReceive")
            getAirplane()
        }
    }

    private fun getAirplane(){
        Log.d("ModoAvion", "Revisa getAirplane")
        val airplaneMode = Settings.Global.getInt(contentResolver, Settings.Global.AIRPLANE_MODE_ON,0) == 1
        Log.d("ModoAvion", "valor $airplaneMode")
        val texto =if (airplaneMode) getString(R.string.modo_avion_activado) else getString(R.string.modo_avion_no_activado)
        Toast.makeText(this,texto, LENGTH_LONG).show()
    }


    lateinit var viewTitulo: TextView
    lateinit var viewContenido:TextView
    val KEY_TITULO:String = "key_titulo"
    val KEY_CONTENIDO:String = "key_contenido"
    lateinit var botonCompartir: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contenido)
        viewTitulo = findViewById(R.id.textViewTitulo)
        viewContenido = findViewById(R.id.textViewContenido)

        //AVION
        val intentAvion = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneReceiver,intentAvion)
        if(savedInstanceState != null){
            viewTitulo.text = savedInstanceState.getString(KEY_TITULO)
            viewContenido.text = savedInstanceState.getString(KEY_CONTENIDO)
        } else{
            viewTitulo.text = intent.getStringExtra("tituloNoticia")
            viewContenido.text = intent.getStringExtra("cuerpoNoticia")
        }

        botonCompartir = findViewById(R.id.buttonCompartir)

        botonCompartir.setOnClickListener{
            val intentText= getString(R.string.compartir_con)
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,
                getString(R.string.titulo_contenido, viewTitulo.text, viewContenido.text))
            startActivity(Intent.createChooser(intent, intentText))


        }


    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardamos el texto actual del EditText en el Bundle
        outState.putString(KEY_TITULO, viewTitulo.text.toString())
        // Guardamos el progreso actual del SeekBar en el Bundle
        outState.putString(KEY_CONTENIDO, viewContenido.text.toString())
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneReceiver)
    }
//    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//        // Este bloque de código se ejecuta cuando el usuario ha respondido a la solicitud de permiso.
//        if (isGranted) {
//            // Si el permiso fue concedido, entonces podemos proceder con la acción que requiere dicho permiso.
////            takePicturePreviewLauncher.launch(null)
//        } else {
//            // Si el permiso fue denegado, mostramos un mensaje al usuario.
//            Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_LONG).show()
//        }
//    }
}