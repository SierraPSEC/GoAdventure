package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by E6410 on 4/28/2016.
 */
public class Filter {

    public int color; //0=red, 1=green, 2=blue
    public BoundingBox bbox;
    public Vector2 pos; //starting position for the block: x-variable up to either side wall, y-starts at constant, goes down w/ movement
    public Vector2 initPos; //starting position for block, never gets updated, used as starting reference
    public Vector2 wh; //width and height of block (might not always be a square, who knows) x=width, y=height

    Filter(Vector2 position, float width, float height, int c)
    {
        bbox = new BoundingBox(position, width, height * ((float)GamePanel.screenX / (float)GamePanel.screenY));
        pos = new Vector2(position);
        initPos = new Vector2(position);
        wh = new Vector2(width, height * ((float)GamePanel.screenX / (float)GamePanel.screenY));
        color = c;
    }

    public void onCollide()
    {
        //do something to the player after its bbox registers a collision
        System.out.println("player collided with a filter");

    }

    public void updatePos(float delta) {
        bbox.updateYPos(delta);
    }
    public void resetPos(float delta)
    {
        pos.y = initPos.y;
        bbox.resetYPos(delta, wh.y);
    }

    public void draw(Canvas canvas)
    {
        Paint p = new Paint();
        switch(color)
        {
            case 0:
                p.setARGB(155, 255, 0, 0);
                break;
            case 1:
                p.setARGB(155, 0, 255, 0);
                break;
            case 2:
                p.setARGB(155, 0, 0, 255);
                break;
        }
        canvas.drawRect(bbox.topLeft.x * GamePanel.screenX,
                bbox.topLeft.y * GamePanel.screenY,
                bbox.botRight.x * GamePanel.screenX,
                bbox.botRight.y * GamePanel.screenY, p);
    }
}
