package com.example.gameoflife;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity{

    SurfView surfView;

    ImageButton play, move, edit;

    boolean isPlaying = true, isMoving = true, isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        surfView = findViewById(R.id.surfView);
        play = findViewById(R.id.play);
        move = findViewById(R.id.move);
        edit = findViewById(R.id.edit);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isMoving = !isMoving;
                isEditing = false;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying = !isPlaying;
                isEditing = false;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing = !isEditing;
                isPlaying = !isEditing;
                isMoving = !isEditing;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });

    }

    @Override
    protected void onStop() {
        super .onStop();
        surfView.mMyThread.interrupt();
    }
}
