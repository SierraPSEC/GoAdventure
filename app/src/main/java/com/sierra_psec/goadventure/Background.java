package com.sierra_psec.goadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by Melanos on 2/15/2016.
 */

public class Background {

    private Bitmap image;
     int bgWidth;
    int bgHeight;
    int scale;
    private int x, y, dx;

    public Background(Bitmap res){
        bgHeight = res.getHeight(); //height of background
        bgWidth=res.getWidth(); //  width of background
        scale = GamePanel.screenY/bgHeight; // background is shorter than screen height, so need to find by what factor

        image = res;
//        dx = GamePanel.MOVESPEED;
    }

    public void update(){
        x+=dx;
        if((x*-1)>(bgWidth)){
            x=0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,0, null);
       if((x*-1)>(bgWidth- GamePanel.screenX)){
           canvas.drawBitmap(image,(x+bgWidth),0,null);
      }
    }

}