package com.example.myapplicationautho.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationautho.R
import com.example.myapplicationautho.activity.AddEmergencyPostActivity
import com.example.myapplicationautho.activity.MainActivity
import com.example.myapplicationautho.adapter.EmergencyAdapter
import com.example.myapplicationautho.databinding.FragmentEmergencyBinding
import com.example.myapplicationautho.model.EmergencyPost
import com.example.myapplicationautho.model.NeederPost
import com.example.myapplicationautho.model.User
import com.example.myapplicationautho.utility.EMERGENCY_COLLECTION
import com.example.myapplicationautho.utility.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class EmergencyFragment : Fragment(R.layout.fragment_emergency),
    EmergencyAdapter.Interaction
{

    private lateinit var binding: FragmentEmergencyBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    private var emergencyPostsList: MutableList<EmergencyPost> = mutableListOf()
    private var emergencyPostsListBackup: MutableList<EmergencyPost> = mutableListOf()

    private lateinit var emergencyAdapter: EmergencyAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEmergencyBinding.bind(view)

        initFirebase()
        initListeners()
        initRecyclerView()
    }

    private fun initFirebase(){
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun initRecyclerView(){
        emergencyAdapter = EmergencyAdapter(requireActivity(), this, emergencyPostsList)
        binding.rvEmergency.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvEmergency.setHasFixedSize(true)
        binding.rvEmergency.adapter = emergencyAdapter
    }

    private fun initListeners(){

        if(MainActivity.user.isHospital){
            binding.fabAdd.visibility = View.VISIBLE
            binding.fabAdd.setOnClickListener {
                startActivity(Intent(requireContext(), AddEmergencyPostActivity::class.java))
            }
        }else{
            binding.fabAdd.visibility = View.GONE
        }
    }

    private fun getEmergencyPosts(){
        mFirestore.collection(EMERGENCY_COLLECTION).get().addOnSuccessListener { result ->
            for (document in result){
                val emergencyPost = document.toObject(EmergencyPost::class.java)
                emergencyPost.key = document.id
                getUserData(emergencyPost)
            }
        }.addOnFailureListener {
            print(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData(emergencyPost: EmergencyPost){
        mFirestore.collection(USERS_COLLECTION).document(emergencyPost.userKey).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    emergencyPost.user = user!!
                    emergencyPostsList.add(emergencyPost)
                    emergencyPostsListBackup.add(emergencyPost)
                    emergencyAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->

            }
    }

    override fun onResume() {
        super.onResume()
        emergencyPostsList.clear()
        emergencyPostsListBackup.clear()
        getEmergencyPosts()
    }

    override fun edit(position: Int, emergencyPost: EmergencyPost) {
        val intent = Intent(requireContext(), AddEmergencyPostActivity::class.java)
        intent.putExtra("emergencyPost", emergencyPost)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun delete(position: Int, emergencyPost: EmergencyPost) {
        mFirestore.collection(EMERGENCY_COLLECTION).document(emergencyPost.key).delete()
        emergencyPostsList.removeAt(position)
        emergencyAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByCity(city: String) {
        emergencyPostsList.clear()
        if(city == "All"){
            emergencyPostsList.addAll(emergencyPostsListBackup)
        }else{
            for (needer in emergencyPostsListBackup){
                if(needer.city == city){
                    emergencyPostsList.add(needer)
                }
            }
        }
        emergencyAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByBloodType(bloodType: String) {
        emergencyPostsList.clear()
        if(bloodType == "All"){
            emergencyPostsList.addAll(emergencyPostsListBackup)
        }else{
            for (needer in emergencyPostsListBackup){
                if(needer.bloodType == bloodType){
                    emergencyPostsList.add(needer)
                }
            }
        }
        emergencyAdapter.notifyDataSetChanged()
    }

}



