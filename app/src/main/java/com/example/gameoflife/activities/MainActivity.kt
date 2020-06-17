package com.example.gameoflife.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.classes.Firebase
import com.example.gameoflife.classes.Pattern
import com.example.gameoflife.fragments.RegisterFragment
import com.example.gameoflife.fragments.SignInFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask


class MainActivity : AppCompatActivity() {
    private lateinit var btn_main_play: Button
    private lateinit var btn_main_sign_in: Button
    private lateinit var btn_main_register: Button
    private lateinit var btn_main_articles: Button
    private lateinit var db : Firebase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseDatabase.getInstance()
        init()

        setListeners()


    }

   private fun init(){
       btn_main_play = findViewById(R.id.btn_main_play)
       btn_main_register = findViewById(R.id.btn_main_register)
       btn_main_sign_in = findViewById(R.id.btn_main_sign_in)
       btn_main_articles = findViewById(R.id.btn_main_arcticles_and_rules);
       db = Firebase(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance())
     ///  db.addUserPattern("worit", Pattern(4,4,"haip","worit","busterim"))
       ///db.registerUser("worit","nechaev.world@gmail.com", "123456789",this)



   }

    private fun setListeners(){


      btn_main_play.setOnClickListener(View.OnClickListener {
          startActivity(Intent(this, GameActivity::class.java))
       })


      btn_main_sign_in.setOnClickListener(View.OnClickListener {
          startFragment(SignInFragment())
        })


      btn_main_register.setOnClickListener(View.OnClickListener {
          startFragment(RegisterFragment())
        })


       btn_main_articles.setOnClickListener(View.OnClickListener {
           startActivity(Intent(this, ArticlesActivity::class.java))
       })
        }



    private fun startFragment(fragment: Fragment){
        val FragmentTransaction = supportFragmentManager.beginTransaction()
        FragmentTransaction.replace(R.id.containerMain,fragment)
        FragmentTransaction.addToBackStack(null)
        FragmentTransaction.commit()

    }

}