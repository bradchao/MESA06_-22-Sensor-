package com.example.user.brad22;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager smgr;
    private Sensor sensor;
    private MySensorListener listener;
    private MyView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        myView = new MyView(this);
        setContentView(myView);

        smgr = (SensorManager)getSystemService(SENSOR_SERVICE);

        List<Sensor> sensors =  smgr.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors){
            Log.v("brad", sensor.getName() + ":" + sensor.getType() + ":" +sensor.getVendor());
        }

        sensor = smgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        listener = new MySensorListener();


    }

    private class MyView extends View {
        private boolean isInit;
        private int viewW, viewH;
        private Paint paintH, paintV, paintBall;
        private float ballX, ballY,ballR;

        public MyView(Context context) {
            super(context);
            setBackgroundColor(Color.BLACK);
        }

        private void init(){
            viewW = getWidth(); viewH = getHeight();
            paintH = new Paint(); paintH.setColor(Color.RED);
            paintV = new Paint(); paintV.setColor(Color.BLUE);
            paintBall = new Paint(); paintBall.setColor(Color.YELLOW);
            ballX = viewW/2f; ballY = viewH/2f; ballR = 50;
        }


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            if (!isInit) init();

            canvas.drawCircle(ballX, ballY, ballR, paintBall);
            canvas.drawLine(0,viewH/2f,viewW,viewH/2f,paintH);
            canvas.drawLine(viewW/2f,0,viewW/2f,viewH,paintV);
        }
    }

    private class MySensorListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            float[] values = sensorEvent.values;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    }

    @Override
    protected void onStart() {
        super.onStart();
        smgr.registerListener(listener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        smgr.unregisterListener(listener);
    }
}
