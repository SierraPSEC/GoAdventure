package com.sierra_psec.goadventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.Paint;



/**
 * Created by Melanos on 2/15/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    // The size of the screen in pixels
    public static int screenX;
    public static int screenY;
    private Bitmap grassbg1;
    private Bitmap helicopter;
    private Bitmap missile;



    //public static final int WIDTH = 800;
    //public static final int HEIGHT = 480;
    public static final int MOVESPEED = -5;
    private MainThread thread;
    private Projectile projectile;
    private Background bg;
    private Player player;

    public GamePanel(Context context, int x, int y){

        super(context);

        //add callback to surfaceholder to intercept events
        getHolder().addCallback(this);

        thread= new MainThread(getHolder(), this);

        screenX = x; // width of screen
        screenY = y; // height of screen

        //make gamePanel focusable to handle events
        setFocusable(true);
    }

     @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){e.printStackTrace();}
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        player = new Player(BitmapFactory.decodeResource(getResources(),R.drawable.helicopter), 98, 40, 3);

        grassbg1 = BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1);


        int bgWidth=grassbg1.getWidth();
        grassbg1 = Bitmap.createScaledBitmap(grassbg1,bgWidth, screenY, false); // rescale background to for screen

        helicopter = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
        helicopter = Bitmap.createScaledBitmap(helicopter, 600, 115, false);



        missile = BitmapFactory.decodeResource(getResources(), R.drawable.missile);
        missile= Bitmap.createScaledBitmap(missile, 150, 655, false);

        bg = new Background(grassbg1); //create new background using newly scaled bitmap
        player = new Player(helicopter, 195, 100, 3);
        projectile =  new Projectile(missile,150,50,13);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(!player.getPlaying()) {
                player.setPlaying(true);
            }
            player.setUp(true);
            return true;
        }

        if(event.getAction()==MotionEvent.ACTION_UP){
            player.setUp(false);
            return true;
        }
        return super.onTouchEvent(event);
    }

    public void update(){
        if(player.getPlaying()){
            bg.update();
            player.update();
            projectile.update();
        }
    }

    @Override
    public void draw(Canvas canvas){
        final float scaleFactorX = getWidth()/(screenX*1.f);
        final float scaleFactorY = getHeight()/(screenY*1.f);
        if(canvas!=null) {
            final int savedState = canvas.save();
            canvas.scale(scaleFactorX,scaleFactorY);
            bg.draw(canvas);
            player.draw(canvas);
            projectile.draw(canvas);
            canvas.restoreToCount(savedState);
       }


        }
}
