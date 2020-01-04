package com.example.multitouchdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import yuku.ambilwarna.AmbilWarnaDialog;


public class MultitacticalDrawView extends View implements
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    // Generador de numeros aleatorios
    Random rdm = new Random();
    // Almacena las propiedades gráficas de dibujo de la línea
    Paint paint = new Paint();
    // Almacenan la posición inicial y final de la línea
    float prevX, prevY, newX, newY;
    // HashMap para guardar los paths
    private HashMap<Integer, Path> mapPaths = new HashMap<Integer, Path>();
    // Gesture detector
    private GestureDetector mDetector;
    // ArrayList with all lines
    private ArrayList<Path> listPaths = new ArrayList<>();
    // ArrayList with all colors
    private ArrayList<Integer> listColors = new ArrayList<>();
    // ArrayList with all stroke widths
    private ArrayList<Float> listStrokes = new ArrayList<>();
    // Paint color
    private int color = Color.BLACK;
    // Stroke width
    private float stroke = 4f;
    // Application context
    private Context context;

    public MultitacticalDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        // Initialize
        mDetector = new GestureDetector(context,this);
        mDetector.setOnDoubleTapListener(this);

        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(stroke);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw all paths
        for (int i = 0; i<listPaths.size(); i++){
            Path p = listPaths.get(i);
            int c = listColors.get(i);
            float s = listStrokes.get(i);
            paint.setColor(c);
            paint.setStrokeWidth(s);
            if(p != null){
                canvas.drawPath(p, paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);

        int index = event.getActionIndex();
        int activePointerId = event.getPointerId(index);

        switch (event.getActionMasked()) {

            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:{
                prevX = event.getX(index);
                prevY = event.getY(index);

                //Generamos un path con las coordenadas x e y donde se ha pulsado
                Path path = new Path();
                path.moveTo(prevX, prevY);
                mapPaths.put(activePointerId, path);

                this.invalidate();
                break;
            }

            case MotionEvent.ACTION_MOVE:
                for (int i = 0; i < event.getPointerCount(); i++) {
                    newX = event.getX(i);
                    newY = event.getY(i);

                    Path path = mapPaths.get(event.getPointerId(i));
                    path.lineTo(newX, newY);
                    listPaths.add(path);
                    listColors.add(color);
                    listStrokes.add(stroke);
                }

                this.invalidate();
                break;

            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return true;
    }

    /** Override methods OnDoubleTapListener **/
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        System.out.println("Single tap: Increase stroke width");
        stroke= stroke+2f;
        return true;
    }
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        System.out.println("Double tap: Decrease stroke width");
        stroke= stroke-2f;
        return true;
    }

    /** Override methods OnGestureListener **/
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
        System.out.println("Long press: Change color");
        // Llamada al método para escoger el color
        openColorPicker();
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        //System.out.println("Fling: Remove lines drawed");
        //listPaths.clear();
        //listColors.clear();
        //listStrokes.clear();
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    /** Show color picker **/
    private void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(context, color, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int c) {
                color = c;
            }
        });
        colorPicker.show();
    }
}