package com.example.myapplicationautho.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplicationautho.R

class ethics_of_donating_blood : AppCompatActivity() {
    private var rrr : TextView ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ethics_of_donating_blood)
        connectview()
        goto_ethic_ar()
    }

    private fun goto_ethic_ar() {
        rrr?.setOnClickListener {
            var x: Intent = Intent(this, ethics_ar::class.java)
            startActivity(x)
        }
    }

    private fun connectview() {
        rrr = findViewById(R.id.btnGoto_ar_ethics)
    }
}