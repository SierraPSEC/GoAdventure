package com.sierra_psec.goadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jonathan as Projectile.java
 * Copied by jjones on 3/3/2016.
 */
public class JetEnemy {
    private Bitmap spriteSheet;
    private Animation animation = new Animation();
    private long startTime;
    int height;
    int width;
    int dx;
    int enemyX;
    int projectileY=Player.playerY; // set missile first frame to be the same as helicopter

    public JetEnemy(Bitmap res, int w, int h, int numFrames)
    {
        dx = 1;
        height =h;
        width = w;

        Bitmap[] image = new Bitmap[numFrames]; // create new array with size = number of frames in animation
        spriteSheet = res; //set spritesheet = bitmap res

        for (int i = 0; i < image.length; i++)
        {
            image[i] = Bitmap.createBitmap(spriteSheet, 0, i*height, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(180);
        startTime = System.nanoTime();

    }

    public void update()
    {
        long elapsed = (System.nanoTime() - startTime) / 1000000;
        if (elapsed > 100)
        {
            startTime = System.nanoTime();
        }

        animation.update();

        //enemyXX += dx*5;
        //if((projectileX)>(GamePanel.screenX)){
       //     projectileX=5;
        //    projectileY=Player.playerY; // sets the location of the missle when resetting to be the same as the copter at that moment
       // }

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),enemyX,projectileY,null);
    }

}
