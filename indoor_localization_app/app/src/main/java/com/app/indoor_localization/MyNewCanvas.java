package com.app.indoor_localization;

import android.view.View;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class MyNewCanvas extends View {
    Context cont;
    Paint paint;
    Path path;
    float x_coor;
    float y_coor;

    public MyNewCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        cont = context;
        paint = new Paint();
        path = new Path();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStrokeJoin(Paint.Join.ROUND);//maybe should delete
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(20f);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);

    }

    void drawMyPath(float x, float y, float x2, float y2){
        startTouch(x, y);
        invalidate();
        moveTouch(x2, y2);
        upTouch();
    }

    private void startTouch(float x, float y) {
        path.moveTo(x, y);

        //Cannot understand why do I need these
        //Maybe should delete later
        x_coor = x;
        y_coor = y;
    }

    private void moveTouch(float x, float y) {
        x_coor = x;
        y_coor = y;
    }

    private void upTouch() {
        path.lineTo(x_coor, y_coor);
    }
}
