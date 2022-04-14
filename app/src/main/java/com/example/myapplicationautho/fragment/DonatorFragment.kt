package com.example.myapplicationautho.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationautho.R
import com.example.myapplicationautho.activity.AddDonatorPostActivity
import com.example.myapplicationautho.adapter.DonatorAdapter
import com.example.myapplicationautho.databinding.FragmentDonatorBinding
import com.example.myapplicationautho.model.DonatorPost
import com.example.myapplicationautho.model.User
import com.example.myapplicationautho.utility.DONATOR_COLLECTION
import com.example.myapplicationautho.utility.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class DonatorFragment : Fragment(R.layout.fragment_donator),
    DonatorAdapter.Interaction{

    private lateinit var binding: FragmentDonatorBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    private var donatorPostsList: MutableList<DonatorPost> = mutableListOf()
    private var donatorPostsListBackup: MutableList<DonatorPost> = mutableListOf()

    private lateinit var donatorAdapter: DonatorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDonatorBinding.bind(view)

        initFirebase()
        initListeners()
        initRecyclerView()
    }

    private fun initFirebase(){
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun initRecyclerView(){
        donatorAdapter = DonatorAdapter(requireActivity(), this, donatorPostsList)
        binding.rvDonator.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDonator.setHasFixedSize(true)
        binding.rvDonator.adapter = donatorAdapter
    }

    private fun initListeners(){
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddDonatorPostActivity::class.java))
        }
    }

    private fun getDonatorPosts(){
        mFirestore.collection(DONATOR_COLLECTION).get().addOnSuccessListener { result ->
            for (document in result){
                val donatorPost = document.toObject(DonatorPost::class.java)
                donatorPost.key = document.id
                getUserData(donatorPost)
            }
        }.addOnFailureListener {
            print(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData(donatorPost: DonatorPost){
        mFirestore.collection(USERS_COLLECTION).document(donatorPost.userKey).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    donatorPost.user = user!!
                    donatorPostsList.add(donatorPost)
                    donatorPostsListBackup.add(donatorPost)
                    donatorAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->

            }
    }

    override fun onResume() {
        super.onResume()
        donatorPostsList.clear()
        donatorPostsListBackup.clear()
        getDonatorPosts()
    }

    override fun edit(position: Int, donatorPost: DonatorPost) {
        val intent = Intent(requireContext(), AddDonatorPostActivity::class.java)
        intent.putExtra("donatorPost", donatorPost)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun delete(position: Int, donatorPost: DonatorPost) {
        mFirestore.collection(DONATOR_COLLECTION).document(donatorPost.key).delete()
        donatorPostsList.removeAt(position)
        donatorAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByCity(city: String) {
        donatorPostsList.clear()
        if(city == "All"){
            donatorPostsList.addAll(donatorPostsListBackup)
        }else{
            for (donator in donatorPostsListBackup){
                if(donator.city == city){
                    donatorPostsList.add(donator)
                }
            }
        }
        donatorAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByBloodType(bloodType: String) {
        donatorPostsList.clear()
        if(bloodType == "All"){
            donatorPostsList.addAll(donatorPostsListBackup)
        }else{
            for (donator in donatorPostsListBackup){
                if(donator.bloodType == bloodType){
                    donatorPostsList.add(donator)
                }
            }
        }
        donatorAdapter.notifyDataSetChanged()
    }

}
