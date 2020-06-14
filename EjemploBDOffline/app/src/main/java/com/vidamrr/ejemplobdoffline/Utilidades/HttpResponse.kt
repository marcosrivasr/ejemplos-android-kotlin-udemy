package com.vidamrr.ejemplobdoffline.Utilidades

import com.android.volley.Response
import com.android.volley.VolleyError
import org.json.JSONObject

interface HttpResponse {

    fun httpResponseSuccess(response: String)

    fun httpErrorResponse(response: String)

}