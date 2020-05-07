package com.vidamrr.fragmentos01


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast


/**
 * A simple [Fragment] subclass.
 */
class BlankFragment : Fragment() {

    private var listener:BotonClickListener? = null
    private var boton:Button? = null

    interface BotonClickListener{
        fun botonClick(){
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater?.inflate(R.layout.fragment_blank, container, false)

        boton = view?.findViewById(R.id.boton)
        boton?.setOnClickListener {
            listener!!.botonClick()

        }

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        try {
            listener = context as BotonClickListener
        } catch (e: ClassCastException) {
            throw ClassCastException(context.toString() + " must implement OnArticleSelectedListener")
        }


    }


}// Required empty public constructor
