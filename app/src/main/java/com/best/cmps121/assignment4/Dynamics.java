package com.best.cmps121.assignment4;

import android.view.animation.AnimationUtils;
/**
 * Background music "Trial and Error" by PlayOnLoop.com
 * Licensed under Creative Commons By Attribution 4.0
 *
 * Code written based on the compress view code professor build on class
 *
 */
public final class Dynamics {

    /**
     * Used to compare floats, if the difference is smaller than this, they are
     * considered equal
     */
    private static final float TOLERANCE = 0.01f;

    /** The position the dynamics should to be at */
    private static float targetAngle = 30.0f;

    /** The current position of the dynamics */
    private static float position;

    /** The current velocity of the dynamics at y axis*/
    private static float velocity = 0;

    /** The current velocity of the dynamics at x axis*/
    private static float velocityX;

    /** The time the last update happened */
    private static long lastTime;

    /** Accel of x */
    private static double accX;

    /** Accel of y*/
    private static double accY;



    /** Flags to check the change of sign */
    private static boolean pos = false;
    private static boolean neg = false;

    private Dynamics(){
        long now  = AnimationUtils.currentAnimationTimeMillis();
        lastTime = now;
        velocity = 0;  velocityX = 0;
        position = 0;
    }

    // where the magic happen
    public static void update() {
        //get time
        long now = AnimationUtils.currentAnimationTimeMillis();
        float dt = Math.min(now - lastTime, 50) / 1000f;

        //calc velocity on y axis
        velocity += accY * dt;
        if (velocity > 60) {
            velocity = 60;
        } else if (velocity <= 0){
            velocity = 2;
        }
        position += velocity * dt;

        //calc velocity on x axis
        velocityX += accX * dt;
        if(velocityX >30){velocityX = 30;}
        else if(velocityX < -30){velocityX = -30;}

        //update timer
        lastTime = now;
    }
    // check if it is at 30 degree
    public static boolean isAtRest(double angle) {
        return Math.abs(angle- targetAngle) < TOLERANCE;
    }
    // check if it is at rest in the x axis
    public static boolean isAtRestX(double angle){
        return Math.abs(angle-0) < TOLERANCE;
    }

    //getter function
    public static float getPosition() {
        return position;
    }
    public static float getVelocityX() {
        return velocityX;
    }
    public static float getVelocityY() {
        return velocity;
    }

    // accelation update
    public static void setaccelationX(double x){
        if(x >0){
            // check sign
            if(pos)
            {
                // stop player  if true
                velocityX = 0;
                pos = false;
            }
            accX = -2*x;
            neg = true;
            if(isAtRestX(x)){
                accX=0;
            }
        } else if(x < 0){
            if(neg){
                velocityX =0;
                neg = false;
            }
            accX = -2*x;
            pos = true;
            if(isAtRestX(x)){
                accX=0;
            }
        }
    }
    public static void setaccelationY(double y){
        accY = y - targetAngle;
        if( isAtRest(y)) {
            accY = 0;
        }
    }

    // reset function
    public static void resetaccX(){
        accX=0;
    }
    public static void resetV()
    {
        velocity = 0;
    }
    public static void resetVX()
    {
        velocityX = 0;
    }
    public static void resetP()
    {
        position = 0;
    }

}

