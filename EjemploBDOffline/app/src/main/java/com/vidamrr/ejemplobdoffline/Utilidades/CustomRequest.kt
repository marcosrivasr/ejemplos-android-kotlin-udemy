package com.vidamrr.ejemplobdoffline.Utilidades

import com.android.volley.NetworkResponse
import com.android.volley.ParseError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.google.gson.JsonSyntaxException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset

class CustomRequest(
        method: Int,
        url: String?,
        private val headers:MutableMap<String, String>,
        private val listener: Response.Listener<String>,
        errorListener: Response.ErrorListener): Request<String>(method, url, errorListener){

    override fun getHeaders(): MutableMap<String, String> = headers ?: super.getHeaders()

    override fun deliverResponse(response: String?) {
        return listener.onResponse(response);
    }


    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        return try {
            val json:String = String(
                    response?.data ?: ByteArray(0),
                    Charset.forName(HttpHeaderParser.parseCharset(response?.headers)))

            Response.success(json, HttpHeaderParser.parseCacheHeaders(response))


        } catch (e: UnsupportedEncodingException) {
            Response.error(ParseError(e))
        } catch (e: JsonSyntaxException) {
            Response.error(ParseError(e))
        }
    }


}