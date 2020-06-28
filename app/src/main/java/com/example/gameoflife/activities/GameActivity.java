package com.example.gameoflife.activities;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.gameoflife.classes.DbPatterns;
import com.example.gameoflife.classes.Field;
import com.example.gameoflife.threads.SurfView;
import com.example.gameoflife.R;
import com.google.gson.Gson;


import static java.lang.Thread.sleep;

public class GameActivity extends AppCompatActivity {

    private final int NEWMOVE = 0;
    private final int COUNTLIFES = 1;
    private final int GAMEOVER = 2;
    private final int EMPTYPATTERN = 3;
    private final int CONTAINSPATTERN = 4;


    private boolean flag = false;

    SurfView surfView;
    ImageButton play, move, edit;
    DbPatterns db;
    TextView tv_countmoves, tv_countlifes;

    EditText title;
    Button add;

    int moves = 0;

    boolean isPlaying = false, isMoving = true, isEditing = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        play = findViewById(R.id.playmake);
        play.setBackgroundResource(R.drawable.ic_baseline_pause_24);
        move = findViewById(R.id.movem);
        move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
        edit = findViewById(R.id.editm);
        edit.setBackgroundResource(R.drawable.ic_baseline_build_24);


        db = new DbPatterns(this);

        title = findViewById(R.id.et_make_title);
        add = findViewById(R.id.buttonmakeadd);
        tv_countlifes = findViewById(R.id.tv_game_countlifes);
        tv_countmoves = findViewById(R.id.tv_game_countmove);


    }



    @Override
    protected void onStart() {
        super.onStart();
        surfView = findViewById(R.id.surfView);


        new Thread(new Runnable() {
            public void run() {
                try {
                    sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int x = 0;
                try{
                    x = db.getqq().length();
                }catch (Exception e){
                    x = 0;
                }

                surfView.mMyThread.setHandler(handler);
                    if (x > 0) {
                        surfView.mMyThread.setRunning(false);
                        Field fields = new Gson().fromJson(db.select(db.getqq()).getField(), Field.class);
                        surfView.mMyThread.setField(fields);
                        if (!db.getqq().equals("Empty Field")){
                            surfView.mMyThread.setRunning(true);
                            surfView.setBool(true,true,false);
                            handler.sendEmptyMessage(CONTAINSPATTERN);
                        }else{
                            handler.sendEmptyMessage(EMPTYPATTERN);
                        }



                    }else {
                        handler.sendEmptyMessage(EMPTYPATTERN);
                    }


            }
        }).start();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (title.getText().toString().length() > 0){
                    db.insert(title.getText().toString(),new Gson().toJson(getField()),"me");
                    Toast.makeText(getApplicationContext(),"Pattern add to your patterns",Toast.LENGTH_SHORT).show();
                    String emptyField = getString(R.string.empty_field);
                    Field fields = new Gson().fromJson(emptyField,Field.class);
                    surfView.mMyThread.setField(fields);
                    isPlaying = false;
                    isMoving = false;
                    isEditing = true;
                    play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_check_24);
                    move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
                }else {
                    Toast.makeText(getApplicationContext(),"Check title of your new pattern",Toast.LENGTH_SHORT).show();
                }
            }
        });

        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMoving) {
                    isEditing = true;
                    v.setBackgroundResource(R.drawable.ic_baseline_block_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_check_24);
                } else {
                    isEditing = false;
                    v.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_build_24);
                }
                isMoving = !isMoving;;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying){
                    v.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    add.setEnabled(true);
                    title.setHint("title");
                }
                else {
                    v.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    edit.setBackgroundResource(R.drawable.ic_baseline_build_24);
                    add.setEnabled(false);
                    title.setHint("add during pause");
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
                    move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
                } else {
                    v.setBackgroundResource(R.drawable.ic_baseline_check_24);
                    play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    move.setBackgroundResource(R.drawable.ic_baseline_block_24);
                }
                isEditing = !isEditing;
                isPlaying = !isEditing;
                isMoving = !isEditing;
                surfView.setBool(isPlaying, isMoving, isEditing);
            }
        });
    }

    void empty(){
        surfView.mMyThread.setRunning(false);
        Field fields = new Gson().fromJson(getApplicationContext().getString(R.string.empty_field), Field.class);
        surfView.mMyThread.setField(fields);
        edit.setBackgroundResource(R.drawable.ic_baseline_check_24);
        play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
        move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
        surfView.mMyThread.setRunning(true);
        isPlaying = false;
        isMoving = false;
        isEditing = true;
        surfView.setBool(false,false,true);
    }

    public Field getField(){
        return surfView.getField();
    }

    @Override
    protected void onPause() {
        isPlaying = false;
        super.onPause();
    }

    @Override
    protected void onRestart() {
        isPlaying = true;
        super.onRestart();
    }

    @Override
    protected void onStop() {
        surfView.mMyThread.interrupt();
        super .onStop();
    }

    @Override
    public void onBackPressed() {
        surfView.mMyThread.interrupt();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }


    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){

               case NEWMOVE:{
                   moves +=1;
                   tv_countmoves.setText(moves + " moves");
                   flag = true;
                   break;
               }



               case COUNTLIFES:{
                   tv_countlifes.setText(surfView.mMyThread.countlifes + " lifes");
                   flag = true;
                   break;
               }


               case CONTAINSPATTERN:{
                   isPlaying = true;
                   add.setEnabled(false);
                   title.setHint("add during pause");
                   break;
               }


               case GAMEOVER:{

                   if (isPlaying && flag){
                       surfView.setBool(false,false,false);
                       move.setBackgroundResource(R.drawable.ic_baseline_pan_tool_24);
                       edit.setBackgroundResource(R.drawable.ic_baseline_build_24);
                       play.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                     //  startFragment(new GameOverFragment());
                   }
                   break;

               }


               case EMPTYPATTERN:{
                   empty();
                   break;
               }





               default:
                   break;
           }
        }
    };

    private void startFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.constain_game_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
