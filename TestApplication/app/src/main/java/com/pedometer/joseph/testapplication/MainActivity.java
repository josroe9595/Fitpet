package com.pedometer.joseph.testapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;



public class MainActivity extends Activity{
    private int mInterval = 3000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private int hInterval = 60000;
    private Handler hHandler;
    private SharedPreferences mSettings;
    //private PedometerSettings mPedometerSettings;
    //private Utils mUtils;

    private TextView mStepValueView;
    private TextView mCoinView;
    private TextView mDistanceValueView;
    private TextView mSpeedValueView;
    private TextView mCaloriesValueView;
    TextView mDesiredPaceView;
    private int mStepValue;
    private int mPaceValue;
    private float mDistanceValue;
    private float mSpeedValue;
    private int mCaloriesValue;
    private float mDesiredPaceOrSpeed;
    private int mMaintain;
    private boolean mIsMetric;
    private float mMaintainInc;
    private boolean mQuitting = false;

    private int hunger;
    private int happiness;
    private int coins;

    private int numFries;
    private int numPizza;
    private int numMilk;
    private int numCarrots;
    private int numKale;


    private TextView textView;
    private SensorManager mSensorManager;
    private Sensor mStepCounterSensor;
    private Sensor mStepDetectorSensor;

    private float previousY;
    private float currentY;
    private int numSteps;
    private int Threshold;
    private int ThreshMax;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        hHandler = new Handler();


        //mStepValue = 0;
        //mPaceValue = 0;

        setContentView(R.layout.activity_main);
        mStepValueView = (TextView)findViewById(R.id.healthView);
        mCoinView = (TextView)findViewById(R.id.coinView);

        hunger = 100;
        happiness = 100;
        coins = 0;

        previousY = 0;
        currentY = 0;
        numSteps = 0;
        Threshold = 5;
        ThreshMax = 25;
        mStepValueView.setText(String.valueOf(hunger));

        mCoinView.setText(String.valueOf(coins));

        numFries = 0;
        numPizza = 0;
        numMilk = 0;
        numCarrots = 0;
        numKale = 0;

        ImageButton buyB = (ImageButton)findViewById(R.id.buyBanana);

        buyB.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (coins >= 60) {
                            numFries++;
                            coins-=60;
                            mCoinView.setText(String.valueOf(coins));

                        }
                    }
                }
        );

        ImageButton buyA = (ImageButton)findViewById(R.id.buyApple);

        buyA.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (coins >= 40) {
                            numPizza++;
                            coins -= 40;
                            mCoinView.setText(String.valueOf(coins));

                        }
                    }
                }
        );

        ImageButton buyP = (ImageButton)findViewById(R.id.buyPine);

        buyP.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (coins >= 90) {
                            numMilk++;
                            coins -= 90;
                            mCoinView.setText(String.valueOf(coins));

                        }
                    }
                }
        );

        ImageButton buyO = (ImageButton)findViewById(R.id.buyOrange);

        buyO.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (coins >= 20) {
                            numKale++;
                            coins -= 20;
                            mCoinView.setText(String.valueOf(coins));

                        }
                    }
                }
        );








        enableAccelerometerListening();
        startRepeatingTask();


        /*super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mStepCounterSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mStepDetectorSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);*/
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    private void enableAccelerometerListening(){
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(sensorEventListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                mSensorManager.SENSOR_DELAY_NORMAL);
    }
    private SensorEventListener sensorEventListener =
            new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    float y = event.values[1];
                    currentY = y;
                    //System.out.println(currentY);
                    if (Math.abs(currentY - previousY) > Threshold && Math.abs(currentY - previousY) < ThreshMax){
                        coins++;
                        mCoinView.setText(String.valueOf(coins));
                        //System.out.println(currentY);
                    }
                    previousY = currentY;
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
    @Override
    protected void onStart() {
        //Log.i(TAG, "[ACTIVITY] onStart");
        super.onStart();
    }

    /*public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        float[] values = event.values;
        int value = -1;

        if (values.length > 0) {
            value = (int) values[0];
        }

        if (sensor.getType() == Sensor.TYPE_STEP_COUNTER) {textView.setText("Step Counter Detected : " + value);
        } else if (sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {textView.setText("Step Detector Detected : " + value);
        }
    }*/

    protected void onResume() {

        super.onResume();

        //mSensorManager.registerListener(this, mStepCounterSensor,SensorManager.SENSOR_DELAY_FASTEST);
        //mSensorManager.registerListener(this, mStepDetectorSensor, SensorManager.SENSOR_DELAY_FASTEST);

    }

    protected void onStop() {
        super.onStop();
        //stopRepeatingTask();
        //mSensorManager.unregisterListener(this, mStepCounterSensor);
        //mSensorManager.unregisterListener(this, mStepDetectorSensor);
    }




    //@Override
   /* public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // TODO Auto-generated method stub
    }





    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            eat(); //this function can change value of mInterval.
            hunger--;
            System.out.println(hunger + " THISISHUNGER");
            mStepValueView.setText(String.valueOf(hunger));
            mHandler.postDelayed(mStatusChecker, mInterval);
        }

        //@Override
        /*public void decH(){
            hunger--;
            mStepValueView.setText(String.valueOf(hunger));

            //mCoinView.setText(String.valueOf(coins));
            hHandler.postDelayed(mStatusChecker, mInterval);
        }*/
    };

    void eat() {
        if (hunger < 100) {
            if (numFries > 0 && hunger < 85) {
                numFries--;
                hunger += 15;
            }
            //numSteps++;
            //mStepValueView.setText(String.valueOf(numSteps));
            else if (numPizza > 85 && hunger < 90) {
                numPizza--;
                hunger += 10;
            }
            //numSteps++;
            //mStepValueView.setText(String.valueOf(coins));
            else if (numMilk > 0 && hunger < 70) {
                numMilk--;
                hunger += 30;
            }
            //numSteps++;
            //mStepValueView.setText(String.valueOf(coins));
            else if (numKale > 0 && hunger < 95) {
                numKale--;
                hunger += 5;
            }
            //numSteps++;
           // mStepValueView.setText(String.valueOf(hunger));
            mStepValueView.setText(String.valueOf(hunger));

        }
        mStepValueView.setText(String.valueOf(hunger));
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }


    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }
}
