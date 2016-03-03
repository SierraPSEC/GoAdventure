package com.sierra_psec.goadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;

/**
 * Created by Melanos on 2/22/2016.
 */
public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private int height;
    private int width;
    public static int playerX;
    public static int playerY;
    private boolean up;
    private boolean playing;
    private Animation animation = new Animation();
    private long startTime;

    public Player(Bitmap res, int w, int h, int numFrames){
        playerX = 100;
        playerY = GamePanel.screenY/4;
        dy = 0;
        score = 0;
         height=h;
         width = w;

        Bitmap[] image = new Bitmap[numFrames]; // create new array with size = number of frames in animation
        spritesheet = res; //set spritesheet = bitmap res

        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(spritesheet, i*width, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(180);
        startTime = System.nanoTime();
    }



    public void setUp(boolean b){up = b;}

    public void update(){
        long elapsed = (System.nanoTime()-startTime)/1000000;
        if(elapsed>100){
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if(up){
            dy -= 4; //if touching screen move up
        }
        else{
            dy += 1; // if not touching screen move down
        }

        if(dy>14)dy = 14;
        if(dy<-14)dy = -14;

        if (playerY<1){ // upper boundary
            dy = 1;
        }

        else if (playerY>GamePanel.screenY){ //Lower boundary. The Need to find way to remove navbar on bottom
            dy=-1;
        }

        playerY += dy*2;


    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(animation.getImage(),playerX,playerY,null);
    }

    public int getScore(){return score;}
    public boolean getPlaying(){return playing;}
    public void setPlaying(boolean b){playing = b;}
    public void resetDYA(){dy = 0;}
    public void resetScore(){score = 0;}

}

