package com.example.gameoflife.threads;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.gameoflife.threads.SThread;

public class SurfView extends SurfaceView implements SurfaceHolder.Callback {

    private SThread mMyThread;
    public Context context;

    public SurfView(Context context) {
        super(context);
        this.context = context;
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mMyThread = new SThread(getHolder(), context);
        mMyThread.setRunning(true);
        mMyThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public SurfView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public SurfView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                System.out.println("down");
                break;
            case MotionEvent.ACTION_UP:
                System.out.println("up");
                break;
        }
        return true;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = false;
        mMyThread.setRunning(false);

      //  while(retry) {
         ///   try {
                ///mMyThread.join();
         ///       retry = false;
         //   }
         ///   catch (InterruptedException ignored) {
          ///  }
        }
}