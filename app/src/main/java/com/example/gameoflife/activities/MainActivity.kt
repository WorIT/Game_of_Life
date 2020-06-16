package com.example.gameoflife.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.gameoflife.R
import com.example.gameoflife.fragments.RegisterFragment
import com.example.gameoflife.fragments.SignInFragment

class MainActivity : AppCompatActivity() {
    private lateinit var btn_main_play: Button
    private lateinit var btn_main_sign_in: Button
    private lateinit var btn_main_register: Button
    private lateinit var btn_main_articles: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        setListeners()

    }

   private fun init(){
       btn_main_play = findViewById(R.id.btn_main_play)
       btn_main_register = findViewById(R.id.btn_main_register)
       btn_main_sign_in = findViewById(R.id.btn_main_sign_in)
       btn_main_articles = findViewById(R.id.btn_main_arcticles_and_rules);
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