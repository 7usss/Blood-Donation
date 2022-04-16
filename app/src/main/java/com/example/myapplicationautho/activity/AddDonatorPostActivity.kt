package com.example.myapplicationautho.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import com.example.myapplicationautho.R
import com.example.myapplicationautho.databinding.ActivityAddDonatorPostBinding
import com.example.myapplicationautho.model.DonatorPost
import com.example.myapplicationautho.utility.DONATOR_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddDonatorPostActivity : AppCompatActivity() {

    private val context = this@AddDonatorPostActivity

    private lateinit var binding: ActivityAddDonatorPostBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    private lateinit var donatorPost: DonatorPost

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddDonatorPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        initListeners()
        checkIntent()
    }

    private fun checkIntent(){
        if(intent.hasExtra("donatorPost")){
            donatorPost = intent.getSerializableExtra("donatorPost") as DonatorPost
            setData()
        }
    }

    private fun setData(){
        binding.tvBlood.text = donatorPost.bloodType
        binding.tvCity.text = donatorPost.city
        binding.etPhone.setText(donatorPost.phone)
        binding.etAge.setText(donatorPost.age)
        binding.etPatientNumber.setText(donatorPost.patientNumber)
        binding.etNotes.setText(donatorPost.notes)
    }

    private fun initFirebase(){
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun initListeners(){
        binding.tvBlood.setOnClickListener {
            val popupMenu = PopupMenu(context, binding.tvBlood)

            popupMenu.menu.add(Menu.NONE, 1, Menu.NONE, "A+")
            popupMenu.menu.add(Menu.NONE, 2, Menu.NONE, "A-")
            popupMenu.menu.add(Menu.NONE, 3, Menu.NONE, "B+")
            popupMenu.menu.add(Menu.NONE, 4, Menu.NONE, "B-")
            popupMenu.menu.add(Menu.NONE, 5, Menu.NONE, "AB+")
            popupMenu.menu.add(Menu.NONE, 6, Menu.NONE, "AB-")
            popupMenu.menu.add(Menu.NONE, 7, Menu.NONE, "O+")

            popupMenu.setOnMenuItemClickListener { menuItem -> // Toast message on menu item clicked
                binding.tvBlood.text = menuItem.title
                true
            }
            // Showing the popup menu
            popupMenu.show()
        }

        binding.tvCity.setOnClickListener {
            val popupMenu = PopupMenu(context, binding.tvBlood)

            popupMenu.menu.add(Menu.NONE, 1, Menu.NONE, "Riyadh")
            popupMenu.menu.add(Menu.NONE, 2, Menu.NONE, "Jeddah")
            popupMenu.menu.add(Menu.NONE, 3, Menu.NONE, "Yanbu")

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

                if(::donatorPost.isInitialized){
                    donatorPost.bloodType = binding.tvBlood.text.toString()
                    donatorPost.city = binding.tvCity.text.toString()
                    donatorPost.phone = binding.etPhone.text.toString()
                    donatorPost.age = binding.etAge.text.toString()
                    donatorPost.patientNumber = binding.etPatientNumber.text.toString()
                    donatorPost.notes = binding.etNotes.text.toString()
                    donatorPost.userKey = mAuth.currentUser!!.uid

                    mFirestore.collection(DONATOR_COLLECTION).document(donatorPost.key).set(donatorPost)
                    finish()
                    Toast.makeText(context, "Post updated", Toast.LENGTH_SHORT).show()
                }else{
                    val donatorPost= DonatorPost()
                    donatorPost.bloodType = binding.tvBlood.text.toString()
                    donatorPost.city = binding.tvCity.text.toString()
                    donatorPost.phone = binding.etPhone.text.toString()
                    donatorPost.age = binding.etAge.text.toString()
//                    donatorPost.patientNumber = binding.etPatientNumber.text.toString()
                    donatorPost.notes = binding.etNotes.text.toString()
                    donatorPost.userKey = mAuth.currentUser!!.uid

                    mFirestore.collection(DONATOR_COLLECTION).add(donatorPost)
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

        val mPhone = binding.etPhone.text.toString()
        if (TextUtils.isEmpty(mPhone)) {
            binding.etPhone.error = getString(R.string.error_required)
            valid = false
        } else {
            binding.etPhone.error = null
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