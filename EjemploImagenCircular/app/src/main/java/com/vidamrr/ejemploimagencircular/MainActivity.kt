package com.vidamrr.ejemploimagencircular

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mikhaellopez.circularimageview.CircularImageView


class MainActivity : AppCompatActivity() {

    var foto:CircularImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        foto = findViewById(R.id.foto)

        foto?.setImageResource(R.drawable.foto)

    }
}
