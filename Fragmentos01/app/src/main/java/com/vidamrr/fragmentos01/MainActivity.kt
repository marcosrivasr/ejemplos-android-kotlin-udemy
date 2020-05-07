package com.vidamrr.fragmentos01

import android.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity(), BlankFragment.BotonClickListener {

    var boton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        boton = findViewById(R.id.boton)



        boton!!.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            val newFragment = BlankFragment()

            fragmentTransaction.add(R.id.container, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()


        }
    }

    override fun botonClick() {
        Toast.makeText(this, "Holi", Toast.LENGTH_SHORT).show()
    }
}
