package com.example.gameoflife.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.google.android.gms.ads.*


class GameOverFragment : Fragment() {
    private var adView: AdView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view =  inflater.inflate(R.layout.fragment_ad_mod, container, false)


        adView= view.findViewById<AdView>(R.id.adViewGameOver)
        MobileAds.initialize(activity, context?.getString(R.string.banner_ad_unit_id))
        val adReq = AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build()
        adView?.loadAd(adReq)

        return view
    }

    override  fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}