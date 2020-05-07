package com.vidamrr.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val video:Video = Video()
        video.render()

        val publicidad:Publicidad = Video()
        publicidad.render()

        val multimedia:Multimedia = Video()
        multimedia.render()
    }
}
