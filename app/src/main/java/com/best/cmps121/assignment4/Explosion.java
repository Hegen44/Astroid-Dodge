// Explosion.java : This file contains the class Explosion.
//

package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
/**
 *  An object of class Explosion represent explosion that spwan when two object collide. 
 */
public class Explosion {
    private int x;
    private int y;
    private int width;
    private int height;
    private int row;
    private Animation animation = new Animation();
    private Bitmap spritesheet;

   /**
   *  constructor
*  The constructor set the position of the position and the explosion type that will be rendered
   *  @oaran res This is the spritesheet of the object 
   *  @param x This is the paramter to set the x axis
   *  @param y This is the paramter to set the y axis
   *  @param w This is the paramter to set width of the object
   *  @param h This is the paramter to set heighit of the object
   *  @param numFrames This number represent the number of frame that the animation will use
   */
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

   /**
   *  this method render the explosion, only render the animaion once
   */
    public void draw(Canvas canvas)
    {
        if(!animation.playedOnce())
        {
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }

    }

   /**
   *  update is called each frame
   *  this method update hte animation frame
   */
    public void update()
    {
        if(!animation.playedOnce())
        {
            animation.update();
        }
    }

   /**
   *  This method is used to get the size of the explosion, since the exploration 
   *  is always prefect square, only the height is needed
   *  @return int This returns the height of the explosion
   */
    public int getHeight(){return height;}
}