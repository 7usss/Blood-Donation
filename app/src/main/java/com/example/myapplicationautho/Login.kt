package com.example.myapplicationautho


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class Login() : AppCompatActivity()  {
    private var GoToRegisterPage:TextView?= null
    private var Emailinloin:EditText?= null
    private var Passwordinlogin:EditText?= null
    private var buttoninlogin :Button?= null
    private var forgotpassword :TextView?=null

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
        Passwordinlogin = findViewById(R.id.passwordET)
        buttoninlogin = findViewById(R.id.buttonLogin)
        forgotpassword = findViewById(R.id.forgotpasswordTV)
    }


}