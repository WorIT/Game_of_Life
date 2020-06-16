package com.example.gameoflife;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

class Field {
    int [][] field;
    int x_size = 30;
    int y_size = 30;
    int number_in_width = 30;
    int number_in_height= 30;
    int ALIVE = 1;
    int DEAD = 0;

    Field(int width, int height){

        number_in_width = width / x_size;
        number_in_height = height / y_size;
        System.out.println(number_in_width + " " + number_in_height);
        field = new int[number_in_width+2][number_in_height+2];
        field[number_in_width/2][number_in_height/2] = 1;
        field[number_in_width/2][number_in_height/2-1] = 1;
        field[number_in_width/2][number_in_height/2+1] = 1;
        field[number_in_width/2+1][number_in_height/2-1] = 1;
        field[number_in_width/2-1][number_in_height/2] = 1;

    }

    void draw(Canvas canvas){
        for (int i = 0; i < number_in_width; i++)
            for (int j = 0; j < number_in_height; j++)
                if(field[i][j] == ALIVE){
                    Paint p = new Paint();
                    p.setColor(Color.LTGRAY);
                    canvas.drawRect(x_size*(i-1), y_size*(j-1), x_size*i, y_size*j,p);
                }
        for (int i = 0; i < number_in_width; i++)
            for (int j = 0; j < number_in_height; j++) {
                canvas.drawLine(x_size*i, 0, x_size*i, y_size*number_in_height, new Paint());
                canvas.drawLine(0, y_size*j, x_size*number_in_width, y_size*j, new Paint());
            }
    }

    void move(){
        int [][] field1 = new int[number_in_width+2][number_in_height+2];
        for (int i = 1; i < number_in_width+1; i++) {
            for (int j = 1; j < number_in_height+1; j++) {
                int neidh = getNeighbors(i,j);
                if (field[i][j] == DEAD && neidh == 3)
                    field1[i][j] = ALIVE;
                else if(field[i][j] == ALIVE && (neidh == 3 || neidh == 2))
                    field1[i][j] = ALIVE;
                else
                    field1[i][j] = DEAD;
            }
        }
        field = field1;
    }
    int getNeighbors(int x, int y) {
        int sum = 0;
        sum += field[x-1][y-1];
        sum += field[x][y-1];
        sum += field[x-1][y];
        sum += field[x+1][y-1];
        sum += field[x-1][y+1];
        sum += field[x+1][y+1];
        sum += field[x][y+1];
        sum += field[x+1][y];
        if(sum == 3)
        System.out.println(x + " " + y);
        return sum;

    }
}
