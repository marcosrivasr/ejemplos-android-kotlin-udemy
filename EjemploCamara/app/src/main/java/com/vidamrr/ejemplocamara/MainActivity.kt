package com.vidamrr.ejemplocamara

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import java.io.File
import java.net.URI
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    var fotos:Fotos? = null

    var ivFoto:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bTomar = findViewById<Button>(R.id.bTomar)
        val bSeleccionar = findViewById<Button>(R.id.bSeleccionar)

        ivFoto = findViewById(R.id.ivFoto)

        fotos = Fotos(this, ivFoto!!)


        bTomar.setOnClickListener {
            //dispararIntentTomarFoto()
            //tomarFoto()
            fotos?.tomarFoto()
        }

        bSeleccionar.setOnClickListener {
            //seleccionarFoto()
            fotos?.seleccionarFoto()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //requestPermissionsResult(requestCode, permissions, grantResults)
        fotos?.requestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //activityResult(requestCode, resultCode, data)
        fotos?.activityResult(requestCode, resultCode, data)

    }
}
