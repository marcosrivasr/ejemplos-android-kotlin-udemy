package com.vidamrr.ejemplofragmentos02

import android.content.res.Configuration
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class Detalles : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            finish()
            return
        }

        if(savedInstanceState == null){
            val fDetalles = ContenidoPeliculas()
            fDetalles.arguments = intent.extras
            supportFragmentManager.beginTransaction().add(R.id.container, fDetalles).commit()
        }

        /*val index = intent.getIntExtra("INDEX", 0)

        val foto = findViewById<ImageView>(R.id.ivFoto)

        foto.setImageResource(ListaPeliculas.peliculas?.get(index)?.imagen!!)
        */
    }
}
