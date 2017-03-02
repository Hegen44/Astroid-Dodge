package com.best.cmps121.assignment4;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
/**
 * Background music "Trial and Error" by PlayOnLoop.com
 * Licensed under Creative Commons By Attribution 4.0
 *
 * Code written based on the compress view code professor build on class
 * and paymon wang-lotfi's code in the youtube video series "How to make a 2D game for Android",
 * his youtube channel can be find at https://www.youtube.com/channel/UCKkABMS8IVJlu0G4ipPyZaA
 *
 */

public class Game extends Activity implements SensorEventListener{
    private Sensor direction;
    MediaPlayer backgroundMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //turn title off
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //set to full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backgroundMusic = MediaPlayer.create(Game.this, R.raw.bg);


        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(3.0f, 3.0f);
        backgroundMusic.start();

        setContentView(new GamePanel(this));
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    @Override
    protected void onPause() {
        backgroundMusic.stop();
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (backgroundMusic != null && !backgroundMusic.isPlaying()) {
            backgroundMusic = MediaPlayer.create(Game.this, R.raw.bg);
            backgroundMusic.setLooping(true);
            backgroundMusic.setVolume(3.0f, 3.0f);
            backgroundMusic.start();
        }
        // Gets the accelerometer sensor.
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        direction = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        // Let's attach the listener to the sensor.
        sm.registerListener(this, direction, SensorManager.SENSOR_DELAY_GAME);
    }

    public void onSensorChanged(SensorEvent event) {

        double x = event.values[0];
        double y = event.values[1];
        double z = event. values[2];
        //convert to angle
        double phi = Math.atan2(x, z)/(Math.PI/180);
        double angle = Math.atan2(y, z)/(Math.PI/180);
        // update angle to dynamics formula
        Dynamics.setaccelationX(phi);
        Dynamics.setaccelationY(angle);
        Dynamics.update();
    }

    public void onAccuracyChanged(Sensor s, int i) {
        // Nothing.
    }

}