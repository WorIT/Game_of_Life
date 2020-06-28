package com.example.gameoflife.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.classes.DbPatterns
import com.example.gameoflife.fragments.MainFragment


open class MainActivity : AppCompatActivity()  {
    private val APP_PREFERENCES = "mysettings"
    private val APP_PREFERENCES_IS_FIRST = "first"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container)


        val mSettings: SharedPreferences = this.getSharedPreferences(APP_PREFERENCES,
            Context.MODE_PRIVATE
        )
        Thread(Runnable {
            if(mSettings.getBoolean(APP_PREFERENCES_IS_FIRST,true)){
                val db = DbPatterns(this)
                db.crdb()
            }

        }).start()

        startFragment(MainFragment())
    }

    private fun startFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.cl_container,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}


