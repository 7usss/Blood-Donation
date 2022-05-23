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
import com.example.myapplicationautho.activity.guide_emergincy_ar


class guide_emergince : Fragment() {
    private var gotoemer_ar: TextView?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_emergince, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectview(view)
        gotoemergincy()
    }

    private fun connectview(view: View) {

            gotoemer_ar = view.findViewById(R.id.btnGoto_emer_ar)



    }

    private fun gotoemergincy() {
        gotoemer_ar?.setOnClickListener {
            var x: Intent = Intent(context, guide_emergincy_ar::class.java)
            startActivity(x)
        }


    }
}
