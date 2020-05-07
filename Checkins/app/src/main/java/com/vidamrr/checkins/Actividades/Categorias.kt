package com.vidamrr.checkins.Actividades

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.vidamrr.checkins.Foursquare.Category
import com.vidamrr.checkins.Foursquare.Foursquare
import com.vidamrr.checkins.Interfaces.CategoriasVenuesInterface
import com.vidamrr.checkins.R
import com.vidamrr.checkins.RecyclerViewCategorias.AdaptadorCustom
import com.vidamrr.checkins.RecyclerViewCategorias.ClickListener
import com.vidamrr.checkins.RecyclerViewCategorias.LongClickListener

class Categorias : AppCompatActivity() {

    // RecyclerView
    var lista: RecyclerView? = null
    var adaptador: AdaptadorCustom? = null
    var layoutManager: RecyclerView.LayoutManager? = null

    //Toolbar
    var toolbar: Toolbar? = null

    companion object {
        val CATEGORIA_ACTUAL = "checkins.Categorias"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorias)

        initToolbar()
        initRecyclerView()

        val fsqr = Foursquare(this, Categorias())

        if(fsqr.hayToken()){
            fsqr.cargarCategorias(object: CategoriasVenuesInterface {
                override fun categoriasVenues(categorias: ArrayList<Category>) {
                    Log.d("Categorias", categorias.count().toString())

                    implementacionRecyclerView(categorias)
                }
            })
        }else{
            fsqr.mandarIniciarSesion()
        }

    }

    private fun initRecyclerView(){
        lista = findViewById(R.id.rvCategorias)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager
    }

    private fun implementacionRecyclerView(categorias: ArrayList<Category>){
        adaptador = AdaptadorCustom( categorias, object: ClickListener {
            override fun onClick(vista: View, index: Int) {
                val categoriaToJson = Gson()
                val categoriaActualString = categoriaToJson.toJson(categorias.get(index))
                val intent = Intent(applicationContext, VenuesPorCategoria::class.java)

                intent.putExtra(Categorias.CATEGORIA_ACTUAL, categoriaActualString)
                startActivity(intent)
            }
        }, object: LongClickListener {
            override fun longClick(vista: View, index: Int) {}
        })

        lista?.adapter = adaptador
    }

    fun initToolbar(){
        toolbar = findViewById(R.id.toolbar)
        toolbar?.setTitle(R.string.app_categories)
        setSupportActionBar(toolbar)

        var actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar?.setNavigationOnClickListener { finish() }

    }
}
