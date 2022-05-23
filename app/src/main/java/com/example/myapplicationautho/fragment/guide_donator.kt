package com.example.myapplicationautho.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.myapplicationautho.R
import com.example.myapplicationautho.activity.Login
import com.example.myapplicationautho.activity.ethics_of_donating_blood
import com.example.myapplicationautho.activity.guide_donator_ar
import org.w3c.dom.Text
import android.content.Intent as Intent1


class guide_donator : Fragment() {
 private  var gotoethic :Button ?= null
    private var gotoartext :TextView ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_guide_donator, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectview(view)
        gotoetic_page()
        goto_ar_page()

    }

    private fun goto_ar_page() {
        gotoartext?.setOnClickListener {
            var x: Intent1 = Intent1(context, ethics_of_donating_blood::class.java)
            startActivity(x)
        }
    }

    private fun gotoetic_page() {
        gotoethic?.setOnClickListener {
            var x: Intent1 = Intent1(context, guide_donator_ar::class.java)
            startActivity(x)

        }
    }

    private fun connectview(view: View) {
        gotoethic = view.findViewById(R.id.btnGotoithical)
        gotoartext = view.findViewById(R.id.textView13)


    }
}


