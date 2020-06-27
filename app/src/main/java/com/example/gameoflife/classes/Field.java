package com.example.gameoflife.classes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import androidx.preference.PreferenceManager;

public class Field {
    int [][] field0;
    int [][] field;
    int x_size = 30;
    int y_size = 30;
    int x_size_with_coff = 30;
    int y_size_with_coff = 30;
    int number_in_width = 30;
    int number_in_height= 30;
    int ALIVE = 1;
    int DEAD = 0;
    public int height = 0;
    public int width = 0;
    public double fix_coefficient = 2;
    public double coefficient = 1;
    public Point shift = new Point(0,0);
    public Point fix_shift = new Point(0,0);
    public Point p = new Point(0,0);
    public int spw = 0;
    public int fix_spw = 0;
    public int sph = 0;
    public int fix_sph = 0;

    public Field(Point point){
        number_in_width = point.getX() / x_size;
        number_in_height = point.getY() / y_size;
        field = new int[number_in_width+2][number_in_height+2];
        field0 = new int[number_in_width+2][number_in_height+2];
        for (int i = 0; i < number_in_width+2; i++)
            for (int j = 0; j < number_in_height+2; j++)
                field0[i][j] = field[i][j];
    }

    public Field() {

    }


    public void reZero(){
        for (int i = 0; i < number_in_width+2; i++)
            for (int j = 0; j < number_in_height+2; j++)
                field[i][j] = field0[i][j];
    }

    public void draw(Canvas canvas){
        height = canvas.getHeight();
        width = canvas.getWidth();
        x_size_with_coff = (int) (x_size * coefficient * fix_coefficient);
        y_size_with_coff = (int) (y_size * coefficient * fix_coefficient);
        int x_shift = shift.getX() + fix_shift.getX() - (width-x_size_with_coff*(number_in_width-1)) / 2 - spw - fix_spw;
        int y_shift = shift.getY() + fix_shift.getY() - (height-y_size_with_coff*(number_in_height-1)) / 2 - sph - fix_sph;
        int countLifes = 0;
        for (int i = 1; i < number_in_width; i++)
            for (int j = 1; j < number_in_height; j++)
                if(field[i][j] == ALIVE){
                    countLifes+=1;
                    Paint p = new Paint();
                    p.setColor(Color.GREEN);
                    canvas.drawRect(x_size_with_coff*(i-1) - x_shift , y_size_with_coff*(j-1) - y_shift, x_size_with_coff*i - x_shift, y_size_with_coff*j - y_shift,p);
                }

        Paint pline = new Paint();
        pline.setColor(Color.WHITE);
        for (int i = 0; i < number_in_width; i++)
            for (int j = 0; j < number_in_height; j++) {
                canvas.drawLine(x_size_with_coff*i - x_shift, -y_shift, x_size_with_coff*i - x_shift, y_size_with_coff*(number_in_height-1) -y_shift, pline);
                canvas.drawLine( -x_shift, y_size_with_coff*j - y_shift , x_size_with_coff*(number_in_width-1) - x_shift, y_size_with_coff*j - y_shift , pline);
            }
    }

    public void move(){
        int [][] field1 = new int[number_in_width+2][number_in_height+2];
        for (int i = 1; i < number_in_width; i++) {
            for (int j = 1; j < number_in_height; j++) {
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
        return sum;

    }

    public void touch(Point point){
        int x = (point.getX() + (shift.getX() + fix_shift.getX() - (width-x_size_with_coff*(number_in_width-1)) / 2 - spw - fix_spw))/ x_size_with_coff;
        int y = (point.getY() + (shift.getY() + fix_shift.getY() - (height-y_size_with_coff*(number_in_height-1)) / 2 - sph - fix_sph))/ y_size_with_coff;
        x = (x > number_in_width - 1? -1 : x);
        y = (y > number_in_width - 1? -1 : y);
        field0[x+1][y+1] = (field0[x+1][y+1] == ALIVE ? DEAD : ALIVE);
        field[x+1][y+1] = (field[x+1][y+1] == ALIVE ? DEAD : ALIVE);
    }
}
