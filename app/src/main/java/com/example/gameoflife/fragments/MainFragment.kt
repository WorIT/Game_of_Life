package com.example.gameoflife.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.activities.GameActivity


class MainFragment : Fragment() {

    private var card_start : CardView? = null
    private var card_patterns : CardView? = null
    private lateinit var iv : ImageView



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_main, container, false)

        init(view)

        setListeners()



        return view
    }
    




    private fun init(view: View) {
        card_start = view.findViewById(R.id.main_card_start)
        card_patterns = view.findViewById(R.id.pattern_card_patterns)
    }


    private fun setListeners(){

        card_start?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(activity, GameActivity::class.java))
        })

        card_patterns?.setOnClickListener(View.OnClickListener {
            startFragment(PatternFragment())
        })

    }


    private fun startFragment(fragment: Fragment) {
        val FragmentTransaction = activity?.supportFragmentManager?.beginTransaction()
        FragmentTransaction!!.replace(R.id.cl_container, fragment)
        FragmentTransaction.addToBackStack(null)
        FragmentTransaction.commit()

    }



}