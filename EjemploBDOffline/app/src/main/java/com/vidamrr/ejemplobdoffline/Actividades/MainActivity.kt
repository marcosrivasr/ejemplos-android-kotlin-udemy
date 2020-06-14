package com.vidamrr.ejemplobdoffline.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.vidamrr.ejemplobdoffline.Modelo.Alumno
import com.vidamrr.ejemplobdoffline.R
import com.vidamrr.ejemplobdoffline.RecyclerView.AdaptadorCustom
import com.vidamrr.ejemplobdoffline.RecyclerView.ClickListener
import com.vidamrr.ejemplobdoffline.RecyclerView.LongClickListener
import com.vidamrr.ejemplobdoffline.SQLite.AlumnoCRUD
import com.vidamrr.ejemplobdoffline.Utilidades.HttpResponse
import com.vidamrr.ejemplobdoffline.Utilidades.Network
import com.vidamrr.ejemplobdoffline.Modelo.Alumnos

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

        val network = Network(this)
        val activity = this.applicationContext
        val gson = Gson()

        crud = AlumnoCRUD(this)
        alumnos = crud?.getAlumnos()

        network.httpRequest(
                activity,
                "http://192.168.0.22:80/alumnos/",
                object: HttpResponse {

            override fun httpResponseSuccess(response: String) {
                Log.d("response", response)

                val alumnosAPI = gson.fromJson(response, Alumnos::class.java).items

                for (alumno in alumnos!!){
                    crud?.deleteAlumno(alumno)
                }
                for ( alumno in alumnosAPI!! ){
                    crud?.newAlumno(Alumno(alumno.id!!, alumno.nombre!!))
                }

                alumnos = crud?.getAlumnos()
                configurarAdaptador(alumnos!!)
            }

            override fun httpErrorResponse(response: String) {
                Toast.makeText(activity, "Error al hacer la solicitud HTTP", Toast.LENGTH_SHORT).show()
                configurarAdaptador(alumnos!!)
            }
        } )


    }

    fun configurarAdaptador(data: ArrayList<Alumno>){
        this.adaptador = AdaptadorCustom(data!!, object : ClickListener {
            override fun onClick(vista: View, index: Int) {
                //click
                val intent = Intent(applicationContext, DetalleAlumno::class.java)
                intent.putExtra("ID", data!!.get(index).id)
                startActivity(intent)
            }
        }, object : LongClickListener {
            override fun longClick(vista: View, index: Int) {}
        })

        this.lista?.adapter = this.adaptador
    }
}
