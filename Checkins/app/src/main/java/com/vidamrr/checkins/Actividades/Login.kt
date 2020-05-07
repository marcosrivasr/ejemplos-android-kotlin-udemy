package com.vidamrr.checkins.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.vidamrr.checkins.Foursquare.Foursquare
import com.vidamrr.checkins.R

class Login : AppCompatActivity() {

    var foursquare: Foursquare? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val bLogin = findViewById<Button>(R.id.bLogin)
        foursquare = Foursquare(this, PantallaPrincipal())

        if(foursquare?.hayToken()!!){
            foursquare?.navegarSiguienteActividad()
        }

        bLogin.setOnClickListener {
            foursquare?.iniciarSesion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        foursquare?.validarActivityResult(requestCode, resultCode, data)
    }
}
