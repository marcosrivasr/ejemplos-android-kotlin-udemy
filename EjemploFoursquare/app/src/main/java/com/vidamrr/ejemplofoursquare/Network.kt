package com.vidamrr.ejemplofoursquare

import android.content.Context
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.vidamrr.ejemplofoursquare.RecyclerView.AdaptadorCustom
import com.vidamrr.ejemplofoursquare.RecyclerView.ClickListener
import com.vidamrr.ejemplofoursquare.RecyclerView.LongClickListener


class Network{


    fun httpRequest(context: Context, url: String,httpResponse: HttpResponse){
        val queue = Volley.newRequestQueue(context)


        val solicitud = StringRequest(Request.Method.GET, url, Response.Listener<String>{
            response ->

            Log.d("RESPONSE HTTP", response)
            httpResponse.httpResponseSuccess(response)

        }, Response.ErrorListener {

        })

        queue.add(solicitud)
    }
}