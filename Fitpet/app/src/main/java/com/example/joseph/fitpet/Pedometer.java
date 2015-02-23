package com.example.joseph.fitpet;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

/**
 * Created by Joseph on 2/16/2015.
 */
public class Pedometer extends Activity {
    private TextView x_orientation;
    private TextView y_orientation;
    private TextView z_orientation;

    private TextView textSensitive;

    private TextView textViewSteps;

    private Button buttonReset;

    private SensorManager sensorManager;
    private float acceleration;

    private float previousY;
    private float currentY;
    private int numSteps;

    private SeekBar seekBar;
    private int threshold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        x_orientation = (TextView) findViewById(R.id.x_orientation);
        y_orientation = (TextView) findViewById(R.id.y_orientation);
        z_orientation = (TextView) findViewById(R.id.z_orientation);

        textViewSteps = (TextView) findViewById(R.id.textSteps);
        textSensitive = (TextView) findViewById(R.id.textSensitive);

        buttonReset = (Button) findViewById(R.id.buttonReset);

        //seekBar = (SeekBar) findViewById(R.id.seekBar);

        //seekBar.setProgress(10);
        //seekBar.setOnSeekBarChangeListener(seekBarListener);
        threshold = 10;
        textSensitive.setText(String.valueOf(threshold));

        previousY = 0;
        currentY = 0;
        numSteps = 0;

        acceleration = 0.00f;

        enableAccelerometerListening();

    }

    @Override
    public Boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.activity_fullscreen, menu);
        return true;
    }

    private void enableAccelerometerListening(){
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorManager.registerListener(sensorEventListener,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
    }

    private SensorEventListener sensorEventListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float x = event.values[0];
                    float y = event.values[1];
                    float z = event.values[2];

                    currentY = y;

                    if (Math.abs(currentY - previousY) > threshold){
                        numSteps++;
                        textViewSteps.setText(String.valueOf(numSteps));
                    }

                    x_orientation.setText(String.valueOf(x));
                    y_orientation.setText(String.valueOf(y));
                    z_orientation.setText(String.valueOf(z));

                    previousY = y;



                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };

    public void resetSteps(View v) {
        numSteps = 0;
        textViewSteps.setText(String.valueOf(numSteps));
    }

    private OnSeekBarChangeListener seekBarListener =
            new OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    threshold = seekBar.getProgress();

                    textSensitive.setText(String.valueOf(threshold));
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            };

}
