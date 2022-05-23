package com.example.myapplicationautho.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplicationautho.R
import com.example.myapplicationautho.fragment.Fragmentadapter
import com.example.myapplicationautho.fragment.guide_donator
import com.example.myapplicationautho.fragment.guide_emergince
import com.example.myapplicationautho.fragment.guide_needer
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class guide_main : AppCompatActivity() {
    private var taplayout: TabLayout? = null
    private var frag : ViewPager2? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_main)
        connectview()
        tapconnect()
    }

    private fun connectview() {
        taplayout = findViewById(R.id.taplayout)
        frag = findViewById(R.id.fragpager)
    }

    private fun tapconnect() {

        var fAdabter: Fragmentadapter = Fragmentadapter(supportFragmentManager, lifecycle)
        fAdabter.addfragment(guide_donator(), "donator")
        fAdabter.addfragment(guide_needer(), "needer")
        fAdabter.addfragment(guide_emergince(), "emergince")
        frag?.adapter = fAdabter
        TabLayoutMediator(taplayout!!, frag!!) { tab, position ->
            tab.text = fAdabter.fragmenttitle[position]


        }
            .attach()
    }
}