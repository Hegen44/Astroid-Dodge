// GameObject.java : This file contains the abstract class gameObject.
//
package com.best.cmps121.assignment4;

import android.graphics.Rect;
/**
 *  An object of class GameObject. This is a fundamental class
 *  that represent all controllable or movable objects in the game.
 */
public abstract class GameObject {
    protected int x;    // x axis position
    protected int y;    // y axis position
    protected int dy;   // x axis speed
    protected int dx;   // y axis speed
    protected int width;
    protected int height;
	
	/**
   *  These three method is used to set the position of the gameObject
   *  @param x This is the paramter to set the x axis
   *  @param y This is the paramter to set the y axis
   *  @param pos This is the paramter that contain the position of both x and y
   */
    public void setX(int x)
    {
        this.x = x;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public void setPosition(Vector2 pos){
        this.x = pos.x;
        this.y = pos.y;
    }
	/**
   *  These three method is used to get the position of the gameObject
   *  @return int the position of game object's x or y axis
   */
    public int getX()
    {
        return x;
    }
    public int getY()
    {
        return y;
    }
   /**
   *  These tow method is used to get the size of the game object
   *  @return int the height or width of the game object
   */
    public int getHeight()
    {
        return height;
    }
    public int getWidth()
    {
        return width;
    }

    /**
   *  This method is used to get the collision box of the object
   *  @return Rect This returns the collison box represent by a rect struct
   */
    protected Rect getRect(){
        return new Rect(x, y, x+width, y+width);
    }

   /**
   *  This method is used to check if this gameObject collide with the paramter object
   *  @param b This is the gameObject to check against
   *  @return Rect This returns the collison box represent by a rect struct
   */

    public boolean isCollided(gameObject b){
        return Rect.intersects(getRect(),b.getRect());
    }
}