package com.example.gameoflife.classes;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Pattern {

    private int widthPx;
    private int heightPx;
    private ArrayList<Point> fieldPattern;
    private String titlePattern;
    private String authorPattern;
    private int likesPattern;
    private String description;


    public Pattern(int widthPx, int heightPx, String titlePattern, String authorPattern, String description) {
        this.widthPx = widthPx;
        this.heightPx = heightPx;
       /// this.fieldPattern = fieldPattern;
        this.fieldPattern = new ArrayList<Point>();
        this.fieldPattern.add(new Point(1,4));
        this.titlePattern = titlePattern;
        this.authorPattern = authorPattern;
        this.likesPattern = 0;
        this.description = description;
    }

    public Pattern(){}


    public int getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(int widthPx) {
        this.widthPx = widthPx;
    }

    public int getHeightPx() {
        return heightPx;
    }

    public void setHeightPx(int heightPx) {
        this.heightPx = heightPx;
    }


    public String getTitlePattern() {
        return titlePattern;
    }

    public void setTitlePattern(String titlePattern) {
        this.titlePattern = titlePattern;
    }

    public String getAuthorPattern() {
        return authorPattern;
    }

    public void setAuthorPattern(String authorPattern) {
        this.authorPattern = authorPattern;
    }

    public int getLikesPattern() {
        return likesPattern;
    }

    public void setLikesPattern(int likesPattern) {
        this.likesPattern = likesPattern;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
