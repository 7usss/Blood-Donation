package com.example.myapplicationautho


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class Login() : AppCompatActivity()  {
    private var GoToRegisterPage:TextView?= null
    private var Emailinloin:EditText?= null
    private var Passwordinlogin:EditText?= null
    private var buttoninlogin :Button?= null
    private var forgotpassword :TextView?= null
    private var Authe: FirebaseAuth? = null
    private var progrissbar:ProgressBar?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        connectview()
        Authe= FirebaseAuth.getInstance()
        LoginToMainActivity()
        movetoregisterpage()

    }

    private fun LoginToMainActivity() {

        buttoninlogin?.setOnClickListener {
            var email2 = Emailinloin?.text.toString().trim()
            var password2 = Passwordinlogin?.text.toString().trim()


            Authe?.signInWithEmailAndPassword(email2,password2)?.addOnCompleteListener {
                if (it.isSuccessful) {
                    progrissbar?.visibility = View.VISIBLE
                    var move_to_main_activity1: Intent = Intent(this, MainActivity::class.java)
                    move_to_main_activity1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    finish()
                    startActivity(move_to_main_activity1)
                }else{
                    progrissbar?.visibility = View.INVISIBLE
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
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
            var i =Intent(this,register::class.java)
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
    }


}