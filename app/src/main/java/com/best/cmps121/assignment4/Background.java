// Background.java : This file contains the class Background.
//
package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
/**
 *  An object of class Background represent the Background image of the game. 
 */
public class Background {

    private Bitmap image;   // image of the background
    private int x, y, dy; // position and space of the scrolling background

   /**
   *  constructor
   *  @oaran res This is the spritesheet of the object 
   */
    public Background(Bitmap res)
    {
        image = res;
    }

    /**
   *  update is called each frame
   *  this method calculate the y axis positon of the image background as it scroll vertically
   */
    public void update()
    {
        y+=dy;
        if(y>GamePanel.HEIGHT){
            y=0;
        }
    }

   /**
   * This method is render the object's current sprite
   *  @param canvas This is the canvas the game is rendering on
   */
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x, y,null);
        // if y is below the top of the screen, redraw to loop
        if(y>0)
        {
            canvas.drawBitmap(image, x, y-GamePanel.HEIGHT, null);
        }
    }

   /**
   * This method adjust the speed of the background image to be in syn with the player
   * @param dy This is the speed of the player
   */
    public void setVector(int dy)
    {
        this.dy = dy;
    }
}
