package com.sierra_psec.goadventure;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.util.Log;

/**
 * Created by Melanos on 2/22/2016.
 */
public class Player extends GameObject{
    private Bitmap spritesheet;
    private int score;
    private int height;
    private int width;
    //FIXME: you already have a x and y position variable from GameObject, why create a duplicate?
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
            image[i] = Bitmap.createBitmap(spritesheet, width*i, 0, width, height);
        }

        animation.setFrames(image);
        animation.setDelay(180);
        startTime = System.nanoTime();

        tracePositions = new Vector2[maxTraces];
        traceNormals = new Vector2[maxTraces];
        for(int i = 0; i < maxTraces; i++)
        {
            tracePositions[i] = new Vector2();
            traceNormals[i] = new Vector2();
        }
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

        //making the player move horizontally (to test tracing)
        playerX = 100 + 400 + (int)(400*Math.sin(System.nanoTime()*0.000000001));


    }

    Vector2 lastPos;
    Vector2 initialDirection;
    //using playerX and playerY as the current position.
    //need an array of the last positions
    //and an array of the last angles.
    //scrap angles, store an array of normals to the path at the point. (easily calculable)
    final int maxTraces = 30;//going to maintain up to 10 previous indices.
    int setTraces = 0;
    Vector2 tracePositions[];
    Vector2 traceNormals[];

	public void draw(Canvas canvas)
	{
		//TODO in future, make sure we initialize all variable elsewhere, this is hacky
		if(lastPos == null)
		{
			lastPos = new Vector2(playerX,playerY);
			return;//this could lead to issues (dividing by zero and all of that)
		}
		//Going to test drawing a movement trail
		canvas.drawBitmap(animation.getImage(),playerX,playerY,null);
		Vector2 currentDirection;
		//If we have not moveed, our current direction is right, (this is a hack, we need to resolve this using velocity):
		if(playerX == lastPos.x && playerY == lastPos.y)
		{
			//Just going to offset, this too is a hack (we should offset the lastPos using some other non-nonsensical
			// 												(did you know sensical isn't a word?) method)
			lastPos.x -= 0.1;
		}
		currentDirection = Vector2.normalize(new Vector2(playerX - lastPos.x, playerY - lastPos.y));

		//if condition for adding one is true:
		if(initialDirection == null || (Vector2.dot(currentDirection,initialDirection) < 0.995))
		{
			//Moving all of the trace positions up by 1.
			for (int i = maxTraces - 1; i > 0; i--)
			{
				tracePositions[i] = tracePositions[i - 1];
				traceNormals[i] = traceNormals[i - 1];
			}

			//We have added a new trace, so increment setTraces
			setTraces++;
			if (setTraces > maxTraces)
				setTraces = maxTraces;

			initialDirection = currentDirection;
		}
		//adding our current position to the traces.
		tracePositions[0] = new Vector2(playerX,playerY);
		//the normal to a slope is given by the negative reciprocal, so... flip the x and y, and make the x or y negative( I choose x)
		traceNormals[0] = Vector2.normalize(new Vector2(-1*(playerY-lastPos.y) , playerX - lastPos.x));



		//setting the last position
		lastPos = tracePositions[0];

		Paint p = new Paint();

		/*for(int i = 0; i < setTraces; i++)
		{
			//This test code will draw a line from the origin to each recorded position,
			// as well as draw the normal of each position at each position as a small green line.

			p.setARGB(255, 255, 0, 0);
			canvas.drawLine(0, 0, tracePositions[i].x, tracePositions[i].y, p);
			p.setARGB(255, 0, 255, 0);
			canvas.drawLine(tracePositions[i].x, tracePositions[i].y,
				   tracePositions[i].x + traceNormals[i].x * 50,
				   tracePositions[i].y + traceNormals[i].y * 50, p);

		}*/

		int largest_size = 40;
		int smallest_size = 4;
		int total_rays = 10;

		int rays_done = 0;
		//have draw them largest to smallest to ensure white gets drawn on top.
		for(int size = largest_size; size >= smallest_size; size-= ((largest_size - smallest_size)/(float)(total_rays-1)))
		{
			//Drawing the last two as white (give the lightsaber effect)
			if(total_rays - rays_done <= 2)
				p.setARGB(128,255,255,255);
			else
				p.setARGB(32,0,255,0);
			Path polygon = new Path();

			//Starting at first point
			polygon.moveTo(tracePositions[0].x + traceNormals[0].x * size, tracePositions[0].y + traceNormals[0].y * size);
			//Moving up through the line
			for (int i = 1; i < setTraces; i++)
				polygon.lineTo(tracePositions[i].x + traceNormals[i].x * size, tracePositions[i].y + traceNormals[i].y * size);
			//Moving back along line
			for (int i = setTraces - 1; i >= 0; i--)
				polygon.lineTo(tracePositions[i].x - traceNormals[i].x * size, tracePositions[i].y - traceNormals[i].y * size);
			//Moving back to start
			polygon.lineTo(tracePositions[0].x + traceNormals[0].x * size, tracePositions[0].y + traceNormals[0].y * size);


			canvas.drawPath(polygon, p);

			rays_done++;
		}

		//draw the points array
		//at first I'm just going to draw
		//I'm essentially creating a polygon utilizing the points of the array as well as the normals of the path
		//I'm creating 2 points, starting from the point, and moving up and the other down the normal direction.
		//this will create a box.
		//
	}

	public int getScore(){return score;}
	public boolean getPlaying(){return playing;}
	public void setPlaying(boolean b){playing = b;}
	public void resetDYA(){dy = 0;}
	public void resetScore(){score = 0;}

}

