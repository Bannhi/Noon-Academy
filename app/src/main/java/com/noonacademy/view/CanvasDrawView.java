package com.noonacademy.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.noonacademy.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by bannhi on 26/2/18.
 */

public class CanvasDrawView extends View {

    //drawing path
    private Path drawPath;

    Context context;

    private int drawColor = Color.WHITE;
    //defines what to draw
    private Paint canvasPaint;

    //defines how to draw
    private Paint drawPaint;

    //canvas - holding pen, holds your drawings
    //and transfers them to the view
    private Canvas drawCanvas;
    private float mX, mY;

    //canvas bitmap
    private Bitmap canvasBitmap;

    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Integer> colors = new ArrayList<Integer>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();

    public CanvasDrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setUpDrawing();

    }
    public CanvasDrawView(Context context) {
        super(context);

    }

    public void setUpDrawing() {
        System.out.println("SET UP IS CALLED");
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5f);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasBitmap = Bitmap.createBitmap(1000/*width*/, 1000/*height*/, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        System.out.println("ON DRAW IS CALLEd");
        for (int i = 0; i < paths.size(); i++) {
            Path p = paths.get(i);
            drawPaint.setColor(colors.get(i));
            canvas.drawPath(p, drawPaint);
        }
        canvas.drawPath(drawPath, drawPaint);
    }

    public void redo() {
        if (paths != null && paths.size() > 0)
            for (Path p : paths) {
                p.reset();
                colors.clear();
            }
        invalidate();
    }


    public void undo() {
        if (paths.size() > 0) {
            undonePaths.add(paths.remove(paths.size() - 1));
            colors.remove(paths.size() - 1);
            invalidate();
        }

    }

    public void changeColor(int color) {
        drawColor = color;

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        System.out.println("DISPATCH IS CALLEd");
        float touchX = event.getX();
        float touchY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(touchX, touchY);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
            default:
                return false;
        }
        return true;

    }

    private void touch_start(float x, float y) {
        System.out.println("TOUCH START IS CALLEd");
        undonePaths.clear();
        drawPath.reset();
        drawPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        System.out.println("TOUCH MOVE IS CALLEd");
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= Constants.TOUCH_TOLERANCE || dy >= Constants.TOUCH_TOLERANCE) {
            drawPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        System.out.println("TOUCH UP IS CALLEd");
        drawPath.lineTo(mX, mY);
        drawCanvas.drawPath(drawPath, drawPaint);
        paths.add(drawPath);
        colors.add(drawColor);
        drawPath = new Path();

    }

    public void saveImage(){
        String root = Environment.getExternalStorageDirectory().toString();
        File dir =new File(root + "/saved_images");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File output = new File(dir, System.currentTimeMillis()+".jpg");
        OutputStream os = null;

        try {
            os = new FileOutputStream(output);
            canvasBitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

            MediaScannerConnection.scanFile(context, new String[] { output.toString() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.d("appname", "Image is saved in gallery and gallery is refreshed.");

                        }
                    }
            );
        } catch (Exception e) {
        }
    }


}
