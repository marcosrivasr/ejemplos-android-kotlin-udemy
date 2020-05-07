package com.vidamrr.tutorialrecyclerview

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.template.view.*


class AdaptadorCustom(items:ArrayList<Persona>, var clickListener: ClickListener): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items:ArrayList<Persona>? = null

    var viewHolder:ViewHolder? = null

    init {
        this.items = items
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items?.get(position)
        holder.nombre?.text = item?.nombre
        holder.foto?.setImageResource(item?.foto!!)

    }

    override fun getItemCount(): Int {
        return this.items?.count()!!
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AdaptadorCustom.ViewHolder{
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.template, parent, false)

        viewHolder = ViewHolder(vista, clickListener)

        return viewHolder!!
    }


    class ViewHolder(vista: View, listener:ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener{


        var vista = vista

        var foto:ImageView? = null

        var nombre:TextView? = null

        var listener:ClickListener?= null

        init {
            foto = vista.ivFoto
            nombre = vista.tvNombre

            this.listener = listener

            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }


}