package com.example.gameoflife.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.classes.DbPatterns
import com.example.gameoflife.classes.Pattern
import com.example.gameoflife.fragments.MainFragment
import com.google.gson.Gson





open class MainActivity : AppCompatActivity()  {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)
        var db = DbPatterns(this)


        startFragment(MainFragment())




    }


    private fun startFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.cl_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }



}


