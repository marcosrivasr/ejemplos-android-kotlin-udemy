package com.vidamrr.ejemplocamaracustom

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Camera
import android.graphics.ImageFormat
import android.graphics.SurfaceTexture
import android.hardware.camera2.*
import android.media.Image
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.widget.Button
import android.widget.ImageView
import android.os.HandlerThread
import android.media.ImageReader
import android.os.Environment
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.util.Size
import android.util.SparseArray
import android.util.SparseIntArray
import android.view.Surface
import android.view.TextureView
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*


class MainActivity : AppCompatActivity() {

    var bCapturar:Button? = null
    var preview:TextureView? = null

    var cameraDevice:CameraDevice? = null
    var file:File? = null
    var backgroundHandler:Handler? = null
    var backgroundThread:HandlerThread? = null
    var imageDimension:Size? = null
    var captureRequestBuilder:CaptureRequest.Builder? = null
    var cameraCaptureSessions:CameraCaptureSession? = null
    var cameraId = ""

    val REQUEST_CAMERA_PERMISSION = 100

    val ORIENTATIONS = SparseIntArray()


    var stateCallback:CameraDevice.StateCallback? = null
    var textureListener:TextureView.SurfaceTextureListener? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bCapturar = findViewById(R.id.bCapturar)
        preview = findViewById(R.id.preview)

        ORIENTATIONS.append(Surface.ROTATION_0, 0)
        ORIENTATIONS.append(Surface.ROTATION_90, 90)
        ORIENTATIONS.append(Surface.ROTATION_180, 180)
        ORIENTATIONS.append(Surface.ROTATION_270, 270)

        bCapturar?.setOnClickListener {
            takePicture()
        }


        textureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {}
            override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {}
            override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean { return false}

            override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
                openCamera()
            }
        }

        stateCallback = object: CameraDevice.StateCallback(){
            override fun onOpened(camera: CameraDevice?) {
                //cuando se abre la cámara
                cameraDevice = camera

                createCameraPreview()
            }

            override fun onDisconnected(p0: CameraDevice?) {
                cameraDevice?.close()
            }

            override fun onError(p0: CameraDevice?, p1: Int) {
                cameraDevice?.close()
                cameraDevice = null
            }

        }

        val captureCallbackListener = object : CameraCaptureSession.CaptureCallback(){

            override fun onCaptureCompleted(session: CameraCaptureSession?, request: CaptureRequest?, result: TotalCaptureResult?) {
                super.onCaptureCompleted(session, request, result)
                // captura completada
                createCameraPreview()

            }
        }
    }


    fun openCamera(){
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        // número de cámara
        cameraId = cameraManager.cameraIdList[0]


        val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraId)
        // se obtiene el tipo de configuraciones de stream del teléfono
        val streamConfigurationMap = cameraCharacteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)

        imageDimension = streamConfigurationMap.getOutputSizes(SurfaceTexture::class.java)[0]

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CAMERA_PERMISSION)
                return
        }

        cameraManager.openCamera(cameraId, stateCallback, null)

    }

    fun createCameraPreview(){

        val textureSurface = preview?.surfaceTexture

        textureSurface?.setDefaultBufferSize(imageDimension?.width!!, imageDimension?.height!!)

        val surface = Surface(textureSurface)

        captureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW)
        captureRequestBuilder?.addTarget(surface)

        cameraDevice?.createCaptureSession(Arrays.asList(surface), object :CameraCaptureSession.StateCallback(){
            override fun onConfigureFailed(cameraCaptureSession: CameraCaptureSession?) {}

            override fun onConfigured(cameraCaptureSession: CameraCaptureSession?) {
                if (cameraDevice == null) return

                cameraCaptureSessions = cameraCaptureSession
                updatePreview()
            }

        }, null)
    }

    fun updatePreview(){
        if(cameraDevice == null) return

        captureRequestBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)
        cameraCaptureSessions?.setRepeatingRequest(captureRequestBuilder?.build(), null, backgroundHandler)
    }

    fun closeCamera(){
        if(cameraDevice != null){
            cameraDevice?.close()
            cameraDevice = null
        }

    }

    fun takePicture(){
        if(cameraDevice == null){
            return
        }

        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager

        try{
            val cameraCharacteristics = cameraManager.getCameraCharacteristics(cameraDevice?.id)

            var jpgSizes = arrayOf<Size>()

            if (cameraCharacteristics != null){
                jpgSizes = cameraCharacteristics
                        .get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP)
                        .getOutputSizes(ImageFormat.JPEG)

                var w = 640
                var h = 480

                if(jpgSizes != null && jpgSizes.isNotEmpty()){
                    w = jpgSizes[0].width
                    h = jpgSizes[0].height
                }

                val imageReader = ImageReader.newInstance(w, h, ImageFormat.JPEG, 1)

                val outputSurfaces = ArrayList<Surface>()

                outputSurfaces.add(imageReader.surface)
                outputSurfaces.add(Surface(preview?.surfaceTexture))

                val captureRequestBuilder = cameraDevice?.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE)

                captureRequestBuilder?.addTarget(imageReader.surface)
                captureRequestBuilder?.set(CaptureRequest.CONTROL_MODE, CameraMetadata.CONTROL_MODE_AUTO)

                val rotation = windowManager.defaultDisplay.rotation
                captureRequestBuilder?.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation))

                val file = File(Environment.getExternalStorageDirectory().absolutePath + "/Pictures/pic.jpg")

                val readerListener = object: ImageReader.OnImageAvailableListener{
                    override fun onImageAvailable(p0: ImageReader?) {
                        var image:Image? = null

                        image = imageReader.acquireLatestImage()

                        val buffer = image!!.planes[0].buffer
                        val bytes = ByteArray(buffer.capacity())
                        buffer.get(bytes)
                        save(bytes)
                    }

                    fun save(bytes: ByteArray){
                        var output:OutputStream? = null

                        output = FileOutputStream(file)
                        output.write(bytes)
                    }

                }

                imageReader.setOnImageAvailableListener(readerListener, backgroundHandler)

                val captureListener = object: CameraCaptureSession.CaptureCallback(){
                    override fun onCaptureCompleted(session: CameraCaptureSession?, request: CaptureRequest?, result: TotalCaptureResult?) {
                        super.onCaptureCompleted(session, request, result)

                        createCameraPreview()
                    }
                }

                cameraDevice?.createCaptureSession(outputSurfaces, object: CameraCaptureSession.StateCallback(){
                    override fun onConfigureFailed(session: CameraCaptureSession?) {

                    }

                    override fun onConfigured(session: CameraCaptureSession?) {
                        session?.capture(captureRequestBuilder?.build(), captureListener, backgroundHandler)
                    }

                }, backgroundHandler)


            }
        }catch (e:CameraAccessException){

        }
    }

    override fun onResume() {
        super.onResume()

        startBackgroundThread()

        if (preview?.isAvailable!!) {
            openCamera()
        } else {
            preview?.surfaceTextureListener = textureListener
        }
    }

    override fun onPause() {
        closeCamera();
        stopBackgroundThread()
        super.onPause()
    }

    fun startBackgroundThread(){
        backgroundThread = HandlerThread("Camera background")
        backgroundThread?.start()
        backgroundHandler = Handler(backgroundThread?.looper)
    }

    fun stopBackgroundThread(){
        backgroundThread?.quitSafely()
        backgroundThread?.join()
        backgroundThread = null
        backgroundHandler = null
    }


}
