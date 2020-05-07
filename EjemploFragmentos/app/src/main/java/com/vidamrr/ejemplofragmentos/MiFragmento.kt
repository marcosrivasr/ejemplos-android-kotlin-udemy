package com.vidamrr.ejemplofragmentos


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class MiFragmento : Fragment() {

    var boton:Button? = null
    var nombre:EditText? = null
    var listener: NombreListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mi_fragmento, container, false)

        boton = view.findViewById(R.id.boton)
        nombre = view.findViewById(R.id.etNombre)

        boton?.setOnClickListener {
            //Toast.makeText(view.context, nombre?.text.toString(), Toast.LENGTH_SHORT).show()
            val nombreActual = nombre?.text.toString()

            listener?.obtenerNombre(nombreActual)
        }

        return view
    }

    interface NombreListener{
        fun obtenerNombre(nombre:String){
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        try{
            listener = context as NombreListener
        }catch (e: ClassCastException){
            throw ClassCastException(context.toString() + " debes implementar la interfaz")
        }

    }


}
