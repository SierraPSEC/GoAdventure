package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by E6410 on 5/5/2016.
 *
 * alternate method for drawing the player trails
 *
 * on game start: player initial pos (Vec2) into array
 * on player touch: log coords of player, add coord(Vector2 object) to an array
 * on update: log current player pos, update all coord points of trail down by playerspeed, draw lines from each new coord connecting to player
 * drawing: use paths inside each other (different paint transparency) to create ray shading or:
 * http://developer.android.com/reference/android/graphics/LinearGradient.html use gradients
 * on corners: use diamond paths to connect corner squares
 *
 */
public class Trail {
    private final float ROOT_TWO = 1.41421f;
    private final float ASP = 0.02f*((float)GamePanel.screenX / (float)GamePanel.screenY);
    public Vector2 left, right; //current players position (y is constant)
    public Vector2[] lefts, rights;
    public int color;
    public int count; //might need to start at zero
    public boolean isRight;

    Trail(Vector2 pos) {
        //construct with same vector2 as player center pos on game start
        lefts = new Vector2[90];
        rights = new Vector2[90];
        left = new Vector2(pos.x-0.02f, pos.y-ASP);
        right = new Vector2(pos.x+0.02f, pos.y+ASP);
        lefts[0] = left;
        rights[0] = right;//current pos
        lefts[1] = left;
        rights[1] = right;//starting pos
        rights[1].x -= 0.01657f;
        rights[1].y -= 0.01657f*((float)GamePanel.screenX / (float)GamePanel.screenY);
        lefts[1].x += 0.01657f;
        lefts[1].y += 0.01657f*((float)GamePanel.screenX / (float)GamePanel.screenY);
        count = 1;
        color = 0;//white trail
        isRight = false;//we start out moving right (positive playerspeed)
        onDirectionChange(pos, color);
    }

    public void onDirectionChange(Vector2 pos, int c) { //pos is current player position(center point), c is current player color
        System.out.println("player travelling right before hit? " + isRight +"\ncount before hit: "+ count);
        for (int i = (count-1); i >= 0; i--){
            lefts[i+1] = lefts[i];//move all elements in the array to the right by one, so [0] can be player pos
            rights[i+1] = rights[i];
        }

        if (isRight) { //hit something while going right, is about to turn left
            //TODO: fix the inside corners for both directions, they seem to be too much inside
            rights[1].x += 0.02f;
            rights[1].y -= 0.02f*((float)GamePanel.screenX / (float)GamePanel.screenY);
            lefts[1].x -= 0.02f;
            lefts[1].y += 0.02f*((float)GamePanel.screenX / (float)GamePanel.screenY);
            left = new Vector2(pos.x-0.02f, pos.y+ASP);
            right = new Vector2(pos.x+0.02f, pos.y-ASP);
        }
        else {//hit something going left, is about to turn right
            rights[1].x += 0.02f;
            rights[1].y += 0.02f*((float)GamePanel.screenX / (float)GamePanel.screenY);
            lefts[1].x -= 0.02f;
            lefts[1].y -= 0.02f*((float)GamePanel.screenX / (float)GamePanel.screenY);
            left = new Vector2(pos.x-0.02f, pos.y-ASP);
            right = new Vector2(pos.x+0.02f, pos.y+ASP);
        }

        lefts[0] = left;
        rights[0] = right;
        count++;
        color = c;
        if (isRight)isRight = false;
        else isRight = true;
        System.out.println("CHANGING DIR, count=  " + count);
    }

    public void update(float delta) {
        int temp = count;
       // System.out.println("UPDATING, count=  " + count);
        for (int i = 1; i < temp; i++) {
            if (lefts[i].y > 1.75f && rights[i].y > 1.75f) {
                count = count -1;
               // System.out.println("removing coord at index " + i);
            }
        }
        for (int i = 1; i < count; i++) {
            lefts[i].y += delta;
            rights[i].y += delta;
           // System.out.println(lefts[i].x+","+lefts[i].y + "\ncount: " +count +"index"+i);
        }
        if (isRight) {
            lefts[0].x += delta*GamePanel.ASPECT_RATIO;
            rights[0].x += delta*GamePanel.ASPECT_RATIO;
        }
        else {
            lefts[0].x -= delta*GamePanel.ASPECT_RATIO;
            rights[0].x -= delta*GamePanel.ASPECT_RATIO;
        }
    }

    public void changeColor(int c) {
        color = c;
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setStrokeWidth(2);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setAntiAlias(true);
                switch(color)
                {
                    case 0:
                        p.setARGB(255, 255, 255, 255);
                        break;
                    case 1:
                        p.setARGB(255, 255, 0, 0);
                        break;
                    case 2:
                        p.setARGB(255, 0, 255, 0);
                        break;
                    case 3:
                        p.setARGB(255, 0, 0, 255);
                        break;
                    case 4:
                        p.setARGB(255, 255, 255, 0);
                        break;
                    case 5:
                        p.setARGB(255, 0, 255, 255);
                break;
            case 6:
                p.setARGB(255, 125, 5, 240);
                break;
        }

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(rights[0].x*GamePanel.screenX, rights[0].y*GamePanel.screenY);

        for (int i = 1; i < count; i++) {
            if (rights[i] != null) path.lineTo(rights[i].x*GamePanel.screenX, rights[i].y*GamePanel.screenY);
        }
        for (int i = count-1; i >= 0; i--) {
            if (lefts[i] != null) path.lineTo(lefts[i].x*GamePanel.screenX, lefts[i].y*GamePanel.screenY);
        }
        path.lineTo(rights[0].x*GamePanel.screenX, rights[0].y*GamePanel.screenY);

        canvas.drawPath(path, p);
    }
}