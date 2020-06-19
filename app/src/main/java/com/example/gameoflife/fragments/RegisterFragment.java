package com.example.gameoflife.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.gameoflife.R;
import com.example.gameoflife.classes.Firebase;
import com.example.gameoflife.classes.Pattern;
import com.example.gameoflife.classes.User;
import com.example.gameoflife.interfaces.FireCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


public class RegisterFragment extends Fragment implements FireCallback {
    private EditText etuserName;
    private EditText etemail;
    private EditText etpassword;
    private Firebase db;
    private Button send_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        init(view);








        return view;
    }

    private void init(View view){
        db = new Firebase(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance(), this);
        etuserName = view.findViewById(R.id.et_new_username);
        etemail = view.findViewById(R.id.et_new_email);
        etpassword = view.findViewById(R.id.et_new_password);
        send_email = view.findViewById(R.id.btn_enter_enter);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    private void setListeners(){
        send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///TODO проверки на "дурака"
                db.registerUser(etuserName.getText().toString(), etemail.getText().toString(),etpassword.getText().toString(), getActivity());

                //TODO снэкбар о подтверждении регистрации
            }
        });
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

    }

    @Override
    public void callResponseBool(boolean flag) {

    }
}