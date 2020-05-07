package com.vidamrr.ejemplofragmentos02


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_lista_peliculas.view.*


class ListaPeliculas : Fragment() {

    companion object {
        var peliculas:ArrayList<Pelicula>? = null
    }


    var nombrePeliculas:ArrayList<String>? = null

    var lista:ListView? = null

    var hayDoblePanel = false

    var posicionActual = 0


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        configurarListView()

        val frameDetalles = activity!!.findViewById<FrameLayout>(R.id.detalles)
        hayDoblePanel = frameDetalles != null && frameDetalles.visibility == View.VISIBLE

        if(savedInstanceState != null){
            posicionActual = savedInstanceState.getInt("INDEX", 0)
        }

        if(hayDoblePanel){
            lista?.choiceMode = ListView.CHOICE_MODE_SINGLE
            mostrarDetalles(posicionActual)
        }

    }

    private fun configurarListView() {
        peliculas = ArrayList()
        peliculas?.add(Pelicula("The Terminator", R.drawable.poster01))
        peliculas?.add(Pelicula("Blade Runner", R.drawable.poster02))
        peliculas?.add(Pelicula("Titanic", R.drawable.poster03))
        peliculas?.add(Pelicula("Wonder Woman", R.drawable.poster04))

        nombrePeliculas = obtenerNombrePeliculas(peliculas!!)

        val adaptador = ArrayAdapter(activity, android.R.layout.simple_list_item_activated_1, nombrePeliculas)

        lista = activity!!.findViewById(R.id.lista)
        lista?.adapter = adaptador

        lista?.setOnItemClickListener { adapterView, view, i, l ->
            mostrarDetalles(i)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val vista = inflater.inflate(R.layout.fragment_lista_peliculas, container, false)

        return vista
    }

    fun mostrarDetalles(index: Int){
        posicionActual = index

        if(hayDoblePanel){

            lista?.setItemChecked(index, true)

            var fDetalles = activity!!.supportFragmentManager.findFragmentById(R.id.detalles) as? ContenidoPeliculas

            if(fDetalles == null || fDetalles.obtenerIndex() != index){
                fDetalles = ContenidoPeliculas().nuevaInstancia(index)

                val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()

                fragmentTransaction.replace(R.id.detalles, fDetalles)

                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

                fragmentTransaction.commit()

            }


        }else{
            val intent = Intent(activity, Detalles::class.java)
            intent.putExtra("INDEX", index)
            startActivity(intent)
        }
    }


    fun obtenerNombrePeliculas(peliculas:ArrayList<Pelicula>):ArrayList<String>{
        val nombres = ArrayList<String>()

        for (pelicula in peliculas){
            nombres.add(pelicula.nombre)
        }

        return nombres
    }




}
