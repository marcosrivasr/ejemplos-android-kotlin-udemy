package com.vidamrr.miprimeraapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.view.*

class Main : AppCompatActivity() {

    val etNombre = findViewById<EditText>(R.id.etNombre)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bSaludar = findViewById<Button>(R.id.bSaludar)

        val cbDev = findViewById<CheckBox>(R.id.cbDeveloper)

        bSaludar.setOnClickListener(View.OnClickListener {

            if(validaDato()){
                val textoBienvenida = "Bienvenido a la app, " + etNombre.text
                val textoDev = ", marcaste la casilla de developer"

                if(cbDev.isChecked){
                    Toast.makeText(this, textoBienvenida + textoDev, Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this, textoBienvenida, Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this, "Escribe tu nombre para saludarte :/", Toast.LENGTH_SHORT).show()
            }
        })

    }

    fun validaDato(): Boolean{
        val nombreUsuario = etNombre.text

        if(nombreUsuario.isNullOrEmpty()){
            return false
        }

        return true
    }
}
