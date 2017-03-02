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
public class Explosion {
    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public Explosion(Bitmap res, int x, int y, int w, int h, int numFrames)
    {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        Bitmap[] image = new Bitmap[numFrames];

        spritesheet = res;

        for(int i = 0; i<image.length; i++)
        {
            if(i%5==0&&i>0)row++;
            image[i] = Bitmap.createBitmap(spritesheet, (i-(5*row))*width, row*height, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(10);

    }
    public void draw(Canvas canvas)
    {
        if(!animation.playedOnce())
        {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }

    }
    public void update()
    {
        if(!animation.playedOnce())
        {
            animation.update();
        }
    }
    public int getHeight(){return height;}
}