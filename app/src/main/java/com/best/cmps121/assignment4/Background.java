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
public class Background {

    private Bitmap image;
    private int x, y, dy;

    public Background(Bitmap res)
    {
        image = res;
    }
    public void update()
    {
        y+=dy;
        if(y>GamePanel.HEIGHT){
            y=0;
        }
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        // if y is below the top of the screen, redraw to loop
        if(y>0)
        {
            canvas.drawBitmap(image, x, y-GamePanel.HEIGHT, null);
        }
    }
    public void setVector(int dy)
    {
        this.dy = dy;
    }
}
