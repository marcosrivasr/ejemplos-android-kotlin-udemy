package com.vidamrr.checkins.RecyclerViewPrincipal

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.vidamrr.checkins.R
import com.vidamrr.checkins.Foursquare.Venue
import kotlinx.android.synthetic.main.template_venues.view.*


class AdaptadorCustom(items:ArrayList<Venue>, var listener:ClickListener, var longClickListener: LongClickListener): RecyclerView.Adapter<AdaptadorCustom.ViewHolder>() {

    var items: ArrayList<Venue>? = null
    var multiSeleccion = false

    var itemsSeleccionados:ArrayList<Int>? = null
    var viewHolder:ViewHolder? = null

    init {
        this.items = items
        itemsSeleccionados = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AdaptadorCustom.ViewHolder{
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.template_venues,parent,false)
         viewHolder = ViewHolder(vista, listener, longClickListener)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        return items?.count()!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = items?.get(position)
        Picasso.get().load(item?.imagePreview).placeholder(R.drawable.placeholder_venue).into(holder.foto)
        Picasso.get().load(item?.iconCategory).placeholder(R.drawable.icono_categorias).into(holder.iconoCategoria)

        holder.nombre?.text = item?.name
        holder.state?.text = String.format("%s, %s", item?.location?.state, item?.location?.country)

        if(item?.categories?.size!! > 0){
            holder.category?.text = item?.categories?.get(0)?.name
        }else{
            holder.category?.setText(R.string.app_template_pantalla_principal_category)
        }
        holder.checkins?.text = item?.stats?.checkinsCount.toString()



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


    class ViewHolder(vista:View, listener: ClickListener, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener, View.OnLongClickListener{

        var vista = vista
        var foto: ImageView? = null
        var iconoCategoria: ImageView? = null
        var nombre:TextView? = null
        var state:TextView?= null
        var category:TextView?= null
        var checkins:TextView?= null
        //var rating:RatingBar? = null
        var listener:ClickListener? = null
        var longListener:LongClickListener? = null

        init {
            foto = vista.ivFoto
            nombre = vista.tvNombre
            state = vista.tvState
            category = vista.tvCategory
            iconoCategoria = vista.ivCategory

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

