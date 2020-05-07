package com.vidamrr.checkins.GridViewDetalleVenue

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.vidamrr.checkins.Foursquare.Rejilla
import com.vidamrr.checkins.R
import kotlinx.android.synthetic.main.template_grid_detalle_venue.view.*

class AdaptadorGridView(var context: Context, items:ArrayList<Rejilla>): BaseAdapter(){

    var items:ArrayList<Rejilla>? = null

    init{
        this.items = items;
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var vista = convertView
        var holder:ViewHolder? = null

        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.template_grid_detalle_venue, null)
            holder = ViewHolder(vista)
            vista.tag = holder
        }else{
            holder = vista.tag as? ViewHolder
        }

        val item = items?.get(position) as? Rejilla
        holder?.nombre?.text = item?.nombre
        holder?.imagen?.setImageResource(item?.icono!!)

        holder?.container?.setBackgroundColor(item?.colorFondo!!)

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return this.items?.count()!!
    }

    private class ViewHolder(vista:View){
        var nombre: TextView? = null
        var imagen: ImageView? = null
        var container:LinearLayout? = null

        init {
            nombre = vista.nombre
            imagen = vista.imagen
            container = vista.container
        }
    }

}
