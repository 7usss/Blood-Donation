package com.example.myapplicationautho.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplicationautho.R
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class Fragmentadapter (e:FragmentManager,w:Lifecycle):FragmentStateAdapter(e,w) {

    var fragmentlist:ArrayList<Fragment> = ArrayList()
    var fragmenttitle:ArrayList<String> = ArrayList()
    override fun getItemCount(): Int {
        return fragmentlist.size

    }

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position]

    }
    fun addfragment(frag:Fragment,title:String){
        fragmentlist.add(frag)
        fragmenttitle.add(title)
    }
}