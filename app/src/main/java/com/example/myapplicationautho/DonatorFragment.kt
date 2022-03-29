package com.example.myapplicationautho

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class DonatorFragment : Fragment() {
    var v:View? =null
    private lateinit var recyv:RecyclerView
    private lateinit var adapterDonator:AdapterDonator
    private lateinit var addBtn:FloatingActionButton
    private lateinit var userList:ArrayList<UserData1>



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          v = inflater.inflate(R.layout.fragment_donator, container, false)
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        connectView(view)
        addBtn.setOnClickListener {
            addInfo()
        }
    }

    private fun connectView(view: View) {
        recyv = v?.findViewById(R.id.recycler_donator)!!
        addBtn = v?.findViewById(R.id.addingBtn)!!
        userList = ArrayList()
        adapterDonator = AdapterDonator(this.context,userList)
        recyv.layoutManager =  LinearLayoutManager(this.context)
        recyv.adapter = adapterDonator



    }
    private fun addInfo(){
        val inflater = LayoutInflater.from(this.context)
        val v = inflater.inflate(R.layout.add_item,null)
        val userName = v.findViewById<EditText>(R.id.UserName)
        val userCity = v.findViewById<EditText>(R.id.UserCity)
        val userBlood = v.findViewById<EditText>(R.id.UserBlood)
        val userPhone = v.findViewById<EditText>(R.id.UserPhone)
        val addDialog = AlertDialog.Builder(this.requireContext())
        addDialog.setView(v)
        addDialog.setPositiveButton("OK"){
            dialog,_->
            val names = userName.text.toString()
            val cities = userCity.text.toString()
            val bloodTypes = userBlood.text.toString()
            val pohoneNums = userPhone.text.toString()
            userList.add(UserData1("Name: $names","City: $cities"
                ,"BloodType: $bloodTypes","Phone: $pohoneNums"))
            adapterDonator.notifyDataSetChanged()
            Toast.makeText(this.context,"adding user information success",Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        addDialog.setNegativeButton("cancel"){
            dialog,_->
            dialog.dismiss()
            Toast.makeText(this.context,"cancel",Toast.LENGTH_LONG).show()

        }
        addDialog.create()
        addDialog.show()
    }

}