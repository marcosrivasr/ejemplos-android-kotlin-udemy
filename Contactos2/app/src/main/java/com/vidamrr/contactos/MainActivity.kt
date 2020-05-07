package com.vidamrr.contactos

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.vidamrr.contactos.R.id.searchView
import android.support.v4.widget.SearchViewCompat.setSearchableInfo
import android.support.v4.view.MenuItemCompat.getActionView
import android.content.Context.SEARCH_SERVICE
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.support.v7.widget.SwitchCompat
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.switch_item.view.*


class MainActivity : AppCompatActivity() {

    var lista:ListView? = null
    var grid:GridView? = null
    var viewSwitcher:ViewSwitcher? = null


    companion object {
        var contactos:ArrayList<Contacto>? = null
        var adaptador:AdaptadorCustom? = null
        var adaptadorGrid:AdaptadorCustomGrid? = null

        fun agregarContacto(contacto:Contacto){
            adaptador?.addItem(contacto)
        }

        fun obtenerContacto(index:Int):Contacto{
            return adaptador?.getItem(index) as Contacto
        }

        fun eliminarContacto(index:Int){
           adaptador?.removeItem(index)
        }

        fun actualizarContacto(index:Int, nuevoContacto:Contacto){
            adaptador?.updateItem(index, nuevoContacto)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)



        contactos = ArrayList()
        contactos?.add(Contacto("Marcos", "Rivas", "Contoso", 25,70.0F, "Tamaulipas 215", "55 1789245","marcos@contoso.com", R.drawable.foto_01 ))
        contactos?.add(Contacto("Tania", "Bañales", "Sedraui", 23,70.0F, "Tamaulipas 215", "55 1789245","marcos@contoso.com", R.drawable.foto_04 ))
        contactos?.add(Contacto("Omar", "Pozos", "Cinelex", 35,70.0F, "Tamaulipas 215", "55 1789245","marcos@contoso.com", R.drawable.foto_03 ))
        contactos?.add(Contacto("Luis", "Anzaldo", "Hermanz", 12,70.0F, "Tamaulipas 215", "55 1789245","marcos@contoso.com", R.drawable.foto_02 ))
        contactos?.add(Contacto("Victoria", "Castro", "Barborns", 42,70.0F, "Tamaulipas 215", "55 1789245","marcos@contoso.com", R.drawable.foto_05))
        contactos?.add(Contacto("Cecilia", "Leon", "Friara", 37,70.0F, "Tamaulipas 215", "55 1789245","marcos@contoso.com", R.drawable.foto_06 ))

        lista = findViewById<ListView>(R.id.lista)
        grid = findViewById<GridView>(R.id.grid)
        adaptador = AdaptadorCustom(this, contactos!!)
        adaptadorGrid = AdaptadorCustomGrid(this, contactos!!)

        viewSwitcher = findViewById(R.id.viewSwitcher)

        lista?.adapter = adaptador
        grid?.adapter = adaptador

        lista?.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, Detalle::class.java)
            intent.putExtra("ID", position.toString())
            startActivity(intent)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val itemBusqueda = menu?.findItem(R.id.searchView)
        val searchView = itemBusqueda?.actionView as SearchView

        val itemSwitch = menu?.findItem(R.id.switchView)
        itemSwitch?.setActionView(R.layout.switch_item)
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.sCambiaVista)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto..."

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            // preparar datos

        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String?): Boolean {
                // filtrar
                adaptador?.filtrar(newText!!)
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // filtrar
                return true
            }
        })

        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            viewSwitcher?.showNext()
            adaptador?.cambiarTemplate()
        }





        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){

            R.id.iNuevo ->{
                val intent = Intent(this, Nuevo::class.java)
                startActivity(intent)
                return true
            }

            else ->{return super.onOptionsItemSelected(item)}
        }

    }

    override fun onResume() {
        super.onResume()

        adaptador?.notifyDataSetChanged()

    }


}
