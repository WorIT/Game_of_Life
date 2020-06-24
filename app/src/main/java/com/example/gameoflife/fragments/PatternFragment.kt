package com.example.gameoflife.fragments

import android.R.drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.gameoflife.R


class PatternFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_pattern, container, false)

        view.setOnClickListener(View.OnClickListener {})

        val iv = view.findViewById<ImageView>(R.id.pattern_iv_curent)
        iv.setImageResource(R.mipmap.ic_mario)





        return view
    }

}