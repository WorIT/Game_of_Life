package com.example.gameoflife.classes;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gameoflife.activities.MainActivity;
import com.example.gameoflife.interfaces.FireCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


public class Firebase {
    private FirebaseDatabase database;
    private FirebaseAuth databaseAuth;
    private static FireCallback callback;

    public Firebase(FirebaseDatabase database, FirebaseAuth databaseAuth, FireCallback callback) {
        this.database = database;
        this.databaseAuth = databaseAuth;
        registerCallBack(callback);
    }


    public void registerCallBack(FireCallback callback){
        this.callback = callback;
    }


    public void registerUser(final String name, final String email, String password, Context context) {
        //TODO проверка уникальности никнейма
        databaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User newUser = new User(name, email);
                    database.getReference("userList").child(newUser.getName()).setValue(newUser);
                    Objects.requireNonNull(databaseAuth.getCurrentUser()).sendEmailVerification();
                    Log.d("myActions","createUserOnComplete");

                }else Log.d("myACtions", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
            }
        });
    }

    public void addUserPattern(final String userName, Pattern pattern){
        /// TODO проверка уникальности паттерна
        database.getReference("userList").child(userName).child("patterns").setValue(pattern).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    //
                }else {
                    Log.d("myActions", Objects.requireNonNull(Objects.requireNonNull(task.getException()).getMessage()));
                }
            }
        });

        database.getReference("patterns").child(pattern.getTitlePattern()).setValue(pattern);
    }

    public void getPatternByTitle(final String title){
        database.getReference("patterns").child(title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pattern p = dataSnapshot.getValue(Pattern.class);
                callback.callPattern(p);  //// передача в активность данных
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void getAllPatterns(){
        database.getReference("patterns").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Pattern> patterns = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Pattern pattern = ds.getValue(Pattern.class);
                    patterns.add(pattern);
                }
                callback.callAllPatterns(patterns);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }





}
