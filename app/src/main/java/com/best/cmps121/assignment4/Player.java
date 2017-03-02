package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
/**
 * Background music "Trial and Error" by PlayOnLoop.com
 * Licensed under Creative Commons By Attribution 4.0
 *
 * Code written based on paymon wang-lotfi's code in the youtube
 * video series "How to make a 2D game for Android",
 * his youtube channel can be find at https://www.youtube.com/channel/UCKkABMS8IVJlu0G4ipPyZaA
 *
 */

public class Player extends GameObject{
    private Bitmap spritesheet;
    private float score;
    private int hard;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames) {

        x = (GamePanel.WIDTH /2) - w/2;
        y = GamePanel.HEIGHT - GamePanel.HEIGHT / 5;
        dx = 0;
        score = 0;
        height = h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        // load  animation
        for (int i = 1; i-1 < numFrames; ++i)
        {
            image[i-1] = Bitmap.createBitmap(spritesheet, i*2*width, 0, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(0);
        startTime = System.nanoTime();

    }

    public void setUp(boolean b){up = b;}

    public void update()
    {
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            hard += 20; // increase difficulty
            score = Dynamics.getPosition();
            startTime = System.nanoTime();
        }
        dx = (int)Dynamics.getVelocityX();

        animation.update();
        x += dx;
        if(x < 0){
            x = 0;
        } else if (x+width > GamePanel.WIDTH){
            x = GamePanel.WIDTH-width;
        }

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }
    public float getScore(){return score;}
    public float gethard(){return hard;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDX(){dx = 0;}
    public void resetScore(){score = 0; hard=0; Dynamics.resetP();
        Dynamics.resetVX(); Dynamics.resetaccX();}
}
