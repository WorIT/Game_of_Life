package com.example.gameoflife.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.fragments.MainFragment

open class MainActivity : AppCompatActivity()  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_main)

        startFragment(MainFragment())



    }


    private fun startFragment(fragment: Fragment){
        val FragmentTransaction = supportFragmentManager.beginTransaction()
        FragmentTransaction.replace(R.id.containerMain,fragment)
        FragmentTransaction.addToBackStack(null)
        FragmentTransaction.commit()

    }



}