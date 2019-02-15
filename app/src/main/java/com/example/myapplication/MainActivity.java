package com.example.myapplication;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

import static java.lang.Math.round;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xAcc, yAcc, zAcc, zAngle;
    private Sensor mySensor, myGravity;
    private SensorManager SM;

    Handler handler;
    int interval = 100; //read sensor data each 1000 ms
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
//           DecimalFormat df = new DecimalFormat("#.###");
//           df.setRoundingMode(RoundingMode.CEILING);
           double getAcceleration_X = event.values[0];
           double getAcceleration_Y = event.values[1];
           double getAcceleration_Z = event.values[2];

//           df.format(getAcceleration_Y);
//           df.format(getAcceleration_Z);
           Log.d("Xaccelration is", Double.toString(getAcceleration_X));
           Log.d("Yaccelration is", Double.toString(getAcceleration_Y));
           Log.d("Zaccelration is", Double.toString(getAcceleration_Z));
           xAcc.setText("X: " + new DecimalFormat("#.##").format(event.values[0]));
           yAcc.setText("Y: " + new DecimalFormat("#.##").format(event.values[1]));
           zAcc.setText("Z: " + new DecimalFormat("#.##").format(event.values[2]));
           angleCalculation(getAcceleration_X, getAcceleration_Y, getAcceleration_Z );
           flag = false;
       }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not in use

    }

    public void angleCalculation(double Ax, double Ay, double Az) {
        double zAxisAngle = (180/Math.PI) * Math.atan(Az/(Math.sqrt(Math.pow(Ax, 2) + Math.pow(Ay, 2))));
            zAngle.setText(new DecimalFormat("###.###").format(zAxisAngle));
    }
}
