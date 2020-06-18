package com.example.gameoflife.interfaces;

import com.example.gameoflife.classes.Pattern;
import com.example.gameoflife.classes.Point;
import com.example.gameoflife.classes.User;

import java.util.ArrayList;


public interface FireCallback {
    void callString(String string);
    void callInt(int number);
    void callPattern(Pattern pattern);
    void callAllPatterns(ArrayList<Pattern> patterns);
    void callUser(User user);
    void callResponseBool(boolean flag);

}
