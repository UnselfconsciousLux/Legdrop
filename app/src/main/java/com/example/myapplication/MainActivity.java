package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    private TextView xAcc, yAcc, zAcc;
    private Sensor mySensor;
    private SensorManager SM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Sensor manager here
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Accelerometer Sensor
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Register sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        //Assign TextView
        xAcc = (TextView)findViewById(R.id.xtext);
        yAcc = (TextView)findViewById(R.id.ytext);
        zAcc = (TextView)findViewById(R.id.ztext);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        xAcc.setText("X: " + event.values[0]);
        yAcc.setText("Y: " + event.values[1]);
        zAcc.setText("Z: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Not in use

    }
}
