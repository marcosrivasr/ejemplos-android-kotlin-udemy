package com.vidamrr.checkins.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.google.android.gms.location.LocationResult
import com.google.gson.Gson
import com.vidamrr.checkins.Foursquare.Category
import com.vidamrr.checkins.Foursquare.Foursquare
import com.vidamrr.checkins.Foursquare.Venue
import com.vidamrr.checkins.Interfaces.ObtenerVenuesInterface
import com.vidamrr.checkins.Interfaces.UbicacionListener
import com.vidamrr.checkins.R
import com.vidamrr.checkins.RecyclerViewPrincipal.AdaptadorCustom
import com.vidamrr.checkins.RecyclerViewPrincipal.ClickListener
import com.vidamrr.checkins.RecyclerViewPrincipal.LongClickListener
import com.vidamrr.checkins.Utilidades.Ubicacion

class VenuesPorCategoria : AppCompatActivity() {

    var ubicacion: Ubicacion? = null
    var foursquare: Foursquare? = null

    var lista: RecyclerView? = null
    var adaptador: AdaptadorCustom? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    //Toolbar
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venues_por_categoria)

        foursquare = Foursquare(this, this)

        lista = findViewById(R.id.rvLugares)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager

        val categoriaActualString = intent.getStringExtra(Categorias.CATEGORIA_ACTUAL)
        val gson = Gson()
        val categoriaActual = gson.fromJson(categoriaActualString, Category::class.java)

        initToolbar(categoriaActual.name)

        if(foursquare?.hayToken()!!){
            ubicacion = Ubicacion(this, object : UbicacionListener {
                override fun ubicacionResponse(locationResult: LocationResult) {

                    val lat = locationResult.lastLocation.latitude.toString()
                    val lon = locationResult.lastLocation.longitude.toString()
                    val categoryId = categoriaActual.id

                    foursquare?.obtenerVenues(lat, lon, categoryId, object : ObtenerVenuesInterface {
                        override fun venuesGenerados(venues: ArrayList<Venue>) {
                            implementacionRecyclerView(venues)

                        }
                    })
                }
            })
        }else{
            foursquare?.mandarIniciarSesion()
        }
    }

    fun initToolbar(categoria: String){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(categoria)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar?.setNavigationOnClickListener { finish() }
    }

    private fun implementacionRecyclerView(lugares: ArrayList<Venue>){
        adaptador = AdaptadorCustom( lugares, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                //Toast.makeText(applicationContext, lugares.get(index).name, Toast.LENGTH_SHORT).show()
                val venueToJson = Gson()
                val venueActualString = venueToJson.toJson(lugares.get(index))
                //Log.d("venueActualString", venueActualString)
                val intent = Intent(applicationContext, DetallesVenue::class.java)
                intent.putExtra(PantallaPrincipal.VENUE_ACTUAL, venueActualString )
                startActivity(intent)
            }
        }, object: LongClickListener {
            override fun longClick(vista: View, index: Int) {
                /* if(!isActionMode){
                     startSupportActionMode(callback)
                     isActionMode = true
                     adaptador?.seleccionarItem(index)
                 }else{
                     // hacer selecciones o deselecciones
                     adaptador?.seleccionarItem(index)
                 }

                 actionMode?.title = adaptador?.obtenerNumeroElementosSeleccionados().toString() + " seleccionados"
                 */
            }

        })

        lista?.adapter = adaptador
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        ubicacion?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onStart() {
        super.onStart()

        ubicacion?.inicializarUbicacion()
    }

    override fun onPause() {
        super.onPause()
        ubicacion?.detenerActualizacionUbicacion()

    }
}
