package com.example.gameoflife.threads;


import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import com.example.gameoflife.classes.Field;


public class SThread extends Thread {

    private final int REDRAW_TIME = 10;
    private final int ANIMATION_TIME = 1_500;
    private Field field;

    private final SurfaceHolder mSurfaceHolder;

    private boolean mRunning;
    private long mStartTime;
    private long mPrevRedrawTime;

    private Paint mPaint;
    private ArgbEvaluator mArgbEvaluator;

    public SThread(SurfaceHolder holder, Context context) {
        mSurfaceHolder = holder;
        mRunning = false;
        DisplayMetrics display = context.getResources().getDisplayMetrics();
        int width = display.widthPixels;
        int height = display.heightPixels;
        field = new Field(width, height);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mArgbEvaluator = new ArgbEvaluator();
    }

    public void setRunning(boolean running) {
        mRunning = running;
        mPrevRedrawTime = getTime();
    }

    public long getTime() {
        return System.nanoTime() / 1_000_000;
    }

    @Override
    public void run() {
        Canvas canvas;
        mStartTime = getTime();
        int g = 0;
        while (mRunning) {
            long curTime = getTime();
            long elapsedTime = curTime - mPrevRedrawTime;
            if (elapsedTime < REDRAW_TIME)
                continue;
            canvas = null;
            if (mRunning) {
                try {
                    try {
                        canvas = mSurfaceHolder.lockCanvas();
                    }catch (Exception e){
                        break;
                    }

                    synchronized (mSurfaceHolder) {
                        draw(canvas);
                        g++;
                        if (g == 1) {
                            field.move();
                            g = 0;
                        }
                    }
                } catch (IllegalArgumentException ignore) {
                } finally {
                    if (canvas != null)
                        mSurfaceHolder.unlockCanvasAndPost(canvas);
                }

                mPrevRedrawTime = curTime;
            }
        }
    }

    private void draw(Canvas canvas) {
        if (canvas!=null){
            canvas.drawColor(Color.WHITE);
            field.draw(canvas);
        }

    }
}
