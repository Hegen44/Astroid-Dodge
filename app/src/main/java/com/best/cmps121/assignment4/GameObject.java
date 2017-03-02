package com.best.cmps121.assignment4;

import android.graphics.Rect;
/**
 * Background music "Trial and Error" by PlayOnLoop.com
 * Licensed under Creative Commons By Attribution 4.0
 *
 * Code written based on paymon wang-lotfi's code in the youtube
 * video series "How to make a 2D game for Android",
 * his youtube channel can be find at https://www.youtube.com/channel/UCKkABMS8IVJlu0G4ipPyZaA
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }

    // collision box for astroid
    public Rect getastRect()
    {
        return new Rect(x+ width*17/100, y +height* 17/100 , x+width*67/100, y+width*67/100);
    }
    // collision box for player
    public Rect getRectplayer1()
    {return new Rect(x+ width*9/25,	y +height*1/20,
            x+ width*9/25 + width*27/100, y +height*1/20 +height*7/50);}
    public Rect getRectplayer2()
    {return new Rect(x+ width*23/100, y +height*19/100,
            x+ width*23/100+ width*53/100, y +height*19/100 +height*1/8);}
    public Rect getRectplayer3()
    {return new Rect(x+  width*11/100, y +height*8/25,
            x+  width*11/100 + width*39/50, y +height*8/25 +height*19/100);}
    public Rect getRectplayer4()
    {return new Rect(x+ width*11/100, y +height*51/100,
            x+ width*11/100 + width*39/50, y +height*51/100 +height*43/500);}
}