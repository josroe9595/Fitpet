package com.pedometer.joseph.testapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;



public class MainActivity extends Activity{
    private int mInterval = 1000; // 5 seconds by default, can be changed later
    private Handler mHandler;
    private int hInterval = 60000;
    private Handler hHandler;
    private SharedPreferences mSettings;
    //private PedometerSettings mPedometerSettings;
    //private Utils mUtils;

    private ProgressBar mStepValueView;
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
    private int Hearts;
    private ImageButton[] heartStats = new ImageButton[5];
    private ImageView cIm;
    private AnimationDrawable anim0;
    private AnimationDrawable anim1;
    private AnimationDrawable anim2;
    private AnimationDrawable anim3;
    private AnimationDrawable anim4;

    //Animation animation;
    //Texture petTexture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        hHandler = new Handler();


        //mStepValue = 0;
        //mPaceValue = 0;

        setContentView(R.layout.activity_main);




        BitmapDrawable frame1 = (BitmapDrawable) getResources().getDrawable(
                R.drawable.c1);
        BitmapDrawable frame2 = (BitmapDrawable) getResources().getDrawable(
                R.drawable.c2);
        BitmapDrawable frame3 = (BitmapDrawable) getResources().getDrawable(
                R.drawable.c3);
        BitmapDrawable frame4 = (BitmapDrawable) getResources().getDrawable(
                R.drawable.c4);
        BitmapDrawable frameb = (BitmapDrawable) getResources().getDrawable(
                R.drawable.cb);
        BitmapDrawable framea = (BitmapDrawable) getResources().getDrawable(
                R.drawable.ca);
        BitmapDrawable framep = (BitmapDrawable) getResources().getDrawable(
                R.drawable.cp);
        BitmapDrawable frameo = (BitmapDrawable) getResources().getDrawable(
                R.drawable.co);

        anim0 = new AnimationDrawable();
        anim0.setOneShot(true);
        anim0.addFrame(frame1,100);

        anim1 = new AnimationDrawable();
        anim1.setOneShot(true);
        anim1.addFrame(frame1, 200);
        anim1.addFrame(frame2, 200);
        anim1.addFrame(frame3, 200);
        anim1.addFrame(frame4, 200);
        anim1.addFrame(frameb, 200);
        anim1.addFrame(frame3, 200);
        anim1.addFrame(frame2, 200);
        anim1.addFrame(frame1, 200);

        anim2 = new AnimationDrawable();
        anim2.setOneShot(true);
        anim2.addFrame(frame1, 200);
        anim2.addFrame(frame2, 200);
        anim2.addFrame(frame3, 200);
        anim2.addFrame(frame4, 200);
        anim2.addFrame(framea, 200);
        anim2.addFrame(frame3, 200);
        anim2.addFrame(frame2, 200);
        anim2.addFrame(frame1, 200);

        anim3 = new AnimationDrawable();
        anim3.setOneShot(true);
        anim3.addFrame(frame1, 200);
        anim3.addFrame(frame2, 200);
        anim3.addFrame(frame3, 200);
        anim3.addFrame(frame4, 200);
        anim3.addFrame(framep, 200);
        anim3.addFrame(frame3, 200);
        anim3.addFrame(frame2, 200);
        anim3.addFrame(frame1, 200);

        anim4 = new AnimationDrawable();
        anim4.setOneShot(true);
        anim4.addFrame(frame1, 200);
        anim4.addFrame(frame2, 200);
        anim4.addFrame(frame3, 200);
        anim4.addFrame(frame4, 200);
        anim4.addFrame(frameo, 200);
        anim4.addFrame(frame3, 200);
        anim4.addFrame(frame2, 200);
        anim4.addFrame(frame1, 200);















        mStepValueView = (ProgressBar)findViewById(R.id.healthView);
        mCoinView = (TextView)findViewById(R.id.coinView);

        hunger = 100;
        happiness = 100;
        coins = 100;

        Hearts = 5;
        previousY = 0;
        currentY = 0;
        numSteps = 0;
        Threshold = 5;
        ThreshMax = 25;
        mStepValueView.setProgress(hunger); //String.valueOf(hunger));

        mCoinView.setText(String.valueOf(coins));

        numFries = 0;
        numPizza = 0;
        numMilk = 0;
        numCarrots = 0;
        numKale = 0;

        cIm = (ImageView) findViewById(R.id.cIM);
        cIm.setBackground(anim0);

        ImageButton buyB = (ImageButton)findViewById(R.id.buyBanana);

        heartStats[0] = (ImageButton)findViewById(R.id.h1);
        heartStats[1] = (ImageButton)findViewById(R.id.h2);
        heartStats[2] = (ImageButton)findViewById(R.id.h3);
        heartStats[3] = (ImageButton)findViewById(R.id.h4);
        heartStats[4] = (ImageButton)findViewById(R.id.h5);

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
            if (hunger == 0){
                hunger = 100;
                Hearts--;
                heartStats[Hearts].setImageResource(R.drawable.heartempty);
            }
            //System.out.println(hunger + " THISISHUNGER");
            mStepValueView.setProgress(hunger);
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
        if (true) {
            if (hunger < 100) {
                if (numFries > 0 && hunger <= 85) {
                    numFries--;
                    hunger += 15;


                    cIm.setBackground(anim1);
                    anim1.stop();
                    anim1.start();
                    //anim1.stop();


                }
                //numSteps++;
                //mStepValueView.setText(String.valueOf(numSteps));
                else if (numPizza > 85 && hunger <= 90) {
                    numPizza--;
                    hunger += 10;
                    cIm.setBackground(anim2);
                    anim2.stop();
                    anim2.start();
                    //anim2.stop();

                }
                //numSteps++;
                //mStepValueView.setText(String.valueOf(coins));
                else if (numMilk > 0 && hunger <= 70) {
                    numMilk--;
                    hunger += 30;
                    cIm.setBackground(anim3);
                    anim3.stop();
                    anim3.start();
                    //anim3.stop();

                }
                //numSteps++;
                //mStepValueView.setText(String.valueOf(coins));
                else if (numKale > 0 && hunger <= 95) {
                    numKale--;
                    hunger += 5;
                    cIm.setBackground(anim4);
                    anim4.stop();
                    anim4.start();
                    //anim4.stop();

                }

                //numSteps++;
                // mStepValueView.setText(String.valueOf(hunger));
                //mStepValueView.setText(String.valueOf(hunger));
                if (hunger == 100 && Hearts < 5) {
                    heartStats[Hearts].setImageResource(R.drawable.heartfull);
                    Hearts++;

                }

            }
        }
        /*catch (InterruptedException e){

        }*/
        mStepValueView.setProgress(hunger);
    }

    void startRepeatingTask() {
        mStatusChecker.run();
    }


    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }






}
