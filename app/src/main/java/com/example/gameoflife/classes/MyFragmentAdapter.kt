package com.example.gameoflife.classes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.gameoflife.fragments.RegisterFragment
import com.example.gameoflife.fragments.SignInFragment


class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                SignInFragment()
            }
            else -> RegisterFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }
}