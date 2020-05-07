package com.vidamrr.tutorialrecyclerview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var personas:ArrayList<Persona>? = null

    var lista:RecyclerView? = null
    var layoutManager:RecyclerView.LayoutManager? = null
    var adaptador:AdaptadorCustom? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personas = ArrayList()
        personas?.add(Persona("Marcos", R.drawable.foto01))
        personas?.add(Persona("Julia", R.drawable.foto02))
        personas?.add(Persona("Camilo", R.drawable.foto03))
        personas?.add(Persona("Luis", R.drawable.foto04))
        personas?.add(Persona("Celia", R.drawable.foto05))
        personas?.add(Persona("Jessica", R.drawable.foto06))
        personas?.add(Persona("Rebeca", R.drawable.foto07))

        lista = findViewById(R.id.lista)
        layoutManager = LinearLayoutManager(this)
        adaptador = AdaptadorCustom(personas!!, object: ClickListener{
            override fun onClick(vista: View, posicion: Int) {
                Toast.makeText(applicationContext, personas?.get(posicion)?.nombre, Toast.LENGTH_SHORT).show()
            }

        })
        lista?.layoutManager = layoutManager
        lista?.adapter = adaptador
    }
}
