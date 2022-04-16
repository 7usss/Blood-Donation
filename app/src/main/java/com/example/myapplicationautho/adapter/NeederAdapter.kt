package com.example.myapplicationautho.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationautho.activity.MainActivity
import com.example.myapplicationautho.databinding.LayoutNeederPostItemBinding
import com.example.myapplicationautho.model.NeederPost
import java.lang.StringBuilder


class NeederAdapter(private val activity: Activity,
                    private val interaction: Interaction?,
                    private var neederPostsList: MutableList<NeederPost>) :
    RecyclerView.Adapter<NeederAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = LayoutNeederPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val current = neederPostsList[position]
        holder.setData(current, position)
    }

    override fun getItemCount(): Int = neederPostsList.size

    inner class MyViewHolder(private val binding: LayoutNeederPostItemBinding) : RecyclerView.ViewHolder(binding.root) {

        private var pos: Int = 0
        private var current: NeederPost? = null

        @SuppressLint("CheckResult")
        fun setData(current: NeederPost?, position: Int) {

            current?.let { NeederPost ->

                binding.tvUsername.text = NeederPost.user.name

                val stringBuilder = StringBuilder()
                stringBuilder.append("Blood type: ")
                stringBuilder.append(NeederPost.bloodType)
                stringBuilder.append("\n\n")

                stringBuilder.append("City: ")
                stringBuilder.append(NeederPost.city)
                stringBuilder.append("\n\n")

                stringBuilder.append("Phone Number: ")
                stringBuilder.append(NeederPost.phone)
                stringBuilder.append("\n\n")

                stringBuilder.append("Age: ")
                stringBuilder.append(NeederPost.age)
                stringBuilder.append("\n\n")

                stringBuilder.append("Patient Number: ")
                stringBuilder.append(NeederPost.patientNumber)
                stringBuilder.append("\n\n")

                stringBuilder.append("Notes: ")
                stringBuilder.append(NeederPost.notes)
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

            this.pos = position
            this.current = current
        }
    }

    interface Interaction {
        fun edit(position: Int, neederPost: NeederPost)
        fun delete(position: Int, neederPost: NeederPost)
    }


}
