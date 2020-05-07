package com.vidamrr.appclima

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var tvCiudad:TextView? = null
    var tvGrados:TextView? = null
    var tvEstatus:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvCiudad = findViewById(R.id.tvCiudad)
        tvGrados = findViewById(R.id.tvGrados)
        tvEstatus = findViewById(R.id.tvEstatus)

        val ciudad = intent.getStringExtra("com.vidamrr.appclima.ciudades.CIUDAD")



        val ciudadmx = Ciudad("Ciudad de México", 15, "Soleado")
        val ciudadBerlin = Ciudad("Berlín", 30, "Cielo despejado")

        if(ciudad == "ciudad-mexico"){
            // mostrar información ciudadmx
            tvCiudad?.text = ciudadmx.nombre
            tvGrados?.text = ciudadmx.grados.toString() + "º"
            tvEstatus?.text = ciudadmx.estatus

        }else if(ciudad == "ciudad-berlin"){
            // mostrar información ciudadberlin
            tvCiudad?.text = ciudadBerlin.nombre
            tvGrados?.text = ciudadBerlin.grados.toString() + "º"
            tvEstatus?.text = ciudadBerlin.estatus

        }else{
            Toast.makeText(this, "No se encuentra la información", Toast.LENGTH_SHORT).show()
        }
    }
}
