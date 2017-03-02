package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;
/**
 * Background music "Trial and Error" by PlayOnLoop.com
 * Licensed under Creative Commons By Attribution 4.0
 *
 * Code written based on paymon wang-lotfi's code in the youtube
 * video series "How to make a 2D game for Android",
 * his youtube channel can be find at https://www.youtube.com/channel/UCKkABMS8IVJlu0G4ipPyZaA
 *
 */
public class Asteroid extends GameObject{

    private int speed;
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet;

    public Asteroid(Bitmap res, int x, int y, int w, int h, int s, int numFrames)
    {
        super.x = x;
        super.y = y;
        width = w;
        height = h;
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        image[0] = Bitmap.createBitmap(spritesheet, s*width, 0, width, height);
        animation.setFrames(image);

    }
    public void update()
    {
        y+=dy;
        animation.update();
    }
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}
    }

    public void setspeed(int s){
        this.dy = s;
    }

}