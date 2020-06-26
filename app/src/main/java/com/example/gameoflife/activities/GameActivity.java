package com.example.gameoflife.activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gameoflife.classes.DbPatterns;
import com.example.gameoflife.classes.Field;
import com.example.gameoflife.threads.SurfView;
import com.example.gameoflife.R;
import com.google.gson.Gson;


import static java.lang.Thread.sleep;

public class GameActivity extends AppCompatActivity {

    SurfView surfView;
    ImageButton play, move, edit;
    DbPatterns db;

    boolean isPlaying = true, isMoving = true, isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);




        play = findViewById(R.id.playm);
        play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        move = findViewById(R.id.movem);
        move.setBackgroundResource(R.drawable.ic_baseline_block_24);
        edit = findViewById(R.id.editm);
        edit.setBackgroundResource(R.drawable.ic_baseline_build_24);



        db = new DbPatterns(this);


    }


    @Override
    protected void onStart() {
        super.onStart();
        surfView = findViewById(R.id.surfView);

        final Handler handler = new Handler() {
            @SuppressLint("HandlerLeak")
            @Override
            public void handleMessage(Message msg) {
                empty();
            }
        };

        new Thread(new Runnable() {
            public void run() {
                try {
                    sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                bundle.putString("Key", "empty");
                msg.setData(bundle);
                if (db.getqq().length() > 1) {
                    surfView.mMyThread.setRunning(false);

                    Field fields = new Gson().fromJson(db.select(db.getqq()).getField(), Field.class);
                    Log.d("aarr", fields.toString());
                    surfView.mMyThread.setField(fields);
                    surfView.mMyThread.setRunning(true);

                }else handler.sendMessage(msg);
            }
        }).start();

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    v.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_check_24);
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_block_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_build_24);
                }
                isMoving = !isMoving;
                isEditing = false;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) v.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                else {
                    v.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_build_24);
                }
                isPlaying = !isPlaying;
                isEditing = false;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    v.setBackgroundResource(R.drawable.ic_baseline_build_24);
                    play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    move.setBackgroundResource(R.drawable.ic_baseline_block_24);
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_check_24);
                    play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
                }
                isEditing = !isEditing;
                isPlaying = !isEditing;
                isMoving = !isEditing;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
    }

    void empty(){
        surfView.setBool(false,false,true);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        move = findViewById(R.id.movem);
        move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
        edit = findViewById(R.id.editm);
        edit.setBackgroundResource(R.drawable.ic_baseline_check_24);
    }


    @Override
    protected void onStop() {
        surfView.mMyThread.interrupt();
        super .onStop();
    }

    @Override
    public void onBackPressed() {
        surfView.mMyThread.interrupt();
        db.close();
        finish();
        super.onBackPressed();
    }
}
