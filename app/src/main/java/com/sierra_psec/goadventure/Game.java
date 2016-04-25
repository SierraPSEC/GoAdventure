package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Luis on 4/14/2016.
 */
public class Game implements Scene
{
	//Pauses the game and brings up the pause menu
	Button pauseButton;
	BoundingBox leftBorder;
	BoundingBox rightBorder;
    Player player;
	static double playerSpeed = 0.75 * GamePanel.screenY; //75% of the screen per second

//	private Projectile projectile;

//	private Player player;


	public boolean initialize()
	{
		//Place this button in the top right corner
		Vector2 pos = new Vector2(0.55f,0.04f);
		pauseButton = new Button(pos,0.35f,0.125f);
		pos = new Vector2(0,0);
		leftBorder = new BoundingBox(pos, GamePanel.screenX / 10, GamePanel.screenY);
        pos = new Vector2(((GamePanel.screenX / 10)*9), 0); //9/10s of the way across the top of the screen
        rightBorder = new BoundingBox(pos, GamePanel.screenX / 10, GamePanel.screenY);
        pos = new Vector2((GamePanel.screenX / 2), ((GamePanel.screenY / 4)*3));
        player = new Player(pos);
		return true;
	}
	public void handleInput(MotionEvent event)
	{
		int touchID = event.getActionIndex();
		int action = event.getAction();
		Vector2 pos = new Vector2(event.getX()/GamePanel.screenX, event.getY()/GamePanel.screenY);

		//Check for new touch begin events
		if(action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
		{
			if(pauseButton.isInside(pos))
			{
				pauseButton.touchID = touchID;
				return;
			}

		}
		//Checking for touch release events
		//Don't perform the button's action until they release the finger on the button they pushed.
		if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)
		{
			//Checking if this touch event began on the pause button
			if(pauseButton.touchID == touchID)
			{
				//Make sure the touch event is still inside the button
				if (pauseButton.isInside(pos))
				{
					GamePanel.pauseGame();
				}
				//We are no longer touching this button
				pauseButton.touchID = -1;
				return;
			}
		}
	}
	public void update(float delta)
	{
        //TODO: handle collisions, movement for now
        //if player collides w/ borders, x * -1, then update pos
        if (player.bbox.isColliding(leftBorder) || player.bbox.isColliding(rightBorder)) {
            player.pos.x *= (-1);
        }
       // player.pos.x += (delta * playerSpeed);
        player.updatePos(delta);
	}

    public void render(Canvas canvas)
    {
        Paint p = new Paint();
        //TODO: a lot
        //Draw the background
        //Draw the player
        p.setARGB(255, 250, 20, 20);
        canvas.drawRect(player.bbox.topLeft.x,
                player.bbox.topLeft.y,
                player.bbox.botRight.x,
                player.bbox.botRight.y, p);

        //Draw other obstacles


        //Draw the side wall borders
        p.setARGB(255, 60, 60, 60);
        canvas.drawRect(leftBorder.topLeft.x,
                leftBorder.topLeft.y,
                leftBorder.botRight.x,
                leftBorder.botRight.y, p);

        canvas.drawRect(rightBorder.topLeft.x,
                rightBorder.topLeft.y,
                rightBorder.botRight.x,
                rightBorder.botRight.y, p);

        //Drawing the UI

		//Test drawing of button (green boxes for now)

		p.setARGB(255, 20, 240, 20);

        canvas.drawRect(pauseButton.mins.x * GamePanel.screenX,
                pauseButton.mins.y * GamePanel.screenY,
                pauseButton.maxs.x * GamePanel.screenX,
                pauseButton.maxs.y * GamePanel.screenY, p);

		p.setTextSize(pauseButton.size.y * 0.5f * GamePanel.screenY);//sets height in pixels
		p.setARGB(255, 0, 0, 0);
		//Text is located from bottom left
		canvas.drawText("Pause", (pauseButton.center.x - 0.15f) * GamePanel.screenX,
                (pauseButton.center.y + 0.025f) * GamePanel.screenY, p);


	}
}
