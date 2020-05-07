package com.vidamrr.ejemplorecyclerview

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.view.ActionMode
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.Toast

class MainActivity : AppCompatActivity(){

    var lista:RecyclerView? = null
    var adaptador:AdaptadorCustom? = null
    var layoutManager:RecyclerView.LayoutManager? = null

    var isActionMode = false
    var actionMode:ActionMode? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val platillos = ArrayList<Platillo>()

        platillos.add(Platillo("Alitas de Pollo Teryaki", 250.0, 3.5F, R.drawable.platillo01))
        platillos.add(Platillo("Ensalada César", 200.0, 5F,R.drawable.platillo02))
        platillos.add(Platillo("Biscochos Salados", 320.0, 2F,R.drawable.platillo03))
        platillos.add(Platillo("Papas Horneadas con Tocino", 85.0, 4.5F,R.drawable.platillo04))
        platillos.add(Platillo("Coctél de Frutas", 100.0, 5F,R.drawable.platillo05))
        platillos.add(Platillo("Pápas a la Francesa", 60.0, 5F, R.drawable.platillo06))
        platillos.add(Platillo("Pollo al Carbón y Especias", 250.0, 1F,R.drawable.platillo07))
        platillos.add(Platillo("Pasta Italiana y Albóndigas", 180.0, 3.5F,R.drawable.platillo08))
        platillos.add(Platillo("Bolitas de Jamón Serrano", 56.0, 2F,R.drawable.platillo09))
        platillos.add(Platillo("Chiles Rellenos de Atún", 99.0, 4F,R.drawable.platillo10))

        lista = findViewById(R.id.lista)
        lista?.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(this)
        lista?.layoutManager = layoutManager

        val callback = object: ActionMode.Callback{

            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
                when(item?.itemId){
                    R.id.iEliminar ->{
                        adaptador?.eliminarSeleccionados()
                    }

                    else->{return true}
                }

                adaptador?.terminarActionMode()
                mode?.finish()
                isActionMode = false


                return true
            }

            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                // inicializar action Mode
                adaptador?.iniciarActionMode()
                actionMode = mode
                // inflar menú
                menuInflater.inflate(R.menu.menu_contextual, menu!!)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.title = "0 seleccionados"
                return false
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                // destruimos el modo
                adaptador?.destruirActionMode()
                isActionMode = false
            }

        }

        adaptador = AdaptadorCustom( platillos, object:ClickListener{
            override fun onClick(vista: View, index: Int) {
                Toast.makeText(applicationContext, platillos.get(index).nombre, Toast.LENGTH_SHORT).show()
            }
        }, object: LongClickListener{
            override fun longClick(vista: View, index: Int) {
                if(!isActionMode){
                    startSupportActionMode(callback)
                    isActionMode = true
                    adaptador?.seleccionarItem(index)
                }else{
                    // hacer selecciones o deselecciones
                    adaptador?.seleccionarItem(index)
                }

                actionMode?.title = adaptador?.obtenerNumeroElementosSeleccionados().toString() + " seleccionados"
            }

        })

        lista?.adapter = adaptador





        val swipeToRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeToRefresh)
        swipeToRefresh.setOnRefreshListener {

            object : AsyncTask<Void, Void, Void>() {

                override fun doInBackground(vararg params: Void): Void? {
                    for (i in 1..1000000000){
                    }
                    return null
                }

                override fun onPostExecute(result: Void?) {
                    super.onPostExecute(result)

                    runOnUiThread {
                        swipeToRefresh.isRefreshing = false
                        platillos.add(Platillo("Nugets", 250.0, 3.5F, R.drawable.platillo01))
                        adaptador?.notifyDataSetChanged()
                    }
                }
            }.execute()

        }
    }

}
