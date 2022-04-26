package com.example.myapplicationautho.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationautho.activity.MainActivity
import com.example.myapplicationautho.databinding.LayoutDonatorPostItemBinding
import com.example.myapplicationautho.model.DonatorPost
import java.lang.StringBuilder


class DonatorAdapter(private val activity: Activity,
                     private val interaction: Interaction?,
                     private var donatorPostsList: MutableList<DonatorPost>) :
    RecyclerView.Adapter<DonatorAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutDonatorPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = donatorPostsList[position]
        holder.setData(current, position)
    }

    override fun getItemCount(): Int = donatorPostsList.size

    inner class MyViewHolder(private val binding: LayoutDonatorPostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var pos: Int = 0
        private var current: DonatorPost? = null

        @SuppressLint("CheckResult")
        fun setData(current: DonatorPost?, position: Int) {

            current?.let { donatorPost ->

                binding.tvUsername.text = donatorPost.user.name

                val stringBuilder = StringBuilder()
                stringBuilder.append("Blood type: ")
                stringBuilder.append(donatorPost.bloodType)
                stringBuilder.append("\n\n")

                stringBuilder.append("City: ")
                stringBuilder.append(donatorPost.city)
                stringBuilder.append("\n\n")

                stringBuilder.append("Phone Number: ")
                stringBuilder.append(donatorPost.phone)
                stringBuilder.append("\n\n")

                stringBuilder.append("Age: ")
                stringBuilder.append(donatorPost.age)
                stringBuilder.append("\n\n")

                stringBuilder.append("Patient Number: ")
                stringBuilder.append(donatorPost.patientNumber)
                stringBuilder.append("\n\n")

                stringBuilder.append("Notes: ")
                stringBuilder.append(donatorPost.notes)
                stringBuilder.append("\n\n")

                binding.tvText.text = stringBuilder.toString()

                if(MainActivity.user.key == current.userKey){
                    binding.ibOptions.visibility = View.VISIBLE
                    binding.ibOptions.setOnClickListener {
                        val popupMenu = PopupMenu(activity, binding.ibOptions)

                        popupMenu.menu.add(Menu.NONE, 1, Menu.NONE, "Edit")
                        popupMenu.menu.add(Menu.NONE, 2, Menu.NONE, "Delete")

                        popupMenu.setOnMenuItemClickListener { menuItem -> // Toast message on menu item clicked
                            when(menuItem.itemId){
                                1 -> {
                                    interaction?.edit(position, current)
                                }

                                2 -> {
                                    interaction?.delete(position, current)
                                }
                            }
                            true
                        }
                        // Showing the popup menu
                        popupMenu.show()
                    }
                }else{
                    binding.ibOptions.visibility = View.GONE
                }

            }

            binding.tvText.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${current?.phone}"))
                itemView.context.startActivity(intent)
            }

            this.pos = position
            this.current = current
        }
    }

    interface Interaction {
        fun edit(position: Int, donatorPost: DonatorPost)
        fun delete(position: Int, donatorPost: DonatorPost)
    }


}
