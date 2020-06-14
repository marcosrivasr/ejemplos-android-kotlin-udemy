package com.vidamrr.ejemplobdoffline.Utilidades

import android.content.Context
import android.net.ConnectivityManager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.vidamrr.ejemplobdoffline.Utilidades.HttpResponse
import org.json.JSONObject
import com.android.volley.ParseError
import com.android.volley.toolbox.HttpHeaderParser

import android.system.Os.link
import com.android.volley.NetworkResponse
import com.android.volley.VolleyError
import android.widget.Toast
import org.json.JSONArray
import com.android.volley.VolleyLog
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonArrayRequest
import com.google.gson.JsonSyntaxException


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

                httpResponse.httpErrorResponse(error.message.toString())

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