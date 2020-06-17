package com.example.gameoflife.classes;

public class Point {
    private int x;
    private int y;
    public boolean flag;
    private final boolean ALIVE = true;
    private final boolean DEAD = false;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(int x, int y, boolean flag) {
        this.x = x;
        this.y = y;
        this.flag = flag;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x &&
                y == point.y;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
