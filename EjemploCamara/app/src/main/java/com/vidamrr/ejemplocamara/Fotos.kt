package com.vidamrr.ejemplocamara

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.widget.Toast
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ImageView

class Fotos(private var activity:Activity, private var imageView: ImageView) {
    private val SOLICITUD_TOMAR_FOTO = 1
    private val SOLICITUD_SELECCIONAR_FOTO = 2

    private val permisoCamera = android.Manifest.permission.CAMERA
    private val permisoWriteStorage = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    private val permisoReadStorage = android.Manifest.permission.READ_EXTERNAL_STORAGE

    var urlFotoActual = ""


    fun tomarFoto(){
        pedirPermisos()
    }

    fun seleccionarFoto(){
        pedirPermisosSeleccionarFoto()
    }

    private fun pedirPermisos(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoCamera)

        if(deboProveerContexto){
            solicitudPermisos()
        }else{
            solicitudPermisos()
        }
    }

    private fun pedirPermisosSeleccionarFoto(){
        val deboProveerContexto = ActivityCompat.shouldShowRequestPermissionRationale(activity, permisoReadStorage)

        if(deboProveerContexto){
            solicitudPermisosSeleccionarFoto()
        }else{
            solicitudPermisosSeleccionarFoto()
        }
    }

    private fun solicitudPermisos(){
        activity.requestPermissions(arrayOf(permisoCamera, permisoWriteStorage, permisoReadStorage), SOLICITUD_TOMAR_FOTO)
    }
    private fun solicitudPermisosSeleccionarFoto(){
        activity.requestPermissions(arrayOf(permisoReadStorage), SOLICITUD_SELECCIONAR_FOTO)
    }

    fun requestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        when(requestCode){
            SOLICITUD_TOMAR_FOTO ->{
                if(grantResults.size > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[2] == PackageManager.PERMISSION_GRANTED){
                    // tenemos permiso
                    dispararIntentTomarFoto()
                }else{
                    // no tenemos permiso
                    Toast.makeText(activity.applicationContext, "No diste permiso para acceder a la cámara y almacenamiento", Toast.LENGTH_SHORT).show()
                }
            }

            SOLICITUD_SELECCIONAR_FOTO->{
                if(grantResults.size > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    // tenemos permiso
                    dispararIntentSeleccionarFoto()
                }else{
                    // no tenemos permiso
                    Toast.makeText(activity.applicationContext, "No diste permiso para acceder a tus fotos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun dispararIntentTomarFoto(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if(intent.resolveActivity(activity.packageManager) != null){

            var archivoFoto: File? = null

            archivoFoto = crearArchivoImagen()

            if(archivoFoto != null){
                val urlFoto = FileProvider.getUriForFile(activity.applicationContext, "com.vidamrr.ejemplocamara.fileprovider", archivoFoto)

                intent.putExtra(MediaStore.EXTRA_OUTPUT, urlFoto)
                activity.startActivityForResult(intent, SOLICITUD_TOMAR_FOTO)
            }
        }
    }

    private fun dispararIntentSeleccionarFoto(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        intent.setType("image/*")

        activity.startActivityForResult(Intent.createChooser(intent, "Seleccionar una foto"), SOLICITUD_SELECCIONAR_FOTO)
    }

    fun activityResult(requestCode: Int, resultCode: Int, data: Intent?){
        when(requestCode){
            SOLICITUD_TOMAR_FOTO->{
                if(resultCode == Activity.RESULT_OK){
                    // obtener imagen
                    //Log.d("ACTIVITY_RESULT", "Obtener imagen")

                    /*val extras = data?.extras
                    val imageBitmap = extras!!.get("data") as Bitmap
                    */
                    mostrarBitmap(urlFotoActual)

                    anadirImagenGaleria()

                }else{
                    // canceló la captura
                }
            }

            SOLICITUD_SELECCIONAR_FOTO->{
                if(resultCode == Activity.RESULT_OK){
                    mostrarBitmap(data?.data.toString())
                }
            }
        }
    }

    private fun mostrarBitmap(url:String){
        val uri = Uri.parse(url)
        val stream = activity.contentResolver.openInputStream(uri)
        val imageBitmap = BitmapFactory.decodeStream(stream)
        imageView.setImageBitmap(imageBitmap)
    }

    private fun crearArchivoImagen(): File{
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val nombreArchivoImagen = "JPEG_" + timeStamp + "_"

        //val directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val directorio = Environment.getExternalStorageDirectory()
        val directorioPictures = File(directorio.absolutePath + "/Pictures/")
        val imagen = File.createTempFile(nombreArchivoImagen,".jpg", directorioPictures)

        urlFotoActual = "file://" + imagen.absolutePath

        return imagen
    }

    private fun anadirImagenGaleria(){
        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val file = File(urlFotoActual)
        val uri = Uri.fromFile(file)
        intent.setData(uri)
        activity.sendBroadcast(intent)
    }
}