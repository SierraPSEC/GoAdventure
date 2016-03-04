package com.sierra_psec.goadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Jonathan on 3/3/2016.
 */
public class Projectile
{
    private Bitmap spriteSheet;
    private Animation animation = new Animation();
    private long startTime;
    int height;
    int width;
    int dx;
    int projectileX;
    int projectileY;

    public Projectile(Bitmap res, int w, int h, int numFrames)
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

        projectileX += dx*3;
        if((projectileX)>(GamePanel.screenX)){
            projectileX=0;
        }

    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(),projectileX,Player.playerY,null);
    }

}

