package com.vidamrr.checkins.Actividades

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.google.gson.Gson
import com.vidamrr.checkins.Foursquare.Foursquare
import com.vidamrr.checkins.Interfaces.UsuariosInterface
import com.vidamrr.checkins.R
import com.vidamrr.checkins.Foursquare.User
import com.vidamrr.checkins.Foursquare.Venue
import android.content.DialogInterface
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.*
import com.squareup.picasso.Picasso
import com.vidamrr.checkins.Foursquare.Rejilla
import com.vidamrr.checkins.GridViewDetalleVenue.AdaptadorGridView
import kotlinx.android.synthetic.main.activity_detalles_venue.*
import java.net.URLEncoder
import java.text.NumberFormat
import java.util.*


class DetallesVenue : AppCompatActivity() {

    //Toolbar
    var toolbar: Toolbar? = null
    var bCheckin: Button? = null
    var bLike: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles_venue)

        bCheckin = findViewById(R.id.bCheckin)
        bLike = findViewById(R.id.bLike)

        val ivFoto = findViewById<ImageView>(R.id.ivFoto)
        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvState = findViewById<TextView>(R.id.tvState)
        val tvCountry = findViewById<TextView>(R.id.tvCountry)
        //val tvCategory = findViewById<TextView>(R.id.tvCategory)
        //val tvCheckins = findViewById<TextView>(R.id.tvCheckins)
        //val tvUsers = findViewById<TextView>(R.id.tvUsers)
        //val tvTips = findViewById<TextView>(R.id.tvTips)
        val rejilla = findViewById<GridView>(R.id.gvRejilla)

        val venueActualString = intent.getStringExtra(PantallaPrincipal.VENUE_ACTUAL)

        val gson = Gson()
        val venueActual = gson.fromJson(venueActualString, Venue::class.java)

        val listaRejilla = ArrayList<Rejilla>()

        initToolbar(venueActual.name)

        //Log.d("venueActual", venueActual.name)

        Picasso.get().load(venueActual.imagePreview).placeholder(R.drawable.placeholder_venue).into(ivFoto)
        tvNombre.text = venueActual.name
        tvState.text = venueActual.location?.state
        tvCountry.text = venueActual.location?.country
        //tvCategory.text = venueActual.categories?.get(0)?.name
        //tvCheckins.text = venueActual.stats?.checkinsCount.toString()
        //tvUsers.text = venueActual.stats?.usersCount.toString()
        //tvTips.text = venueActual.stats?.tipCount.toString()

        listaRejilla.add(Rejilla(venueActual.categories?.get(0)?.name!!, R.drawable.icono_rejilla_categoria, ContextCompat.getColor(this,R.color.primaryColor)))
        listaRejilla.add(Rejilla(String.format("%s checkins", NumberFormat.getNumberInstance(Locale.US).format(venueActual.stats?.checkinsCount)), R.drawable.icono_rejilla_checkin, ContextCompat.getColor(this,R.color.primaryLightColor)))
        listaRejilla.add(Rejilla(String.format("%s usuarios",NumberFormat.getNumberInstance(Locale.US).format(venueActual.stats?.usersCount)), R.drawable.icono_rejilla_users, ContextCompat.getColor(this,R.color.secondaryColor)))
        listaRejilla.add(Rejilla(String.format("%s tips",NumberFormat.getNumberInstance(Locale.US).format(venueActual.stats?.tipCount)), R.drawable.icono_rejilla_tips, ContextCompat.getColor(this,R.color.secondaryDarkColor)))

        val adaptador = AdaptadorGridView(this, listaRejilla)

        gvRejilla.adapter = adaptador

        val foursquare = Foursquare(this, DetallesVenue())

        bCheckin?.setOnClickListener {

            if(foursquare.hayToken()){
                val etMensaje = EditText(this)
                etMensaje.hint = "Hola!"

                AlertDialog.Builder(this)
                        .setTitle("Nuevo Check-in")
                        .setMessage("Ingresa un mensaje")
                        .setView(etMensaje)
                        .setNegativeButton("Cancelar", DialogInterface.OnClickListener {
                            dialog, which ->

                        })
                        .setPositiveButton("Check-in", DialogInterface.OnClickListener {
                            dialog, which ->

                            val mensaje = URLEncoder.encode(etMensaje.text.toString(), "UTF-8")

                            foursquare.nuevoCheckin(venueActual.id, venueActual.location!!, mensaje)
                        })
                        .show()

            }else{
                foursquare?.mandarIniciarSesion()
            }

        }

        bLike?.setOnClickListener{
            if(foursquare.hayToken()){
                foursquare.nuevoLike(venueActual.id)
            }
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
}
