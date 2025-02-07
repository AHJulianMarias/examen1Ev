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


}