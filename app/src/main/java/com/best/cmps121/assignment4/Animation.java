package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
/**
 * Background music "Trial and Error" by PlayOnLoop.com
 * Licensed under Creative Commons By Attribution 4.0
 *
 * Code written based on paymon wang-lotfi's code in the youtube
 * video series "How to make a 2D game for Android",
 * his youtube channel can be find at https://www.youtube.com/channel/UCKkABMS8IVJlu0G4ipPyZaA
 *
 */
public class Animation {
    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;
    // determine frames
    public void setFrames(Bitmap[] frames)
    {
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }
    public void setDelay(long d){delay = d;}
    public void setFrame(int i){currentFrame= i;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;

        if(elapsed>delay)
        {
            currentFrame++;
            startTime = System.nanoTime();
        }
        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }
    public Bitmap getImage(){
        return frames[currentFrame];
    }
    public int getFrame(){return currentFrame;}
    public boolean playedOnce(){return playedOnce;}

}
