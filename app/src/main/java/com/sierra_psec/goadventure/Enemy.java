package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;

/**
 * Created by Luis on 3/10/2016.
 */
public class Enemy extends GameObject
{
    //TODO: place this in a transform matrix (if complexity calls for it) (debatable)
    //TODO: make this an int which scales from 0-1000 or whatever res we need (int math >> floating point math) (debatable)
    protected Point[] mesh = {new Point(-10,-10),new Point(0,4),new Point(10,-10),new Point(10,10),new Point(0,14) ,new Point(-10,10)};

    protected float accelerationTowardsPlayer;

    //In local units, as in x: our speed to our local right hand side, y: our speed forward
    protected Vector2 ddRLocal;//right acceleration, forward acceleration
    protected Vector2 dR;
    protected float rotation;//In degrees
    protected float velDamping;

    public Enemy(int posX, int posY)
    {
        this.x = posX;
        this.y = posY;
        width = 6;
        height = 6;
        accelerationTowardsPlayer = 4f;
        dR = new Vector2();
        ddRLocal = new Vector2(0,accelerationTowardsPlayer);
        velDamping = 0.95f;
    }

    public void update()
    {
        Vector2 pVec = new Vector2(Player.playerX,Player.playerY);

        Vector2 toPlayer = Vector2.normalize(Vector2.sub(pVec, new Vector2(this.x, this.y)));



        //For now just face player
        rotation = (float) Math.toDegrees(Vector2.vecToAngle(toPlayer)) + 90;

        //Calculating our local right and forward vectors.
        Vector2 right = Vector2.angleToVec(rotation);
        Vector2 forward = Vector2.angleToVec(rotation + (float)Math.PI/4f);

        //This will actually never happen because of arctan.
        /*while(rotation > 360)
            rotation -= 360;
        while(rotation < 0)
            rotation += 360;*/

        //Accelerating in the direction of the player
        dR = Vector2.add( dR,Vector2.add( Vector2.mul(right,ddRLocal.x), Vector2.mul(forward,ddRLocal.y) ));

        //Checking terminal velocity:
        float speed = Vector2.len(dR);
        if(speed > 100f)
        {
            dR = Vector2.mul(dR, 100f / speed);
        }
        dR = Vector2.mul(dR,velDamping);

        this.x += dR.x;//FIXME: should be based on frametime?
        this.y += dR.y;//FIXME: should be based on frametime? (i.e. denote speed in m/s,
                            // FIXME: but multiply speed by fraction of second that frame occurred over to get this frame's displacement
    }

    public void draw(Canvas canvas)
    {
        Path polygon = new Path();

        polygon.moveTo(this.x - width / 2 + width*mesh[0].x, this.y - height / 2 + height*mesh[0].y);
        for (int i = 1; i < mesh.length; i++) {
            polygon.lineTo(this.x - width/2 + width*mesh[i].x, this.y - height/2 + height*mesh[i].y);
        }
        Matrix rotationMatrix = new Matrix();
        //accepts rotation in degrees
        rotationMatrix.setRotate(rotation, this.x - 0.5f * width, this.y - 0.5f *height);
        //rotationMatrix.setScale(width,height);
        polygon.transform(rotationMatrix);

        Paint p = new Paint();
        p.setColor(Color.RED);

        canvas.drawPath(polygon,p);


    }
}
