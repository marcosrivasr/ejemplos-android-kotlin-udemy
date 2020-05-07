package com.vidamrr.checkins.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.gms.location.LocationResult
import com.google.gson.Gson
import com.vidamrr.checkins.*
import com.vidamrr.checkins.Foursquare.Foursquare
import com.vidamrr.checkins.Foursquare.Venue
import com.vidamrr.checkins.Interfaces.ObtenerVenuesInterface
import com.vidamrr.checkins.Interfaces.UbicacionListener
import com.vidamrr.checkins.RecyclerViewPrincipal.AdaptadorCustom
import com.vidamrr.checkins.RecyclerViewPrincipal.ClickListener
import com.vidamrr.checkins.RecyclerViewPrincipal.LongClickListener
import com.vidamrr.checkins.Utilidades.Ubicacion

class PantallaPrincipal : AppCompatActivity() {

    var ubicacion: Ubicacion? = null
    var foursquare: Foursquare? = null

    var lista: RecyclerView? = null
    var adaptador:AdaptadorCustom? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    //Toolbar
    var toolbar: Toolbar? = null

    companion object {
        val VENUE_ACTUAL = "checkins.PantallaPrincipal"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_principal)

        foursquare = Foursquare(this, this)

        lista = findViewById(R.id.rvLugares)
        lista?.setHasFixedSize(true)

        initToolbar()

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager

        if(foursquare?.hayToken()!!){
            ubicacion = Ubicacion(this, object : UbicacionListener {
                override fun ubicacionResponse(locationResult: LocationResult) {

                    val lat = locationResult.lastLocation.latitude.toString()
                    val lon = locationResult.lastLocation.longitude.toString()

                    foursquare?.obtenerVenues(lat, lon, object : ObtenerVenuesInterface {
                        override fun venuesGenerados(venues: ArrayList<Venue>) {
                            implementacionRecyclerView(venues)
                            for (venue in venues) {
                                Log.d("VENUE", venue.name)
                            }
                        }
                    })
                }
            })
        }else{
            foursquare?.mandarIniciarSesion()
        }
    }

    private fun implementacionRecyclerView(lugares: ArrayList<Venue>){
        adaptador = AdaptadorCustom( lugares, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                //Toast.makeText(applicationContext, lugares.get(index).name, Toast.LENGTH_SHORT).show()
                val venueToJson = Gson()
                val venueActualString = venueToJson.toJson(lugares.get(index))
                //Log.d("venueActualString", venueActualString)
                val intent = Intent(applicationContext, DetallesVenue::class.java)
                intent.putExtra(VENUE_ACTUAL, venueActualString )
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

    fun initToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_name)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.iconoCategorias -> {
                val intent = Intent(this, Categorias::class.java)
                startActivity(intent)
                return true
            }

            R.id.iconoFavoritos->{
                val intent = Intent(this, Likes::class.java)
                startActivity(intent)
                return true
            }

            R.id.iconoPerfil->{
                val intent = Intent(this, Perfil::class.java)
                startActivity(intent)
                return true
            }

            R.id.iconoCerrarSesion->{
                foursquare?.cerrarSesion()
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
                return true
            }

            else ->{return super.onOptionsItemSelected(item)}
        }
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
