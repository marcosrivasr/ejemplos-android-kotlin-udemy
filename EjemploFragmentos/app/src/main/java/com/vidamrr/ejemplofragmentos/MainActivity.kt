package com.vidamrr.ejemplofragmentos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), MiFragmento.NombreListener {

    var nombreActual:TextView? = null
    var bAdd: Button? = null
    var bReplace: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombreActual = findViewById(R.id.tvNombre)
        bAdd = findViewById(R.id.bAdd)
        bReplace = findViewById(R.id.bReplace)

        val fragmentManager = supportFragmentManager


        bAdd?.setOnClickListener {

            val fragmentTransaction = fragmentManager.beginTransaction()

            val nuevoFragmento = MiFragmento()
            fragmentTransaction.add(R.id.container, nuevoFragmento)

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        bReplace?.setOnClickListener {

            val fragmentTransaction = fragmentManager.beginTransaction()

            val nuevoFragmento = Componente2()
            fragmentTransaction.replace(R.id.container, nuevoFragmento)

            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

    override fun obtenerNombre(nombre: String) {
        super.obtenerNombre(nombre)
        nombreActual?.text = nombre
    }
}
