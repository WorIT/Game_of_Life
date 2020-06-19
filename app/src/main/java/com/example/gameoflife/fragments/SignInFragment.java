package com.example.gameoflife.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gameoflife.R;
import com.example.gameoflife.classes.Firebase;
import com.example.gameoflife.classes.Pattern;
import com.example.gameoflife.classes.User;
import com.example.gameoflife.interfaces.FireCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;


public class SignInFragment extends Fragment implements FireCallback{
    private EditText etEmail;
    private EditText etpassword;
    private Firebase db;
    private User MyUser;
    private Button btn_enter;
    private boolean isEnter = false;
    static SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_EMAIL = "Email";
    public static final String APP_PREFERENCES_PASSWORD = "Password";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sign_in, container, false);
        init(view);
        setListeners();

        return view;
    }

    private void init(View view){
        mSettings = Objects.requireNonNull(getActivity()).getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        etEmail = view.findViewById(R.id.et_enter_email);
        etpassword = view.findViewById(R.id.et_enter_password);
        btn_enter = view.findViewById(R.id.btn_enter_enter);
        db = new Firebase(FirebaseDatabase.getInstance(), FirebaseAuth.getInstance(), this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setListeners(){
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///TODO проверки на "дурака"
            db.enterUser(etEmail.getText().toString(), etpassword.getText().toString(),getActivity()); // идет в callResponseBool
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
        MyUser = user;
        Bundle bundle = new Bundle();
        bundle.putString("emailUser", MyUser.getEmail());
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        startFragment(fragment);
    }

    @Override
    public void callResponseBool(boolean flag) {
        isEnter = flag;
        if (isEnter){
            //TODO сделать checkbox


            addToPreferences();

            Toast.makeText(getActivity(),"enter",Toast.LENGTH_SHORT).show();
            isEnter = false;
            Bundle bundle = new Bundle();
            bundle.putString("emailUser", etEmail.getText().toString());
            MainFragment fragment = new MainFragment();
            fragment.setArguments(bundle);
            startFragment(fragment);
        }else Toast.makeText(getActivity(),"NOenter",Toast.LENGTH_SHORT).show();
    }


    private void startFragment(Fragment fragment) {
        FragmentTransaction ft = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.containerMain, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    private void addToPreferences(){
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_EMAIL, etEmail.getText().toString());
        editor.apply();
        editor.putString(APP_PREFERENCES_PASSWORD, etpassword.getText().toString());
        editor.apply();
    }



}