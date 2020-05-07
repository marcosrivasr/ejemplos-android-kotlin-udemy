package com.vidamrr.ejemplofoursquare

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.gson.Gson
import com.vidamrr.ejemplofoursquare.RecyclerView.AdaptadorCustom
import com.vidamrr.ejemplofoursquare.RecyclerView.ClickListener
import com.vidamrr.ejemplofoursquare.RecyclerView.LongClickListener
import kotlinx.android.synthetic.main.activity_dos.*

class Dos : AppCompatActivity() {

    /* Objeto Foursquare*/
    var fsq:Foursquare? = null

    /* RecyclerView */
    var lista: RecyclerView? = null
    var adaptador: AdaptadorCustom? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    var ubicacion:Ubicacion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dos)

        fsq = Foursquare(this)

        lista = findViewById(R.id.lista)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager

        ubicacion = Ubicacion(this, object: UbicacionInterface{
            override fun ubicacionEncontrada(locationResult: LocationResult?) {
                for(ubicacion in  locationResult?.locations!!){
                    obtenerLugares(ubicacion.latitude.toString(), ubicacion.longitude.toString())
                }
            }
        })
    }

    fun obtenerLugares(lat:String, lon:String){
        val queue = Volley.newRequestQueue(this)
        val url = "https://api.foursquare.com/v2/venues/search?ll="+lat+","+lon+"&oauth_token=" + fsq?.obtenerToken() + "&v=20180109"


        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->

            Log.d("RESPONSE HTTP", response)

            val gson = Gson()
            val venues = gson.fromJson(response, FoursquareRequest::class.java)
            Log.d("VENUES", venues.response?.venues?.size.toString())

            adaptador = AdaptadorCustom(venues.response?.venues!!, object : ClickListener {
                override fun onClick(vista: View, index: Int) {
                }

            }, object : LongClickListener {
                override fun longClick(vista: View, index: Int) {
                }

            })

            lista?.adapter = adaptador


        }, Response.ErrorListener {

        })

        queue.add(solicitud)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        this.ubicacion?.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }


    override fun onStart() {
        super.onStart()
        this.ubicacion?.inicializarUbicacion()
    }

    override fun onPause() {
        super.onPause()

        this.ubicacion?.detenerActualizacionUbicacion()
    }

}
