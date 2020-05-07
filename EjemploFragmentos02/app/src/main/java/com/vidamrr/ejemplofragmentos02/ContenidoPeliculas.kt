package com.vidamrr.ejemplofragmentos02


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView


class ContenidoPeliculas : Fragment() {

    var vista:View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_contenido_peliculas, container, false)

        cambiarFoto()

        return vista!!
    }


    fun nuevaInstancia(index: Int): ContenidoPeliculas {
        val f = ContenidoPeliculas();

        val args = Bundle();
        args.putInt("INDEX", index);
        f.arguments = args

        return f;
    }

    fun obtenerIndex(): Int {
        val index = arguments?.getInt("INDEX", 0)!!;
        return index
    }

    fun cambiarFoto(){

        val foto = vista!!.findViewById<ImageView>(R.id.ivFoto)

        foto.setImageResource(ListaPeliculas.peliculas?.get(obtenerIndex())?.imagen!!)
    }

}
