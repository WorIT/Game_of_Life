package com.example.gameoflife.classes;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private int likes;
    private ArrayList<Pattern> patterns;
    private int countPatterns;


    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.likes = 0;
        this.patterns = new ArrayList<>();
        this.countPatterns = 0;
    }

    public  User(){}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }

    public void setPatterns(ArrayList<Pattern> patterns) {
        this.patterns = patterns;
    }

    public int getCountPatterns() {
        return countPatterns;
    }

    public void setCountPatterns(int countPatterns) {
        this.countPatterns = countPatterns;
    }
}
