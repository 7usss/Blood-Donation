package com.example.myapplicationautho

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class register : AppCompatActivity() {

    private var Nameinregister:EditText? =null
    private var emailinregister:EditText? =null
    private var passwordinregister:EditText? =null
    private var phoneinregister:EditText? =null
    private var buttoninregister:Button? =null
    private var Authe:FirebaseAuth? = null
    private var firestore :FirebaseFirestore? = null
    private val CurrentUserRefDocRef:DocumentReference get() = firestore!!.document("Users/${Authe?.currentUser?.uid.toString()}")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        connectview()
        firestore=FirebaseFirestore.getInstance()
        Authe= FirebaseAuth.getInstance()
        newaccount()

    }

    private fun newaccount() {
        buttoninregister?.setOnClickListener {
            var name=Nameinregister?.text.toString().trim()
            var email= emailinregister?.text.toString().trim()
            var password = passwordinregister?.text.toString().trim()
            var phone = phoneinregister?.text.toString().trim().toInt()

            Authe?.createUserWithEmailAndPassword(email,password)?.addOnCompleteListener {
                val newUser = User(name,phone)
                CurrentUserRefDocRef.set(newUser)
                if (it.isSuccessful){
                    var move_to_main_activity:Intent= Intent(this,MainActivity::class.java)
                    move_to_main_activity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    finish()
                    startActivity(move_to_main_activity)
                }else{
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_LONG).show()
                }
            }

        }
    }

    private fun connectview() {
        Nameinregister = findViewById(R.id.Name)
        emailinregister = findViewById(R.id.email)
        passwordinregister = findViewById(R.id.pass)
        phoneinregister = findViewById(R.id.phone)
        buttoninregister = findViewById(R.id.buttonRegister)
    }
}