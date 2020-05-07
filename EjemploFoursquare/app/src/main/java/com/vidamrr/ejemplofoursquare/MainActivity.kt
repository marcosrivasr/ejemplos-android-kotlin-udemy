package com.vidamrr.ejemplofoursquare

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.foursquare.android.nativeoauth.FoursquareOAuth

class MainActivity : AppCompatActivity() {

    var fsq:Foursquare? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fsq = Foursquare(this)

        val bLoguear = findViewById<Button>(R.id.bLoguear)

        if(fsq?.hayToken()!!){
            startActivity(Intent(this, Dos::class.java))
            finish()
        }

        bLoguear.setOnClickListener {
                fsq?.iniciarSesion()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        fsq?.validarActivityResult(requestCode, resultCode, data)
    }

}
