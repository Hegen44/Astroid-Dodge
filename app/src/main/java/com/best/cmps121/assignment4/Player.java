// Player.java : This file contains the class Player.
//
package com.best.cmps121.assignment4;

import android.graphics.Bitmap;
import android.graphics.Canvas;
/**
 *  An object of class Player represent the player GameObject prefab. 
 */
public class Player extends GameObject{
    private Bitmap spritesheet;
    private float score;
    private int hard;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

   /**
   *  constructor
   *  @oaran res This is the spritesheet of the object 
   *  @param w This is the paramter to set width of the object
   *  @param h This is the paramter to set heighit of the object
   *  @param numFrames This number represent the number of frame that the animation will use
   */
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

   /**
   *  update is called each frame
   */
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

   /**
   * This method is render the object's current sprite
   *  @param canvas This is the canvas the game is rendering on
   */
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }


   /**
   *  This method is used to check if this gameObject collide with the paramter object
   *  since player has multiple collide box, it test each box with the gameojbect collider
   *  @param b This is the gameObject to check against
   *  @return Rect This returns the collison box represent by a rect struct
   */
   @Override
    public boolean isCollided(gameObject obj){
        return Rect.intersects(obj.getastRect(), getRectplayer1())||
                Rect.intersects(obj.getastRect(), getRectplayer2()) ||
                Rect.intersects(obj.getastRect(), getRectplayer3()) ||
                Rect.intersects(obj.getastRect(), getRectplayer4());
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


      /**
   *  These method are used to reset the speed and score.
   */
   public void resetDX(){dx = 0;}
   public void resetScore(){score = 0; hard=0; Dynamics.resetP();
        Dynamics.resetVX(); Dynamics.resetaccX();}

    // getter function and setter funciton
    public float getScore(){return score;}
    public float gethard(){return hard;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}

}
