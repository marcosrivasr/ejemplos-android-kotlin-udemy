package com.vidamrr.ejemplofoursquare

import com.google.android.gms.location.LocationResult


interface UbicacionInterface {

    fun ubicacionEncontrada(locationResult: LocationResult?)
}