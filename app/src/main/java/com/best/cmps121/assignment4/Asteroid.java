// Asteroid.java : This file contains the class Astroid.
//
package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;
/**
 *  An object of class Asteroid represent the astroid GameObject prefab. 
 */
public class Asteroid extends GameObject{

    private int speed;  // overall speed
    private Random rand = new Random();
    private Animation animation = new Animation();
    private Bitmap spritesheet; 

   /**
   *  constructor
   *  @oaran res This is the spritesheet of the object 
   *  @param x This is the paramter to set the x axis
   *  @param y This is the paramter to set the y axis
   *  @param w This is the paramter to set width of the object
   *  @param h This is the paramter to set heighit of the object
   *  @param s This number set the variant sprite set that will be used for for the game object
   *  @param numFrames This number represent the number of frame that the animation will use
   */
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

   /**
   *  This method is used to get the collision box of the object
   *  It override the getRect function in GameObject
   *  @return Rect This returns the collison box represent by a rect struct
   */
   @Override
    protected Rect getRect(){
        return new Rect(x+ width*17/100, y +height* 17/100 , x+width*67/100, y+width*67/100);
    }
   /**
   *  update is called each frame
   */
    public void update()
    {
        y+=dy;
        animation.update();
    }
   /**
   * This method is render the object's current sprite
   *  @param canvas This is the canvas the game is rendering on
   */
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }catch(Exception e){}
    }
   /**
   * This method is used to se the speed of the astreoid
   * @param s This parameter is the speed that will be apply to the astroid 
   */
    public void setspeed(int s){
        this.dy = s;
    }

}