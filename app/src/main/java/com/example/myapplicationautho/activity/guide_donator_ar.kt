package com.example.myapplicationautho.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplicationautho.R

class guide_donator_ar : AppCompatActivity() {
    private var ggg :TextView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_donator_ar)
        connectview()
        goto_ethic_ar()
    }

    private fun goto_ethic_ar() {
        ggg?.setOnClickListener {
            var x: Intent = Intent(this, ethics_ar::class.java)
            startActivity(x)
        }
    }

    private fun connectview() {
        ggg = findViewById(R.id.textView12)
    }
}