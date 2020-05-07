package com.vidamrr.contactos


class Contacto(nombre:String, apellidos:String, empresa:String, edad:Int, peso:Float, direccion:String, telefono:String, email:String, foto: Int) {

    var nombre:String = ""
    var apellidos:String = ""
    var empresa:String = ""
    var edad:Int = 0
    var peso:Float = 0.0F
    var direccion:String = ""
    var telefono:String = ""
    var email:String = ""
    var foto:Int = 0

    init {
        this.nombre = nombre
        this.apellidos = apellidos
        this.empresa = empresa
        this.edad = edad
        this.peso = peso
        this.direccion = direccion
        this.telefono = telefono
        this.email = email
        this.foto = foto
    }

}