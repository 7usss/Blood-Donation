package com.example.myapplicationautho.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.example.myapplicationautho.R
import com.example.myapplicationautho.databinding.ActivityAddEmergencyPostBinding
import com.example.myapplicationautho.model.EmergencyPost
import com.example.myapplicationautho.utility.EMERGENCY_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddEmergencyPostActivity : AppCompatActivity() {

    private val context = this@AddEmergencyPostActivity

    private lateinit var binding: ActivityAddEmergencyPostBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    private lateinit var emergencyPost: EmergencyPost
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEmergencyPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        initListeners()
        checkIntent()
    }

    private fun checkIntent(){
        if(intent.hasExtra("emergencyPost")){
            emergencyPost = intent.getSerializableExtra("emergencyPost") as EmergencyPost
            setData()
        }
    }

    private fun setData(){
        binding.tvBlood.text = emergencyPost.bloodType
        binding.tvCity.text = emergencyPost.city
        binding.etPhone.setText(emergencyPost.phone)
        binding.etAge.setText(emergencyPost.age)
        binding.etPatientNumber.setText(emergencyPost.patientNumber)
        binding.etNotes.setText(emergencyPost.notes)
    }

    private fun initFirebase(){
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun initListeners(){
        binding.tvBlood.setOnClickListener {
            val popupMenu = PopupMenu(context, binding.tvBlood)

            popupMenu.menu.add(Menu.NONE, 1, Menu.NONE, "A")
            popupMenu.menu.add(Menu.NONE, 2, Menu.NONE, "B")
            popupMenu.menu.add(Menu.NONE, 3, Menu.NONE, "AB")
            popupMenu.menu.add(Menu.NONE, 4, Menu.NONE, "O")

            popupMenu.setOnMenuItemClickListener { menuItem -> // Toast message on menu item clicked
                binding.tvBlood.text = menuItem.title
                true
            }
            // Showing the popup menu
            popupMenu.show()
        }

        binding.tvCity.setOnClickListener {
            val popupMenu = PopupMenu(context, binding.tvBlood)

            popupMenu.menu.add(Menu.NONE, 1, Menu.NONE, "Riad")
            popupMenu.menu.add(Menu.NONE, 2, Menu.NONE, "Gada")
            popupMenu.menu.add(Menu.NONE, 3, Menu.NONE, "Yanbaa")

            popupMenu.setOnMenuItemClickListener { menuItem -> // Toast message on menu item clicked
                binding.tvCity.text = menuItem.title
                true
            }
            // Showing the popup menu
            popupMenu.show()
        }

        binding.ibBack.setOnClickListener {
            finish()
        }

        binding.btnSubmit.setOnClickListener {
            if(validateForm()){

                if(::emergencyPost.isInitialized){
                    emergencyPost.bloodType = binding.tvBlood.text.toString()
                    emergencyPost.city = binding.tvCity.text.toString()
                    emergencyPost.phone = binding.etPhone.text.toString()
                    emergencyPost.age = binding.etAge.text.toString()
                    emergencyPost.patientNumber = binding.etPatientNumber.text.toString()
                    emergencyPost.notes = binding.etNotes.text.toString()
                    emergencyPost.userKey = mAuth.currentUser!!.uid

                    mFirestore.collection(EMERGENCY_COLLECTION).document(emergencyPost.key).set(emergencyPost)
                    finish()
                    Toast.makeText(context, "Post updated", Toast.LENGTH_SHORT).show()
                }else{
                    val emergencyPost= EmergencyPost()
                    emergencyPost.bloodType = binding.tvBlood.text.toString()
                    emergencyPost.city = binding.tvCity.text.toString()
                    emergencyPost.phone = binding.etPhone.text.toString()
                    emergencyPost.age = binding.etAge.text.toString()
                    emergencyPost.patientNumber = binding.etPatientNumber.text.toString()
                    emergencyPost.notes = binding.etNotes.text.toString()
                    emergencyPost.userKey = mAuth.currentUser!!.uid

                    mFirestore.collection(EMERGENCY_COLLECTION).add(emergencyPost)
                    finish()
                    Toast.makeText(context, "Post added", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validateForm(): Boolean {
        var valid = true

        val mBlood = binding.tvBlood.text.toString()
        if (TextUtils.isEmpty(mBlood)) {
            binding.tvBlood.error = getString(R.string.error_required)
            valid = false
        } else {
            binding.tvBlood.error = null
        }

        val mCity = binding.tvCity.text.toString()
        if (TextUtils.isEmpty(mCity)) {
            binding.tvCity.error = getString(R.string.error_required)
            valid = false
        } else {
            binding.tvCity.error = null
        }

        val mAge = binding.etAge.text.toString()
        if (TextUtils.isEmpty(mAge)) {
            binding.etAge.error = getString(R.string.error_required)
            valid = false
        } else {
            binding.etAge.error = null
        }

        val mNotes = binding.etNotes.text.toString()
        if (TextUtils.isEmpty(mNotes)) {
            binding.etNotes.error = getString(R.string.error_required)
            valid = false
        } else {
            binding.etNotes.error = null
        }

        return valid
    }

}