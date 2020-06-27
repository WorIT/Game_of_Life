package com.example.gameoflife.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.activities.GameActivity
import com.google.android.gms.ads.*

class MainFragment : Fragment() {
    private var card_start: CardView? = null
    private var card_patterns: CardView? = null
    private var adview: AdView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_main, container, false)
        init(view)
        setListeners()

        MobileAds.initialize(activity,"ca-app-pub-3940256099942544/1033173712")
        val adReq = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        adview?.loadAd(adReq)

        return view
    }




    private fun init(view: View) {
        card_start = view.findViewById(R.id.main_card_start)
        card_patterns = view.findViewById(R.id.pattern_card_patterns)

        adview = view.findViewById(R.id.bannermain)

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