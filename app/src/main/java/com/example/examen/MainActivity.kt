package com.example.examen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageSwitcher
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity



class MainActivity : AppCompatActivity() {

    lateinit var boton: ImageButton


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
        Toast.makeText(this,texto,LENGTH_LONG).show()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        boton = findViewById(R.id.botonIniciar)

        val intentAvion = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneReceiver,intentAvion)

        boton.setOnClickListener{
            val intent = Intent(this, ListaNoticiasActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneReceiver)
    }


}