package com.example.gameoflife.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import com.example.gameoflife.R
import com.example.gameoflife.classes.DbPatterns
import com.example.gameoflife.classes.Field
import com.example.gameoflife.fragments.MainFragment
import com.google.gson.Gson


open class MainActivity : AppCompatActivity()  {
    val APP_PREFERENCES = "mysettings"
    val APP_PREFERENCES_IS_FIRST = "first"
    var mSettings: SharedPreferences? = null


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
                val editor = mSettings.edit()
                editor.putBoolean(APP_PREFERENCES_IS_FIRST, false)
                editor.apply()
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


