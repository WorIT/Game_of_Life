package nechaev.gameoflife.gameoflife.threads;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.view.SurfaceHolder;

import nechaev.gameoflife.gameoflife.classes.Field;
import nechaev.gameoflife.gameoflife.classes.Point;


public class SThread extends Thread {


    private final int NEWMOVE = 0;
    private final int COUNTLIFES = 1;
    private final int GAMEOVER = 2;
    private final int EMPTYPATTERN = 3;
    private final int CONTAINSPATTERN = 4;


    private int REDRAW_TIME = 10;
    private Field field;
    boolean isFixed = false;
    private final SurfaceHolder mSurfaceHolder;
    boolean isPlaying = true, isMoving = true, isEditing = false;
    private boolean mRunning;
    private long mPrevRedrawTime;
    public int countlifes = 0;

    private Paint mPaint;

    public Handler handler;


    public SThread(SurfaceHolder holder,Field field) {
        mSurfaceHolder = holder;
        mRunning = false;
        if (field!= null){
            this.field = field;

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }
        else {
            this.field = new Field(new Point(1000, 1000));

            mPaint = new Paint();
            mPaint.setAntiAlias(true);
            mPaint.setStyle(Paint.Style.FILL);
        }

    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public void setCountlifes(int countlifes) {
        this.countlifes = countlifes;
        handler.sendEmptyMessage(COUNTLIFES);
    }

    public void setREDRAW_TIME(int x){
        this.REDRAW_TIME = x;
    }

    public void setRunning(boolean running) {
        mRunning = running;
        mPrevRedrawTime = getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1_000_000;
    }

    public void setField(Field field){this.field = field;}
    public void setZeroField(Field field){this.field.setZeroField(field.getZeroField());}


    public Field getFied(){
        return field;
    }

    @Override
    public void run() {
        Canvas canvas;
        int g = 0;
        while (mRunning && !isInterrupted()) {
            long curTime = getTime();
            long elapsedTime = curTime - mPrevRedrawTime;
            if (elapsedTime < REDRAW_TIME)
                continue;
            canvas = null;
            try {
                canvas = mSurfaceHolder.lockCanvas();
                synchronized (mSurfaceHolder) {
                    draw(canvas);
                    g++;
                    if (g >= REDRAW_TIME && isPlaying) {
                        field.move();
                        handler.sendEmptyMessage(NEWMOVE);
                        g = 0;
                    }
                }
            } catch (Exception e) {
            } finally {
                if (canvas != null)
                    try {
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                    }catch (Exception e){}

            }

            mPrevRedrawTime = curTime;
        }
    }

    public void setShift(Point p){
        field.shift = p;
    }

    void fixShift() {
        field.fix_shift = new Point(field.shift.getX() + field.fix_shift.getX(), field.shift.getY() + field.fix_shift.getY());
        isFixed = true;
    }

    void setBool(boolean isPlaying, boolean isMoving, boolean isEditing) {
        System.out.println(isPlaying);
        this.isEditing = isEditing;
        this.isPlaying = isPlaying;
        this.isMoving = isMoving;
        if(isEditing)
            field.reZero();
    }

    void setCoff(double coff, Point p) {
        field.coefficient = coff;
        field.p = p;
        field.spw = (int) ((field.width / 2 - p.getX()) * (coff - 1)) / 2;
        field.sph = (int) ((field.height / 2 - p.getY()) * (coff - 1)) / 2;
    }

    void fixCoff() {
        field.fix_coefficient = field.coefficient * field.fix_coefficient;
        field.coefficient = 1;
        field.fix_spw = field.fix_spw + field.spw;
        field.fix_sph = field.fix_sph + field.sph;
        field.spw = 0;
        field.sph = 0;
    }
    void addordel(Point p){
        field.touch(p);
    }

    private void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);
        if (isFixed) {
            field.shift = new Point(0, 0);
            isFixed = false;
        }
        field.draw(canvas, this);
    }

}