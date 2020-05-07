package com.vidamrr.ejemplobdoffline.Utilidades

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.vidamrr.ejemplobdoffline.Utilidades.HttpResponse


class Network(var activity: AppCompatActivity) {

    init {
    }

    fun hayRed(): Boolean{
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    fun httpRequest(context: Context, url:String, httpResponse: HttpResponse){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{

                response ->

                httpResponse.httpResponseSuccess(response)

            }, Response.ErrorListener {
                error ->

                Log.d("HTTP_REQUEST", error.message.toString())

                //Mensaje.mensajeError(context, Errores.HTTP_ERROR)
            })
            queue.add(solicitud)
        }else{
            //Mensaje.mensajeError(context, Errores.NO_HAY_RED)
        }
    }

    fun httpPOSTRequest(context: Context, url:String, httpResponse: HttpResponse){
        if(hayRed()){
            val queue = Volley.newRequestQueue(context)

            val solicitud = StringRequest(Request.Method.POST, url, Response.Listener<String>{

                response ->

                httpResponse.httpResponseSuccess(response)

            }, Response.ErrorListener {
                error ->

                Log.d("HTTP_REQUEST", error.message.toString())

                //Mensaje.mensajeError(context, Errores.HTTP_ERROR)
            })
            queue.add(solicitud)
        }else{
            //Mensaje.mensajeError(context, Errores.NO_HAY_RED)
        }
    }

}