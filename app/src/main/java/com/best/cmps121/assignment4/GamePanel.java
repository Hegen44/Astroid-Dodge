package com.best.cmps121.assignment4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
    Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.space);
    Bitmap playerimage = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship64v2);
    Bitmap astroidimage = BitmapFactory.decodeResource(getResources(), R.drawable.asteroid64);
    Bitmap explosionimage = BitmapFactory.decodeResource(getResources(),R.drawable.explosion128);
    public static int WIDTH;
    public static int HEIGHT;
    public static int speed = 1; // starting speed
    private MainThread thread;
    private Background bg;
    private Player player;
    private long asteroidStartTime;
    private ArrayList<Asteroid> asteroid;
    private Random rand = new Random();
    private boolean newGameCreated = true;
    private Explosion explosion;
    private long startReset;
    private boolean reset;
    private boolean disappear;
    private boolean started;
    private float best;
    final float testTextSize = 48f;
    final String tittle = "Give Me Good Grade,Please?";
    private boolean loading = true;
    MediaPlayer exp;


    public GamePanel(Context context)
    {
        super(context);
        WIDTH = image.getWidth();;
        HEIGHT = image.getHeight();
        exp = MediaPlayer.create(getContext(), R.raw.exp);
        exp.setLooping(false);
        exp.setVolume(10.0f, 3.0f);
        //add the callback to the surfaceholder to intercept events
        getHolder().addCallback(this);
        //make gamePanel focusable so it can handle events
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        loading = true;
        boolean retry = true;
        started = false;
        int counter = 0;
        while(retry && counter < 1000)
        {
            ++counter;
            try{
                thread.setRunning(false);
                thread.join();
                retry = false;
                thread = null;

            }catch(InterruptedException e){e.printStackTrace();}

        }

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        bg = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.space));
        bg.setVector(speed);
        player = new Player(playerimage, playerimage.getWidth() / 10, playerimage.getHeight(), 2);
        asteroid = new ArrayList<Asteroid>();
        asteroidStartTime = System.nanoTime();

        thread = new MainThread(getHolder(), this);
        //we can safely start the game loop
        thread.setRunning(true);
        thread.start();

    }
    // touch to start the game
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying() && newGameCreated && reset)
            {
                player.setPlaying(true);
            }
            if(player.getPlaying())
            {
                if(!started)started = true;
                reset = false;
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update()
    {
        // check if the game has starred or not
        if(player.getPlaying()) {
            speed = (int)Dynamics.getVelocityY();
            bg.setVector(speed);
            bg.update();
            player.update();
            long asteroidElapsed = (System.nanoTime() - asteroidStartTime) / 1000000;
            //create asteroids
            if (asteroidElapsed > (2000 - player.gethard() / 4)) {
                asteroid.add(new Asteroid(astroidimage,
                        rand.nextInt(GamePanel.WIDTH - astroidimage.getWidth()/4+ 1),
                        0 - (rand.nextInt(GamePanel.HEIGHT + 1) + GamePanel.HEIGHT / 2),
                        astroidimage.getWidth() / 4, astroidimage.getHeight(), rand.nextInt(3) + 1, 1));
                //reset timer
                asteroidStartTime = System.nanoTime();
            }
            //loop through every asteroids and check collision and remove
            for (int i = 0; i < asteroid.size(); i++) {
                //update missile
                asteroid.get(i).setspeed(speed);
                asteroid.get(i).update();

                if (collision(asteroid.get(i), player)) {
                    exp.start();
                    asteroid.remove(i);
                    player.setPlaying(false);
                    break;
                }
                //remove astroids if it is way off the screen
                if (asteroid.get(i).getY() > GamePanel.HEIGHT) {
                    asteroid.remove(i);
                    break;
                }
            }
        } else{
            // reset position for new game
            Dynamics.resetP();
            player.resetDX();
            if(!reset)
            {
                newGameCreated = false;
                startReset = System.nanoTime();
                reset = true;
                disappear = true;
                explosion = new Explosion(explosionimage, player.getX() - playerimage.getWidth()/20,
                        player.getY(), explosionimage.getWidth()/5, explosionimage.getHeight()/2, 10);
            }
            explosion.update();
            // some times for loading
            long resetElapsed = (System.nanoTime()-startReset)/1000000;
            if(resetElapsed > 2500 && !newGameCreated)
            {
                newGame();
            }
        }
    }

    // function handeling collision
    public boolean collision(GameObject a, GameObject b)
    {
        if(Rect.intersects(a.getastRect(), b.getRectplayer1())||
                Rect.intersects(a.getastRect(), b.getRectplayer2()) ||
                Rect.intersects(a.getastRect(), b.getRectplayer3()) ||
                Rect.intersects(a.getastRect(), b.getRectplayer4()))
        {
            return true;
        }
        return false;
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        final float scaleFactorX = (float) getWidth()/WIDTH;
        final float scaleFactorY = (float) getHeight()/HEIGHT;
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX, scaleFactorY);
            bg.draw(canvas);
            drawText(canvas);
            if(!disappear) {
                player.draw(canvas);
            }
            for(Asteroid as: asteroid)
            {
                as.draw(canvas);
            }
            if(started)
            {
                explosion.draw(canvas);
            }
            canvas.restoreToCount(savedState);
        }
    }

    // new game, reset everything
    public void newGame()
    {
        Dynamics.resetV();
        disappear = false;
        asteroid.clear();
        best = getbest(getContext());
        if(player.getScore() > best)
        {
           setbest(getContext(), player.getScore());
            // update score
            best = getbest(getContext());
        }
        player.resetDX();
        player.resetScore();
        player.setX((GamePanel.WIDTH / 2)
                - ((playerimage.getWidth())/10) / 2);
        newGameCreated = true;
    }
    // draw title and score
    public void drawText(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);

        //get the desired text size base on screen size
        paint.setTextSize(testTextSize);
        Rect bounds = new Rect();
        paint.getTextBounds(tittle, 0, tittle.length(), bounds);
        float desiredTextSize = testTextSize * (GamePanel.WIDTH - 10) / bounds.width();

        paint.setTextSize(desiredTextSize/3);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        canvas.drawText("DISTANCE: " + new DecimalFormat("##.##").format(player.getScore()),
                10, HEIGHT - desiredTextSize/3, paint);
        canvas.drawText("BEST: " + new DecimalFormat("##.##").format(best), WIDTH -
                paint.measureText("BEST: " + best) - 10, HEIGHT - desiredTextSize/3, paint);
        if (loading) {
            Paint paint1 = new Paint();
            paint1.setColor(Color.GREEN);
            paint1.setTextSize(desiredTextSize);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            paint1.setTextSize(desiredTextSize / 2);
            canvas.drawText("Loading...", WIDTH / 2 - (paint1.measureText("Loading...") / 2),
                    HEIGHT / 2 + desiredTextSize, paint1);
        }
        if(!(player.getPlaying())&&newGameCreated&&reset)
        {
            loading = false;
            Paint paint1 = new Paint();
            paint1.setColor(Color.GREEN);
            paint1.setTextSize(desiredTextSize);
            paint1.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
            canvas.drawText("Give Me Good Grade",
                    WIDTH / 2 - (paint1.measureText("Give Me Good Grade") / 2),
                    HEIGHT / 2 - desiredTextSize, paint1);
            canvas.drawText("Please?", WIDTH / 2 - (paint1.measureText("Please?") / 2),
                    HEIGHT / 2, paint1);


            paint1.setTextSize(desiredTextSize/2);
            canvas.drawText("PRESS ANYWHERE TO START", WIDTH / 2
                            - (paint1.measureText("PRESS ANYWHERE TO START") / 2),
                    HEIGHT / 2 + desiredTextSize , paint1);
        }
    }

    // store high score
    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences("key", Context.MODE_PRIVATE);
    }
    public static float getbest(Context context) {
        return getPrefs(context).getFloat("best", 0);
    }
    public static void setbest(Context context, float score) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putFloat("best", score);
        editor.commit();
    }


}


