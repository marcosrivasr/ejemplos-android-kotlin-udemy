package com.vidamrr.ejemplofoursquare.RecyclerView

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vidamrr.ejemplofoursquare.R
import com.vidamrr.ejemplofoursquare.Venue
import kotlinx.android.synthetic.main.template_venues.view.*


class AdaptadorCustom(items:ArrayList<Venue>, var listener: ClickListener, var longClickListener: LongClickListener): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items: ArrayList<Venue>? = null
    var multiSeleccion = false

    var itemsSeleccionados:ArrayList<Int>? = null
    var viewHolder: ViewHolder? = null

    var contexto:Context? = null

    init {
        this.items = items
        itemsSeleccionados = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val vista: View = LayoutInflater.from(parent?.context).inflate(R.layout.template_venues,parent,false)
        contexto = parent?.context
        viewHolder = ViewHolder(vista, listener, longClickListener)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)
        //holder.foto?.setImageResource(item?.foto!!)
        holder.nombre?.text = item?.name
        //TODO
        if(item?.categories?.size!! > 0){
            val urlImagen = item?.categories?.get(0)!!.icon?.prefix + "bg_64" + item?.categories?.get(0)!!.icon?.suffix
            Picasso.with(contexto).load(urlImagen).into(holder.foto)
        }


        //holder.precio?.text = "$" + item?.precio.toString()
        //holder.rating?.rating = item?.rating!!

        if(itemsSeleccionados?.contains(position)!!){
            holder.vista.setBackgroundColor(Color.LTGRAY)
        }else{
            holder.vista.setBackgroundColor(Color.WHITE)
        }
    }

    fun iniciarActionMode(){
        multiSeleccion = true
    }

    fun destruirActionMode(){
        multiSeleccion = false
        itemsSeleccionados?.clear()
        notifyDataSetChanged()
    }

    fun terminarActionMode(){
        // eliminar elementos seleccionados
        for(item in itemsSeleccionados!!){
            itemsSeleccionados?.remove(item)
        }
        multiSeleccion = false
        notifyDataSetChanged()
    }

    fun seleccionarItem(index:Int){
        if(multiSeleccion){
            if(itemsSeleccionados?.contains(index)!!){
                itemsSeleccionados?.remove(index)
            }else{
                itemsSeleccionados?.add(index)
            }

            notifyDataSetChanged()
        }
    }

    fun obtenerNumeroElementosSeleccionados():Int{
        return itemsSeleccionados?.count()!!
    }

    fun eliminarSeleccionados(){
        if(itemsSeleccionados?.count()!! > 0){
            var itemsEliminados = ArrayList<Venue>()

            for(index in itemsSeleccionados!!){
                itemsEliminados.add(items?.get(index)!!)
            }

            items?.removeAll(itemsEliminados)
            itemsSeleccionados?.clear()
        }
    }


    class ViewHolder(vista: View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener, View.OnLongClickListener{

        var vista: View = vista
        var foto: ImageView? = null
        var nombre: TextView? = null
        //var precio: TextView?= null
        //var rating:RatingBar? = null
        var listener: ClickListener? = null
        var longListener: LongClickListener? = null

        init {
            this.vista = vista
            //foto = vista.findViewById(R.id.ivFoto)
            this.foto = vista.ivFoto
            this.nombre = vista.tvNombre
            //precio = vista.findViewById(R.id.tvNombre)
            //rating = vista.findViewById(R.id.tvRating)

            this.listener = listener
            this.longListener = longClickListener

            vista.setOnClickListener(this)
            vista.setOnLongClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            this.longListener?.longClick(v!!, adapterPosition)
            return true
        }



    }


}

