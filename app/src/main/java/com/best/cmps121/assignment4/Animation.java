// Animation.java : This file contains the class Animation.
//

package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.hardware.SensorManager;
/**
 *  An object of class Animaiton that handel animating the sprites. 
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
   /**
   *  This method get the actual of the current sprite animation
   *  @return Bitmap This return the current image of the animation
   */
    public Bitmap getImage(){
        return frames[currentFrame];
    }
   /**
   *  This method get the actual of the current frame number
   *  @return int this return the current number of frame of the animation
   */
    public int getFrame(){return currentFrame;}
       /**
   *  This method to check this animaiton should only play once or if it should loop
   *  @return bool reteurn if the animtion should loop or not
   */
    public boolean playedOnce(){return playedOnce;}

}
