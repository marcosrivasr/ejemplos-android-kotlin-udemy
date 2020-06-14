package com.vidamrr.ejemplobdoffline.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.vidamrr.ejemplobdoffline.Modelo.Alumno
import com.vidamrr.ejemplobdoffline.Modelo.HttpAPIResponse
import com.vidamrr.ejemplobdoffline.R
import com.vidamrr.ejemplobdoffline.SQLite.AlumnoCRUD
import com.vidamrr.ejemplobdoffline.Utilidades.HttpResponse
import com.vidamrr.ejemplobdoffline.Utilidades.Network

class DetalleAlumno : AppCompatActivity() {

    var crud: AlumnoCRUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_alumno)

        val id = findViewById<EditText>(R.id.etID)
        val nombre = findViewById<EditText>(R.id.etNombre)
        val bActualizar = findViewById<Button>(R.id.bActualizar)
        val bEliminar = findViewById<Button>(R.id.bEliminar)

        val index = intent.getStringExtra("ID")




        crud = AlumnoCRUD(this)

        val alumno = crud?.getAlumno(index)

        id.setText(alumno!!.id, TextView.BufferType.EDITABLE)
        nombre.setText(alumno!!.nombre, TextView.BufferType.EDITABLE)

        //TODO: 5. Sustituir crud con operacion HTTP
        val context = this.applicationContext
        var network = Network(this);
        val gson = Gson()


        bActualizar.setOnClickListener {
            //crud?.updateAlumno(Alumno(id.text.toString(), nombre.text.toString()))
            //startActivity(Intent(this, MainActivity::class.java))

            val query = "?id=" + id.text.toString() + "&nombre=" + nombre.text.toString()
            val url = "http://192.168.0.22:80/alumnos/actualizaralumno" + query

            network.httpRequest(context, url, object : HttpResponse {

                override fun httpResponseSuccess(response: String) {
                    Log.d("response", response)

                    val message = gson.fromJson(response, HttpAPIResponse::class.java)
                    Toast.makeText(context, message.response, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainActivity::class.java))
                }

                override fun httpErrorResponse(response: String) {
                    Log.e("error response", response)
                    Toast.makeText(context, "Hubo un problema al enviar la solicitud", Toast.LENGTH_SHORT).show()
                }
            })
        }

        bEliminar.setOnClickListener {
            //crud?.deleteAlumno(Alumno(id.text.toString(), nombre.text.toString()))
            //startActivity(Intent(this, MainActivity::class.java))
            val query = "?id=" + id.text.toString() + "&nombre=" + nombre.text.toString()
            val url = "http://192.168.0.22:80/alumnos/eliminaralumno" + query
            network.httpRequest(context, url, object : HttpResponse {

                override fun httpResponseSuccess(response: String) {

                    val message = gson.fromJson(response, HttpAPIResponse::class.java)
                    Toast.makeText(context, message.response, Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainActivity::class.java))
                }

                override fun httpErrorResponse(response: String) {
                    Log.e("error response", response)
                    Toast.makeText(context, "Hubo un problema al enviar la solicitud", Toast.LENGTH_SHORT).show()
                }
            })

        }
    }
}
