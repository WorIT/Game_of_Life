package com.example.gameoflife.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gameoflife.R;
import com.example.gameoflife.threads.SurfView;

public class GameActivity extends AppCompatActivity {

    SurfView surfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        surfView = findViewById(R.id.surfView);
        surfView.context = this;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        surfView.surfaceDestroyed();
        finish();
    }

    private void imitateLoading() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
