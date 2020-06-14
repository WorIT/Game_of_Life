package com.example.gameoflife;


import android.graphics.Color;
import android.widget.ImageView;

import java.util.ArrayList;

public class FieldLifes {
   private int width;
   private int height;
   private final int X = 1011011011;
   private final int Y = 1021021021;
   private ImageView [][] field;
   private ArrayList<Point> lifes;
   private ArrayList<Point> change;


   public FieldLifes(int width,int height){
       this.width = width;
       this.height = height;
       field = new ImageView[100][99];
       lifes = new ArrayList<>();
       change = new ArrayList<>();
   }



   private boolean check(int x,int y){  //// x вниз(
       return (x >=0 && x<= height && y>= 0 && y <= width);
   }

    public ArrayList<Point> getLifes() {
        return lifes;
    }

    private void updateChange(){
       change.clear();

       for (int i = 0; i < lifes.size(); i++) {

           change.add(new Point(lifes.get(i).getX(),lifes.get(i).getY()));
           if(check(lifes.get(i).getX() + 1,lifes.get(i).getY() + 1)) change.add(new Point(lifes.get(i).getX() + 1,lifes.get(i).getY() + 1));
           if(check(lifes.get(i).getX() + 1,lifes.get(i).getY())) change.add(new Point(lifes.get(i).getX() + 1,lifes.get(i).getY()));
           if(check(lifes.get(i).getX() + 1,lifes.get(i).getY() - 1)) change.add(new Point(lifes.get(i).getX() + 1,lifes.get(i).getY() - 1));
           if(check(lifes.get(i).getX() ,lifes.get(i).getY() + 1)) change.add(new Point(lifes.get(i).getX(),lifes.get(i).getY() + 1));
           if(check(lifes.get(i).getX(),lifes.get(i).getY() - 1)) change.add(new Point(lifes.get(i).getX(),lifes.get(i).getY() - 1));
           if(check(lifes.get(i).getX() - 1,lifes.get(i).getY() + 1)) change.add(new Point(lifes.get(i).getX() - 1,lifes.get(i).getY() + 1));
           if(check(lifes.get(i).getX() - 1,lifes.get(i).getY())) change.add(new Point(lifes.get(i).getX() - 1,lifes.get(i).getY()));
           if(check(lifes.get(i).getX() - 1,lifes.get(i).getY() - 1)) change.add(new Point(lifes.get(i).getX()- 1,lifes.get(i).getY() - 1));

       }
   }

   public ImageView[][] getImageField(){
       return field;
   }

   private boolean isLife(int x,int y){
       if (field[x][y].getTag().equals("life")){
           return true;
       }else return false;
   }



    private void nextIfLife(Point point){
        int x = point.getX();
        int y = point.getY();
        int countLifes = 0;

        if (isLife(x,y + 1)) countLifes++;
        if (isLife(x,y - 1)) countLifes++;
        if (isLife(x + 1,y + 1)) countLifes++;
        if (isLife(x + 1, y)) countLifes++;
        if (isLife(x + 1,y - 1)) countLifes++;
        if (isLife(x - 1,y + 1)) countLifes++;
        if (isLife(x - 1, y)) countLifes++;
        if (isLife( x - 1,y - 1)) countLifes++;

        if (!(countLifes == 2 || countLifes == 3)){
           field[x][y].setTag("plain");
           field[x][y].setBackgroundColor(Color.WHITE);
           field[x][y].setBackgroundResource(R.drawable.node);
           lifes.remove(point);
        }


    }

    private void nextIfPlain(Point point){
        int countLifes = 0;
        int x = point.getX();
        int y = point.getY();
        if (isLife(x,y + 1)) countLifes++;
        if (isLife(x,y - 1)) countLifes++;
        if (isLife(x + 1,y + 1)) countLifes++;
        if (isLife(x + 1, y)) countLifes++;
        if (isLife(x + 1,y - 1)) countLifes++;
        if (isLife(x - 1,y + 1)) countLifes++;
        if (isLife(x - 1, y)) countLifes++;
        if (isLife( x - 1,y - 1)) countLifes++;

        if (countLifes == 3){
            field[x][y].setTag("life");
            field[x][y].setBackgroundColor(Color.GREEN);
            lifes.add(point);
        }


    }

    private void change(){
        for (int i = 0; i < change.size(); i++) {
            if (field[change.get(i).getX()][change.get(i).getY()].getTag().equals("plain")){
                nextIfPlain(change.get(i));
            }else nextIfLife(change.get(i));
        }
    }

    public void next(){
       updateChange();
       change();
    }




}
