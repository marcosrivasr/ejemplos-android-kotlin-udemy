package com.vidamrr.basededatosejemplo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class DetalleAlumno : AppCompatActivity() {

    var crud:AlumnoCRUD? = null

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

        bActualizar.setOnClickListener {
            crud?.updateAlumno(Alumno(id.text.toString(), nombre.text.toString()))
            startActivity(Intent(this, MainActivity::class.java))
        }

        bEliminar.setOnClickListener {
            crud?.deleteAlumno(Alumno(id.text.toString(), nombre.text.toString()))
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
