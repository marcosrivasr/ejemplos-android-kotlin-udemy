package com.vidamrr.ejemplobdoffline.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.vidamrr.ejemplobdoffline.R
import com.vidamrr.ejemplobdoffline.SQLite.AlumnoCRUD
import com.vidamrr.ejemplobdoffline.Utilidades.Network
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.vidamrr.ejemplobdoffline.Modelo.HttpAPIResponse
import com.vidamrr.ejemplobdoffline.Utilidades.CustomRequest
import com.vidamrr.ejemplobdoffline.Utilidades.HttpResponse


class NuevoAlumno : AppCompatActivity() {

    var crud: AlumnoCRUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_alumno)

        val id = findViewById<EditText>(R.id.etID)
        val nombre = findViewById<EditText>(R.id.etNombre)
        val bAdd = findViewById<Button>(R.id.bAdd)

        crud = AlumnoCRUD(this.applicationContext)

        bAdd.setOnClickListener {

            //crud?.newAlumno(Alumno(id.text.toString(), nombre.text.toString()))
            //startActivity(Intent(this, MainActivity::class.java))

            //TODO: 4. Sustituir crud con operacion HTTP
            val context = this.applicationContext
            var network = Network(this);
            val query = "?id=" + id.text.toString() + "&nombre=" + nombre.text.toString()
            var url = "http://192.168.0.22:80/alumnos/nuevoalumno" + query


           network.httpRequest(context, url, object : HttpResponse {

                override fun httpResponseSuccess(response: String) {
                    val gson = Gson()
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
