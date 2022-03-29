package com.example.myapplicationautho

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class DonatorFragment : Fragment() {
    var v:View? =null
    private lateinit var recyv:RecyclerView
    private lateinit var adapterDonator:AdapterDonator



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

        RecyclerView(view)
        prepaerArray()
    }

    private fun RecyclerView(view: View) {
        recyv = v?.findViewById(R.id.recycler_donator)!!


    }

    private fun prepaerArray(){
        val array:ArrayList<UserData1> = ArrayList()
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        array.add(UserData1("abdullah","City","A+","0538877972"))
        val customAdapter:AdapterDonator = AdapterDonator(this.context,array)
        recyv.layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.VERTICAL,false)
        recyv.adapter = customAdapter

    }


}