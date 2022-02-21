package com.example.myapplicationautho


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView


var GoToRegisterPage:TextView?= null
var Emailinloin:EditText?= null

class Login() : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        connectview()
        movetoregisterpage()
    }

    private fun movetoregisterpage() {
        GoToRegisterPage?.setOnClickListener {
            var i =Intent(this,register::class.java)
            startActivity(i)
        }
    }

    private fun connectview() {
        GoToRegisterPage=findViewById(R.id.goToregisterpageTV)
        Emailinloin = findViewById(R.id.emailTV)
    }


}