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


    private var arrayNoticiasTexto = arrayListOf("Nuevo videojuego", "Bitcoin sube mucho","Pablo Motos enano", "Miguel 100kg en banca")

    private lateinit var arraySpinner: Array<String>
    private lateinit var cuerposNoticias:Array<String>
    private lateinit var imagenes:Array<Int>
    lateinit var listViewNoticias: ListView
    lateinit var adapter:ArrayAdapter<String>

    lateinit var iterator: MutableIterator<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_spinner)
        arraySpinner = getResources().getStringArray(R.array.noticiasOpcion)
        imagenes = arrayOf(R.mipmap.ic_cyl,R.mipmap.ic_cultura,R.mipmap.ic_economia,R.mipmap.ic_politica,R.mipmap.ic_deportes)
        cuerposNoticias = getResources().getStringArray(R.array.cuerposNoticias)



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
            arraySpinner[0] -> {
                Log.d("Noticia seleccionada", "onItemSelected: Todas")
                // Mostrar todas las noticias
                arrayNoticiasTexto = arrayListOf("Nuevo videojuego", "Bitcoin sube mucho", "Pablo Motos enano", "Miguel 100kg en banca")
            }
            arraySpinner[1] -> {
                Log.d("Noticia seleccionada", "onItemSelected: Cultura")
                // Mostrar noticias relacionadas con Cultura (ejemplo: vacío para este caso)
                arrayNoticiasTexto = arrayListOf("Nuevo videojuego")
            }
            arraySpinner[2] -> {
                Log.d("Noticia seleccionada", "onItemSelected: Economia")
                // Mostrar noticias relacionadas con Economía
                arrayNoticiasTexto = arrayListOf("Bitcoin sube mucho")
            }
            arraySpinner[3] -> {
                Log.d("Noticia seleccionada", "onItemSelected: Politica")
                // Mostrar noticias relacionadas con Política
                arrayNoticiasTexto = arrayListOf("Bitcoin sube mucho") // Según tu ejemplo
            }
            arraySpinner[4] -> {
                Log.d("Noticia seleccionada", "onItemSelected: Deportes")
                // Mostrar noticias relacionadas con Deportes
                arrayNoticiasTexto = arrayListOf("Miguel 100kg en banca")
            }
        }
        adapter.clear()
        adapter.addAll(arrayNoticiasTexto)
        adapter.notifyDataSetChanged()
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
            // Crea un objeto LayoutInflater para inflar la vista personalizada desde un diseño XML
            val layoutInflater = LayoutInflater.from(context)
            //Declaro una vista de mi fila, y la preparo para inflarla con datos
            // Los parametros son: XML descriptivo
            // Vista padre
            // Booleano que indica si se debe ceñir a las características del padre
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

}