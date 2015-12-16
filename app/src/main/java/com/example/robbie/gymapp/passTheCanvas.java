package com.example.robbie.gymapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Robbie on 14/12/2015.
 */
public class passTheCanvas extends View {
/*class which creates the canvas which user draws on. variable initialisation starts with integers
declared for width and height, then declaring a bitmap which is used to draw on. A Canvas is then declared
and a path too so we can track the user's interaction. a Context is used here along with a paint object to set the colour and size of the stroke
the user will use

 */
    public int width;
    public int height;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Context mContext;
    private Paint mPaint;
    private float mX, mY;
    private static final float TOLERANCE = 5;
    private int mBlack;
    Resources res;

    public passTheCanvas(Context c, AttributeSet atrs){
        super(c, atrs);
        mContext = c;

        // set up a new path
        mPath = new Path();
        mBlack = ContextCompat.getColor(c, R.color.myblack);
        //set up a new paint with required attributes
        res = getResources();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBlack);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(4f);
    }
    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight)
    {
        //sets up the canvas and sets the bitmap to the canvas
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        //ondraw, sets the path the user is taking and the paintobject to drawpath method
        super.onDraw(canvas);
        canvas.drawPath(mPath, mPaint);
    }

    //when ACTION_DOWN start touch according to x, y values
    private void startTouch(float x, float y)
    {
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }
    private void moveTouch(float x, float y)
    {
        // x and y are then transformed into path moves
        float dX = Math.abs(x - mX);
        float dY = Math.abs(y - mY);
        if(dX >= TOLERANCE || dY >= TOLERANCE)
        {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    public void clearCanvas() {
        //clears all activity on the canvas and returns it to a blank state
        mPath.reset();
        invalidate();
    }
    //when ACTION_UP stop touch
    private void upTouch()
    {
        mPath.lineTo(mX, mY);
    }
    //override the onTouchEvent
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //uses switch to determine which action is occurring and calls method based on that action.
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                moveTouch(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                upTouch();
                invalidate();
                break;

        }
        return true;

    }
}
