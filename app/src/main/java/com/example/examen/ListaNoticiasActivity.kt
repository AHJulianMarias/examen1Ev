package com.example.examen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach

class ListaNoticiasActivity: AppCompatActivity(), AdapterView.OnItemSelectedListener,
    AdapterView.OnItemClickListener {

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
    private val arraySpinner = resources.getStringArray(R.array.noticiasOpcion)
    private var arrayNoticiasTexto = resources.getStringArray(R.array.textoNoticias)
    private val cuerposNoticias = resources.getStringArray(R.array.cuerposNoticias)

    private val imagenes = intArrayOf(R.mipmap.ic_cyl,R.mipmap.ic_cultura,R.mipmap.ic_economia,R.mipmap.ic_politica,R.mipmap.ic_deportes)
    lateinit var listViewNoticias: ListView
    lateinit var adapter:ArrayAdapter<String>
    lateinit var iterator: MutableIterator<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spinner)
        val intentAvion = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneReceiver,intentAvion)

        listViewNoticias = findViewById(R.id.listViewNoticias)
        adapter = ArrayAdapter(this, R.layout.fila_list, R.id.textViewFila,arrayNoticiasTexto)
        listViewNoticias.adapter = adapter
        val selectorNoticias = findViewById<Spinner>(R.id.spinner)
        val adaptadorPerso = AdaptadorPerso(this,R.layout.layout_spinner,arraySpinner)
        selectorNoticias.adapter = adaptadorPerso
        selectorNoticias.onItemSelectedListener = this
        listViewNoticias.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, refId ->
                val selectedNew = arrayNoticiasTexto[refId.toInt()]
                val seleccionCuerpo = cuerposNoticias[refId.toInt()]
                val intent = Intent(this, contenidoNoticiasActivity::class.java)
                intent.putExtra("tituloNoticia",selectedNew)
                intent.putExtra("cuerpoNoticia",seleccionCuerpo)
                startActivity(intent)


            }

    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val noticia = view?.findViewById<TextView>(R.id.nombreNoticia)
        when(noticia?.text.toString()){
            "Todas"->{



                Log.d("Noticia seleccionada", "onItemSelected: Todas")


            }

            "Cultura" ->{
                Log.d("Noticia seleccionada", "onItemSelected: Cultura")

            }
            "Economia" ->{

                Log.d("Noticia seleccionada", "onItemSelected: Economia")

            }
            "Politica" ->{

            }
            "Deportes" ->{
                Log.d("Noticia seleccionada", "onItemSelected: Deportes")



            }

        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    private inner class AdaptadorPerso(context: Context, resource: Int, objects: Array<String>) : ArrayAdapter<String>(context, resource, objects) {

        //Dos primeros override para cambiar lo que devuelven
        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Llama a la función para crear la fila personalizada y la devuelve
            return crearFilaPersonalizada(position, convertView, parent)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            // Este método se llama para mostrar una vista personalizada en el elemento seleccionado
            // Llama a la función para crear la fila personalizada y la devuelve
            return crearFilaPersonalizada(position, convertView, parent)
        }

        private fun crearFilaPersonalizada(
            position: Int,
            convertView: View?,
            parent: ViewGroup
        ): View {

            val layoutInflater = LayoutInflater.from(context)

            val rowView =
                convertView ?: layoutInflater.inflate(R.layout.layout_spinner, parent, false)

            //Fijamos el nombre del equipo
            rowView.findViewById<TextView>(R.id.nombreNoticia).text = arraySpinner[position]
            //Fijamos la imagen del equipo
            rowView.findViewById<ImageView>(R.id.imagenEquipo).setImageResource(imagenes[position])
            // Devuelve la vista de fila personalizada
            return rowView
        }

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneReceiver)
    }

}