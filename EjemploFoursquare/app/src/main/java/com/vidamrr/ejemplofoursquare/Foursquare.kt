package com.vidamrr.ejemplofoursquare

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.foursquare.android.nativeoauth.FoursquareOAuth


class Foursquare(var activity:AppCompatActivity) {

    private val CODIGO_CONEXION = 200
    private val CODIGO_INTERCAMBIO_TOKEN = 201

    private val CLIENT_ID = "WUNP0C0YRJ50ELKZNIZXWKQ02YXKUXXIQYTT24VRI3DBHDFT"
    private val CLIENT_SECRET = "C1SRLK1PCDR4QU1BIZ3RBOS5FW1550UIYJ22N25KKZTBNTSB"

    private val SETTINGS = "settings"

    init {

    }

    fun iniciarSesion(){
        val intent = FoursquareOAuth.getConnectIntent(activity.applicationContext,CLIENT_ID)

        if(FoursquareOAuth.isPlayStoreIntent(intent)){
            // mostrar mensaje de que no tiene la app
            mensaje("No tienes la app instalada de Foursquare...")
            activity.startActivity(intent)
        }else{
            activity.startActivityForResult(intent, CODIGO_CONEXION)
        }
    }

    fun validarActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        when(requestCode){
            CODIGO_CONEXION->{conexionCompleta(resultCode, data)}

            CODIGO_INTERCAMBIO_TOKEN->{intercambioTokenCompleta(resultCode, data)}
        }
    }

    fun conexionCompleta(resultCode: Int, data: Intent?){
        val codigoRespuesta = FoursquareOAuth.getAuthCodeFromResult(resultCode, data)
        val exception = codigoRespuesta.exception

        if(exception == null){
            //autenticación fue correcta
            val codigo = codigoRespuesta.code
            realizarIntercambioToken(codigo)
        }else{
            mensaje("No se pudo realizar la autenticación. Inténtalo más tarde...")
        }
    }

    private fun realizarIntercambioToken(codigo: String){
        val intent = FoursquareOAuth.getTokenExchangeIntent(activity.applicationContext,CLIENT_ID,CLIENT_SECRET,codigo)
        activity.startActivityForResult(intent, CODIGO_INTERCAMBIO_TOKEN)
    }

    private fun intercambioTokenCompleta(resultCode: Int, data: Intent?){
        val respuestaToken = FoursquareOAuth.getTokenFromResult(resultCode, data)
        val exception = respuestaToken.exception

        if(exception == null){
            val accessToken = respuestaToken.accessToken
            guardarToken(accessToken)
            mensaje("Token: " + accessToken)
            navegarSiguienteActividad()
        }else{
            //Hubo un problema al obtener token
            mensaje("No se pudo obtener el token...")
        }
    }

    fun hayToken(): Boolean{
        if(obtenerToken() == ""){
            return false
        }else{
            return true
        }
    }

    private fun guardarToken(token:String){
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val editor = settings.edit()
        editor.putString("accessToken", token)

        editor.apply()
    }

    fun obtenerToken():String{
        val settings = activity.getSharedPreferences(SETTINGS, 0)
        val token = settings.getString("accessToken", "")
        return token
    }

    fun navegarSiguienteActividad(){
        activity.startActivity(Intent(activity, Dos::class.java))
        activity.finish()
    }


    fun mensaje(mensaje:String){
        Toast.makeText(activity.applicationContext, mensaje, Toast.LENGTH_LONG).show()
    }

}