package com.vidamrr.checkins.Actividades

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.GridView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vidamrr.checkins.Foursquare.Foursquare
import com.vidamrr.checkins.Foursquare.Rejilla
import com.vidamrr.checkins.Foursquare.User
import com.vidamrr.checkins.Foursquare.Venue
import com.vidamrr.checkins.GridViewDetalleVenue.AdaptadorGridView
import com.vidamrr.checkins.Interfaces.UsuariosInterface
import com.vidamrr.checkins.Interfaces.VenuesPorLikeInterface
import com.vidamrr.checkins.R
import com.vidamrr.checkins.RecyclerViewPrincipal.AdaptadorCustom
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_detalles_venue.*
import kotlinx.android.synthetic.main.activity_perfil.*
import java.text.NumberFormat
import java.util.*

class Perfil : AppCompatActivity() {

    var foursquare: Foursquare? = null


    //Toolbar
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val tvNombre = findViewById<TextView>(R.id.tvNombre)
        val tvFriends = findViewById<TextView>(R.id.tvFriends)
        val tvTips = findViewById<TextView>(R.id.tvTips)
        val tvPhotos = findViewById<TextView>(R.id.tvPhotos)
        val tvCheckins = findViewById<TextView>(R.id.tvCheckins)
        val gvRejilla = findViewById<GridView>(R.id.gvRejilla)
        val listaRejilla = ArrayList<Rejilla>()
        val ivFoto = findViewById<CircleImageView>(R.id.ivFoto)

        foursquare = Foursquare(this, this)

        if(foursquare?.hayToken()!!){
           foursquare?.obtenerUsuarioActual(object: UsuariosInterface{
               override fun obtenerUsuarioActual(usuario: User) {
                   tvNombre.text = usuario.firstName
                   tvFriends.text = String.format("%d %s", usuario.friends?.count, getString(R.string.app_perfil_friends))
                   tvTips.text = String.format("%d %s", usuario.tips?.count, getString(R.string.app_perfil_tips))
                   tvPhotos.text = String.format("%d %s",usuario.photos?.count, getString(R.string.app_perfil_photos))
                   tvCheckins.text = String.format("%d %s",usuario.checkins?.count, getString(R.string.app_perfil_checkins))
                   initToolbar(usuario.firstName + " " + usuario.lastName)

                   Picasso.get().load(usuario.photo?.urlIcono).into(ivFoto)

                   listaRejilla.add(Rejilla(String.format("%s %s", NumberFormat.getNumberInstance(Locale.US).format(usuario.photos?.count), getString(R.string.app_perfil_photos)), R.drawable.icono_foto, ContextCompat.getColor(applicationContext,R.color.primaryLightColor)))
                   listaRejilla.add(Rejilla(String.format("%s %s", NumberFormat.getNumberInstance(Locale.US).format(usuario.checkins?.count), getString(R.string.app_perfil_checkins)), R.drawable.icono_rejilla_checkin, ContextCompat.getColor(applicationContext,R.color.primaryLightColor)))
                   listaRejilla.add(Rejilla(String.format("%s %s", NumberFormat.getNumberInstance(Locale.US).format(usuario.friends?.count), getString(R.string.app_perfil_friends)), R.drawable.icono_rejilla_users, ContextCompat.getColor(applicationContext,R.color.secondaryColor)))
                   listaRejilla.add(Rejilla(String.format("%s %s", NumberFormat.getNumberInstance(Locale.US).format(usuario.tips?.count), getString(R.string.app_perfil_tips)), R.drawable.icono_rejilla_tips, ContextCompat.getColor(applicationContext,R.color.secondaryDarkColor)))

                   val adaptador = AdaptadorGridView(applicationContext, listaRejilla)

                   gvRejilla.adapter = adaptador
               }
           })
        }else{
            foursquare?.mandarIniciarSesion()
        }
    }

    fun initToolbar(nombrePerfil: String){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(nombrePerfil)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar?.setNavigationOnClickListener { finish() }
    }
}
