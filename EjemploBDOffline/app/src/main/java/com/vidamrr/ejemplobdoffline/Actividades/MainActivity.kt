package com.vidamrr.ejemplobdoffline.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.vidamrr.ejemplobdoffline.Modelo.Alumno
import com.vidamrr.ejemplobdoffline.R
import com.vidamrr.ejemplobdoffline.RecyclerView.AdaptadorCustom
import com.vidamrr.ejemplobdoffline.RecyclerView.ClickListener
import com.vidamrr.ejemplobdoffline.RecyclerView.LongClickListener
import com.vidamrr.ejemplobdoffline.SQLite.AlumnoCRUD

class MainActivity : AppCompatActivity() {

    var lista: RecyclerView? = null
    var adaptador: AdaptadorCustom? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    var alumnos:ArrayList<Alumno>? = null
    var crud: AlumnoCRUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        lista = findViewById(R.id.lista)

        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager


        fab.setOnClickListener {
            startActivity(Intent(this, NuevoAlumno::class.java))
        }

        crud = AlumnoCRUD(this)

        alumnos = crud?.getAlumnos()

        adaptador = AdaptadorCustom(alumnos!!, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                //click
                val intent = Intent(applicationContext, DetalleAlumno::class.java)
                intent.putExtra("ID", alumnos!!.get(index).id)
                startActivity(intent)
            }
        }, object : LongClickListener {
            override fun longClick(vista: View, index: Int) {}
        })

        lista?.adapter = adaptador
    }
}
