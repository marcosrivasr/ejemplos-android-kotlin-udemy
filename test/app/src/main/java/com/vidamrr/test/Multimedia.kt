package com.vidamrr.test

import android.util.Log


open class Multimedia{
    open fun render(){
        Log.d("Mensaje","Objeto multimedia creado")
    }
}
open class Publicidad: Multimedia(){
    override fun render(){
        Log.d("Mensaje","Objeto publicidad creado")
    }
}
class Video: Publicidad(){
    override fun render(){
        Log.d("Mensaje","Objeto video creado")
    }
}