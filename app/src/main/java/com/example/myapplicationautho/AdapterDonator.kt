package com.example.myapplicationautho

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterDonator(val c: Context?, val array:ArrayList<UserData1>):RecyclerView.Adapter<AdapterDonator.ViewHolderIndex>(){



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AdapterDonator.ViewHolderIndex {
        val myViewInflater = LayoutInflater.from(viewGroup.context)
        val v = myViewInflater.inflate(R.layout.donator_item,viewGroup,false)
        return ViewHolderIndex(v)
    }

    override fun onBindViewHolder(holder: ViewHolderIndex, position: Int) {
        val userData: UserData1 = array.get(position)
        holder.name.setText(userData.userName)
        holder.city.setText(userData.userCity)
        holder.bloodType.setText(userData.userBlood)
        holder.phoneNum.setText(userData.userPhone)

    }

    override fun getItemCount(): Int {
        return array.size
    }
    class ViewHolderIndex(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val name =itemView.findViewById<TextView>(R.id.tvName)
        val city =itemView.findViewById<TextView>(R.id.tvLocation)
        val bloodType = itemView.findViewById<TextView>(R.id.tvBlood)
        val phoneNum = itemView.findViewById<TextView>(R.id.tvPhone)


    }
}
