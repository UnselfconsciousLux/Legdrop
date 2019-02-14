package com.example.myapplication;

import java.util.*;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xAcc, yAcc, zAcc, zAngle;
    private double getAcceleration_X, getAcceleration_Y, getAcceleration_Z;
    private Sensor mySensor, myGravity;
    private SensorManager SM;

    Handler handler;
    int interval = 1000; //read sensor data each 1000 ms
    boolean flag = false;
    boolean isHandlerLive = false;

    private final Runnable processSensors = new Runnable() {
        @Override
        public void run() {
            flag = true;
            handler.postDelayed(this, interval);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler();

        //Sensor manager here
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //gravity
        myGravity = SM.getDefaultSensor(Sensor.TYPE_GRAVITY);

        //Accelerometer Sensor
       // mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        //SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        //Assign TextView
        xAcc = findViewById(R.id.xtext);
        yAcc = findViewById(R.id.ytext);
        zAcc = findViewById(R.id.ztext);
        zAngle = findViewById(R.id.zangle);

    }

    @Override
    public void onResume() {
        super.onResume();
        SM.registerListener(this, myGravity,
                SensorManager.SENSOR_DELAY_NORMAL);

        handler.post(processSensors);
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(processSensors);

        super.onPause();
    }



    @Override
    public void onSensorChanged(SensorEvent event) {
       if (flag) {
           getAcceleration_X = event.values[0];
           getAcceleration_Y = event.values[1];
           getAcceleration_Z = event.values[2];
           xAcc.setText("X: " + event.values[0]);
           yAcc.setText("Y: " + event.values[1]);
           zAcc.setText("Z: " + event.values[2]);
           angleCalculation(getAcceleration_X, getAcceleration_Y, getAcceleration_Z );
           flag = false;
       }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not in use

    }

    public void angleCalculation(double Ax, double Ay, double Az) {
        double zAxisAngle = 90 + (180/Math.PI) * Math.atan(Az/(Math.sqrt(Math.pow(Ax, 2) + Math.pow(Ay, 2))));

            String zAngleFinal = Double.toString(zAxisAngle);
            zAngle.setText(zAngleFinal);
    }
}
