package com.example.gameoflife.classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

import static java.util.Objects.*;


public class Firebase {
    private FirebaseDatabase database;
    private FirebaseAuth databaseAuth;
    private static FireCallback callback;

    public Firebase(FirebaseDatabase database, FirebaseAuth databaseAuth, FireCallback callback) {
        this.database = database;
        this.databaseAuth = databaseAuth;
        registerCallBack(callback);
    }


    public void registerCallBack(FireCallback callback) {
        this.callback = callback;
    }


    public void registerUser(final String name, final String email, final String password, Context context) {
        //TODO проверка уникальности никнейма
        databaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User newUser = new User(name, email);
                    database.getReference("userList").child(newUser.getName()).setValue(newUser);
                    requireNonNull(databaseAuth.getCurrentUser()).sendEmailVerification();
                    Log.d("myActions", "createUserOnComplete");

                } else
                    Log.d("myACtions", requireNonNull(requireNonNull(task.getException()).getMessage()));
            }
        });
    }

    public void addUserPattern(final String userName, Pattern pattern) {
        /// TODO проверка уникальности паттерна
        database.getReference("userList").child(userName).child("patterns").setValue(pattern).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    //
                } else {
                    Log.d("myActions", requireNonNull(requireNonNull(task.getException()).getMessage()));
                }
            }
        });

        database.getReference("patterns").child(pattern.getTitlePattern()).setValue(pattern);
    }

    public void getPatternByTitle(final String title) {
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

    public void getUserByEmail(final String email) {
        database.getReference().child("userList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             ///   for (DataSnapshot dataSnap : dataSnapshot.getChildren()) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    if (user.getEmail().equals(email)) {
                        callback.callUser(user);
                        return;
                    }
                }


                }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            //// передача в активность данных
        });
    }


    public void enterUser(String email, String password, Context context) {
        databaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    callback.callResponseBool(true);
                } else callback.callResponseBool(false);
            }
        });

    }
}


