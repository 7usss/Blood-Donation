package com.example.myapplicationautho.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.example.myapplicationautho.R
import com.example.myapplicationautho.databinding.ActivityAddNeederBinding
import com.example.myapplicationautho.model.DonatorPost
import com.example.myapplicationautho.model.NeederPost
import com.example.myapplicationautho.utility.NEEDER_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AddNeederActivity : AppCompatActivity() {

    private val context = this@AddNeederActivity

    private lateinit var binding: ActivityAddNeederBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    private lateinit var neederPost: NeederPost
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNeederBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebase()
        initListeners()
        checkIntent()
    }

    private fun checkIntent(){
        if(intent.hasExtra("neederPost")){
            neederPost = intent.getSerializableExtra("neederPost") as NeederPost
            setData()
        }
    }

    private fun setData(){
        binding.tvBlood.text = neederPost.bloodType
        binding.tvCity.text = neederPost.city
        binding.etPhone.setText(neederPost.phone)
        binding.etAge.setText(neederPost.age)
        binding.etPatientNumber.setText(neederPost.patientNumber)
        binding.etNotes.setText(neederPost.notes)
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

                if(::neederPost.isInitialized){
                    neederPost.bloodType = binding.tvBlood.text.toString()
                    neederPost.city = binding.tvCity.text.toString()
                    neederPost.phone = binding.etPhone.text.toString()
                    neederPost.age = binding.etAge.text.toString()
                    neederPost.patientNumber = binding.etPatientNumber.text.toString()
                    neederPost.notes = binding.etNotes.text.toString()
                    neederPost.userKey = mAuth.currentUser!!.uid

                    mFirestore.collection(NEEDER_COLLECTION).document(neederPost.key).set(neederPost)
                    finish()
                    Toast.makeText(context, "Post updated", Toast.LENGTH_SHORT).show()
                }else{
                    val neederPost= NeederPost()
                    neederPost.bloodType = binding.tvBlood.text.toString()
                    neederPost.city = binding.tvCity.text.toString()
                    neederPost.phone = binding.etPhone.text.toString()
                    neederPost.age = binding.etAge.text.toString()
                    neederPost.patientNumber = binding.etPatientNumber.text.toString()
                    neederPost.notes = binding.etNotes.text.toString()
                    neederPost.userKey = mAuth.currentUser!!.uid

                    mFirestore.collection(NEEDER_COLLECTION).add(neederPost)
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