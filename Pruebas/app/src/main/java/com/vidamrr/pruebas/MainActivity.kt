package com.vidamrr.pruebas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..4) println(i)

        for (i in 10 downTo 1) println(i)

        for (i in 20 until 25) println(i)

        for (i in 1..10 step 2) println(i)


    }
}
