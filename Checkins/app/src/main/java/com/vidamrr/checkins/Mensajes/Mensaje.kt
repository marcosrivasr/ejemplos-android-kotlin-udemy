package com.vidamrr.checkins.Mensajes

import android.content.Context
import android.widget.Toast

class Mensaje {

    companion object {

        fun mensaje(context: Context, mensaje: Mensajes){
            var str = ""
            when(mensaje){
                Mensajes.RATIONALE ->{
                    str = "Requiero permisos para obtener ubicacion"
                }
                Mensajes.CHECKIN_SUCCESS ->{
                    str = "Nuevo checkin añadido"
                }
                Mensajes.LIKE_SUCCESS ->{
                    str = "Nuevo like añadido"
                }
            }
            Toast.makeText(context, str, Toast.LENGTH_SHORT).show()
        }

        fun mensajeError(context: Context, error: Errores){
            var mensaje = ""
            when (error){
                Errores.NO_HAY_RED ->{
                    mensaje = "No hay una conexión disponible"
                }

                Errores.HTTP_ERROR ->{
                    mensaje = "Hubo un problema en la solicitud HTTP"
                }

                Errores.NO_HAY_APP_FSQR ->{
                    mensaje = "No tienes instalada la app de Foursquare"
                }

                Errores.ERROR_CONEXION_FSQR ->{
                    mensaje = "No se pudo completar la conexion a Foursquare"
                }

                Errores.ERROR_INTERCAMBIO_TOKEN ->{
                    mensaje = "No se pudo completar el intercambio de token en Foursquare"
                }

                Errores.ERROR_GUARDAR_TOKEN ->{
                    mensaje = "No se pudo guardar el token"
                }

                Errores.PERMISO_NEGADO ->{
                    mensaje = "No diste los permisos para obtener tu ubicación"
                }
                Errores.ERROR_QUERY ->{
                    mensaje = "Hubo un problema en la solicitud a la API"
                }
            }

            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }

        fun mensajeError(context: Context, error: String){
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }


    }
}