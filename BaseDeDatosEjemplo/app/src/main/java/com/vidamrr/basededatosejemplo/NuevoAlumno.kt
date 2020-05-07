package com.vidamrr.basededatosejemplo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class NuevoAlumno : AppCompatActivity() {

    var crud:AlumnoCRUD? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_alumno)

        val id = findViewById<EditText>(R.id.etID)
        val nombre = findViewById<EditText>(R.id.etNombre)
        val bAdd = findViewById<Button>(R.id.bAdd)

        crud = AlumnoCRUD(this)

        bAdd.setOnClickListener {
            crud?.newAlumno(Alumno(id.text.toString(), nombre.text.toString()))
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
