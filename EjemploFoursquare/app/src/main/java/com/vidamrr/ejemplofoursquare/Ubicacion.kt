package com.vidamrr.ejemplofoursquare

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.gson.Gson
import com.vidamrr.ejemplofoursquare.RecyclerView.AdaptadorCustom
import com.vidamrr.ejemplofoursquare.RecyclerView.ClickListener
import com.vidamrr.ejemplofoursquare.RecyclerView.LongClickListener


class Ubicacion(var activity: AppCompatActivity, ubicacionInterface: UbicacionInterface) {

    private val permisoFineLocation = android.Manifest.permission.ACCESS_FINE_LOCATION

    private val permisoCoarseLocation = android.Manifest.permission.ACCESS_COARSE_LOCATION
    private val CODIGO_SOLICITUD_PERMISO = 100

    private var fusedLocationClient: FusedLocationProviderClient? = null

    private var locationRequest: LocationRequest? = null

    private var callback: LocationCallback? = null


    init {
        fusedLocationClient = FusedLocationProviderClient(activity.applicationContext)
        inicializarLocationRequest()

        callback = object: LocationCallback(){

            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)

                ubicacionInterface.ubicacionEncontrada(locationResult)

            }
        }
    }



    private fun inicializarLocationRequest(){
        locationRequest = LocationRequest()
        locationRequest?.interval = 10000
        locationRequest?.fastestInterval = 5000
        locationRequest?.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun validarPermisosUbicacion():Boolean{
        val hayUbicacionPrecisa = ActivityCompat.checkSelfPermission(activity.applicationContext,permisoFineLocation) == PackageManager.PERMISSION_GRANTED
        val hayUbicacionOrdinaria = ActivityCompat.checkSelfPermission(activity.applicationContext, permisoCoarseLocation) == PackageManager.PERMISSION_GRANTED

        return hayUbicacionPrecisa && hayUbicacionOrdinaria
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion(){
        validarPermisosUbicacion()
        fusedLocationClient?.requestLocationUpdates(locationRequest, callback, null)
    }


    private fun pedirPermisos(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity,permisoFineLocation)

        if(deboProveerContexto){
            // mandar un mensaje con explicación adicional
            solicitudPermiso()
        }else{
            solicitudPermiso()
        }
    }

    private fun solicitudPermiso(){
        ActivityCompat.requestPermissions(activity, arrayOf(permisoFineLocation, permisoCoarseLocation), CODIGO_SOLICITUD_PERMISO)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        when(requestCode){
            CODIGO_SOLICITUD_PERMISO ->{
                if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //obtener ubicación
                    obtenerUbicacion()
                }else{
                    Toast.makeText(activity.applicationContext, "No diste permiso para acceder a la ubicación", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

     fun detenerActualizacionUbicacion(){
        fusedLocationClient?.removeLocationUpdates(callback)
    }

     fun inicializarUbicacion(){
        if(validarPermisosUbicacion()){
            obtenerUbicacion()
        }else{
            pedirPermisos()
        }
    }
}