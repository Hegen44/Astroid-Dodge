// Game.java : This file contains the class Game.
//

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
 *  An object of class Game represent game event and activity. 
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

   /**
   *  this method is used to pause most game Activity
   */
    @Override
    protected void onPause() {
        // stop the music
        backgroundMusic.stop();
        // stop and detach the accelerometer sensor.
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sm.unregisterListener(this);
        super.onPause();
    }

   /**
   *  this method is used to resume activity that is paused
   */
    @Override
    protected void onResume() {
        super.onResume();
        // resume the music
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

   /**
   *  this method is called when the phone is tilted 
   */
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