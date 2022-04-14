package com.example.myapplicationautho.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplicationautho.R
import com.example.myapplicationautho.activity.AddDonatorPostActivity
import com.example.myapplicationautho.activity.AddNeederActivity
import com.example.myapplicationautho.adapter.DonatorAdapter
import com.example.myapplicationautho.adapter.NeederAdapter
import com.example.myapplicationautho.databinding.FragmentDonatorBinding
import com.example.myapplicationautho.databinding.FragmentNeederBinding
import com.example.myapplicationautho.model.DonatorPost
import com.example.myapplicationautho.model.NeederPost
import com.example.myapplicationautho.model.User
import com.example.myapplicationautho.utility.NEEDER_COLLECTION
import com.example.myapplicationautho.utility.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class NeederFragment : Fragment(R.layout.fragment_needer),
    NeederAdapter.Interaction {

    private lateinit var binding: FragmentNeederBinding

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore: FirebaseFirestore

    private var neederPostsList: MutableList<NeederPost> = mutableListOf()
    private var neederPostsListBackup: MutableList<NeederPost> = mutableListOf()

    private lateinit var neederAdapter: NeederAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNeederBinding.bind(view)

        initFirebase()
        initListeners()
        initRecyclerView()
    }
    private fun initFirebase(){
        mAuth = FirebaseAuth.getInstance()
        mFirestore = FirebaseFirestore.getInstance()
    }

    private fun initRecyclerView(){
        neederAdapter = NeederAdapter(requireActivity(), this, neederPostsList)
        binding.rvNeeder.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvNeeder.setHasFixedSize(true)
        binding.rvNeeder.adapter = neederAdapter
    }

    private fun initListeners(){
        binding.fabAdd.setOnClickListener {
            startActivity(Intent(requireContext(), AddNeederActivity::class.java))
        }
    }

    private fun getNeederPosts(){
        mFirestore.collection(NEEDER_COLLECTION).get().addOnSuccessListener { result ->
            for (document in result){
                val neederPost = document.toObject(NeederPost::class.java)
                neederPost.key = document.id
                getUserData(neederPost)
            }
        }.addOnFailureListener {
            print(it)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getUserData(neederPost: NeederPost){
        mFirestore.collection(USERS_COLLECTION).document(neederPost.userKey).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val user = document.toObject(User::class.java)
                    neederPost.user = user!!
                    neederPostsList.add(neederPost)
                    neederPostsListBackup.add(neederPost)
                    neederAdapter.notifyDataSetChanged()
                }
            }
            .addOnFailureListener { exception ->

            }
    }

    override fun onResume() {
        super.onResume()
        neederPostsList.clear()
        neederPostsListBackup.clear()
        getNeederPosts()
    }
    
    override fun edit(position: Int, neederPost: NeederPost) {
        val intent = Intent(requireContext(), AddNeederActivity::class.java)
        intent.putExtra("neederPost", neederPost)
        startActivity(intent)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun delete(position: Int, neederPost: NeederPost) {
        mFirestore.collection(NEEDER_COLLECTION).document(neederPost.key).delete()
        neederPostsList.removeAt(position)
        neederAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByCity(city: String) {
        neederPostsList.clear()
        if(city == "All"){
            neederPostsList.addAll(neederPostsListBackup)
        }else{
            for (needer in neederPostsListBackup){
                if(needer.city == city){
                    neederPostsList.add(needer)
                }
            }
        }
        neederAdapter.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun filterByBloodType(bloodType: String) {
        neederPostsList.clear()
        if(bloodType == "All"){
            neederPostsList.addAll(neederPostsListBackup)
        }else{
            for (needer in neederPostsListBackup){
                if(needer.bloodType == bloodType){
                    neederPostsList.add(needer)
                }
            }
        }
        neederAdapter.notifyDataSetChanged()
    }

}
