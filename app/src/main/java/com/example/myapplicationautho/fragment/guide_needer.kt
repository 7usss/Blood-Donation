package com.example.myapplicationautho.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.myapplicationautho.R
import com.example.myapplicationautho.activity.ethics_of_donating_blood
import com.example.myapplicationautho.activity.guide_needer_ar


class guide_needer : Fragment() {
    private var goto_needer : TextView?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_needer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectview(view)
        gotoneeder()
    }

    private fun gotoneeder() {
        goto_needer?.setOnClickListener {
            var x: Intent = Intent(context, guide_needer_ar::class.java)
            startActivity(x)
        }
    }

    private fun connectview(view: View) {
       goto_needer  = view.findViewById(R.id.btnGoto_needer)
    }
}
