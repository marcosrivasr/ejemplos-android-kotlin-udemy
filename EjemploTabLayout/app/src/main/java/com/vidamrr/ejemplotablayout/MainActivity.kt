package com.vidamrr.ejemplotablayout

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var viewPager:ViewPager? = null
    var tabLayout:TabLayout? = null

    var toolbar: Toolbar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar!!)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabs)

        configurarViewPager()
        tabLayout?.setupWithViewPager(viewPager)

        configurarIconos()




    }

    fun configurarIconos(){
        tabLayout?.getTabAt(0)!!.setIcon(R.drawable.icono01)
        tabLayout?.getTabAt(1)!!.setIcon(R.drawable.icono02)
        tabLayout?.getTabAt(2)!!.setIcon(R.drawable.icono03)
    }

    fun configurarViewPager(){
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(Fragmento01(), "Fragmento01")
        adapter.addFragment(Fragmento01(), "Fragmento02")
        adapter.addFragment(Fragmento01(), "Fragmento03")



        viewPager?.adapter = adapter
    }
}
