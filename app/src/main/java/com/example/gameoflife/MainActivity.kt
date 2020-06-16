package com.example.gameoflife

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btn_play: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        setListeners()

    }

    fun init(){
        btn_play = findViewById(R.id.btn_main_play)
    }

    fun setListeners(){
        btn_play.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this,SplashActivity::class.java))
        })
    }
}