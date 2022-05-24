package com.example.myapplicationautho.activity


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.myapplicationautho.R
import com.google.firebase.auth.FirebaseAuth

class Login() : AppCompatActivity()  {
    private var GoToRegisterPage:TextView?= null
    private var Emailinloin:EditText?= null
    private var Passwordinlogin:EditText?= null
    private var buttoninlogin :Button?= null
    private var forgotpassword :TextView?= null
    private var Authe: FirebaseAuth? = null
    private var progrissbar:ProgressBar?=null
    private var SendEmail:TextView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        connectview()
        Authe= FirebaseAuth.getInstance()
        LoginToMainActivity()
        movetoregisterpage()
        sendemailto_blooddonation_team()

    }

    private fun sendemailto_blooddonation_team() {
        SendEmail?.setOnClickListener {
            val emailintent =Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto","blooddonation258@gmail.com",null))
            startActivity(Intent.createChooser(emailintent,"Send email..."))
        }
    }

    private fun LoginToMainActivity() {

        buttoninlogin?.setOnClickListener {
            var email2 = Emailinloin?.text.toString().trim()
            var password2 = Passwordinlogin?.text.toString().trim()

            if (email2.isEmpty()){
                Emailinloin?.error = "Enter Your Email"
                Emailinloin?.requestFocus()
                return@setOnClickListener
            }
            if (password2.isEmpty()){
                Passwordinlogin?.error = "Enter Your Password"
                Passwordinlogin?.requestFocus()
                return@setOnClickListener
            }
            if (password2.length < 6 ){
                Passwordinlogin?.error = "Enter 6 char"
                Passwordinlogin?.requestFocus()
                return@setOnClickListener
            }




            Authe?.signInWithEmailAndPassword(email2,password2)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    progrissbar?.visibility = View.VISIBLE
                    var move_to_main_activity1: Intent = Intent(this, MainActivity::class.java)
                    move_to_main_activity1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    finish()
                    startActivity(move_to_main_activity1)
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                    progrissbar?.visibility = View.GONE
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        if (Authe?.currentUser?.uid!= null){
            var move_to_main_activity: Intent = Intent(this, MainActivity::class.java)
            finish()
            startActivity(move_to_main_activity)
        }

    }

    private fun movetoregisterpage() {
        GoToRegisterPage?.setOnClickListener {
            var i =Intent(this, register::class.java)
            startActivity(i)
        }
    }

    private fun connectview() {
        progrissbar=findViewById(R.id.progress_par)
        GoToRegisterPage=findViewById(R.id.goToregisterpageTV)
        Emailinloin = findViewById(R.id.emailTV)
        Passwordinlogin = findViewById(R.id.passwordET)
        buttoninlogin = findViewById(R.id.buttonLogin)
        forgotpassword = findViewById(R.id.forgotpasswordTV)
        SendEmail = findViewById(R.id.send_email)
    }


}