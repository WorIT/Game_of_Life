package com.example.gameoflife.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.gameoflife.R;
import com.example.gameoflife.classes.Firebase;
import com.example.gameoflife.classes.Pattern;
import com.example.gameoflife.classes.User;
import com.example.gameoflife.interfaces.FireCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity implements FireCallback {
    private String userEmail;
    private User mUser;
    private Firebase db;
    private TextView tvuserName;
    private TextView tvuserEmail;
    private TextView tvuserLikes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        tvuserName = findViewById(R.id.tv_user_nameUser);
        tvuserEmail = findViewById(R.id.tv_user_email);
        tvuserLikes = findViewById(R.id.tv_user_likes);
        userEmail = getIntent().getStringExtra("userEmail");
        db = new Firebase(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance(), this);
        db.getUserByEmail(userEmail);
    }

    @Override
    public void callString(String string) {

    }

    @Override
    public void callInt(int number) {

    }

    @Override
    public void callPattern(Pattern pattern) {

    }

    @Override
    public void callAllPatterns(ArrayList<Pattern> patterns) {

    }

    @Override
    public void callUser(User user) {
        mUser = user;
        tvuserName.setText(mUser.getName());
        tvuserEmail.setText(mUser.getEmail());
        tvuserLikes.setText(mUser.getLikes());


    }

    @Override
    public void callResponseBool(boolean flag) {


    }
}