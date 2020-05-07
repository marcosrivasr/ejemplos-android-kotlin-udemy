package com.vidamrr.ejemplolistview

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.support.annotation.DrawableRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class AdaptadorCustom(var context:Context, items:ArrayList<Fruta>):BaseAdapter() {

    var items:ArrayList<Fruta>? = null

    init {
        this.items = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder? = null

        var vista:View? = convertView

        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.template, null)
            holder = ViewHolder(vista)
            vista.tag = holder
        }else{
            holder = vista.tag as? ViewHolder
        }

        val item = getItem(position) as Fruta
        holder?.nombre?.text = item.nombre
        holder?.imagen?.setImageResource(item.imagen)

        //Tarea
        if(position % 2 == 0){
            holder?.celda?.setBackgroundColor(Color.LTGRAY)
        }

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    private class ViewHolder(vista:View){
        var nombre:TextView? = null
        var imagen:ImageView? = null
        // Tarea
        var celda:LinearLayout? = null
        var boton:Button? = null

        init {
            nombre = vista.findViewById(R.id.nombre)
            imagen = vista.findViewById(R.id.imagen)
            // Tarea
            celda = vista.findViewById(R.id.celda)
            boton = vista.findViewById(R.id.boton)

            boton?.setOnClickListener{
                Toast.makeText(vista.context, nombre?.text, Toast.LENGTH_LONG).show()
            }
        }
    }

}