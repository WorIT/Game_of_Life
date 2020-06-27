package com.example.gameoflife.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gameoflife.classes.Field;
import com.example.gameoflife.threads.SurfView;
import com.example.gameoflife.R;
public class GameActivity extends AppCompatActivity {

    SurfView surfView;

    ImageButton play, move, edit;

    boolean isPlaying = true, isMoving = true, isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        surfView = findViewById(R.id.surfView);
        play = findViewById(R.id.play);
        move = findViewById(R.id.move);
        edit = findViewById(R.id.edit);

    }


    @Override
    protected void onStart() {
        super.onStart();
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

    public Field getField(){
        return surfView.getField();
    }

    @Override
    protected void onStop() {
        super .onStop();
        surfView.mMyThread.interrupt();
    }
}
