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


//	private Projectile projectile;

//	private Player player;


	public boolean initialize()
	{
		//Place this button in the top right corner
		Vector2 pos = new Vector2(0.55f,0.04f);
		pauseButton = new Button(pos,0.35f,0.125f);
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

	}
	public void render(Canvas canvas)
	{
		//Draw the background
		//Draw the player
		//Draw other obstacles
		//Draw the side wall borders

		//Drawing the UI

		//Test drawing of button (green boxes for now)
		Paint p = new Paint();
		p.setARGB(255, 20, 240, 20);

		canvas.drawRect(pauseButton.mins.x*GamePanel.screenX,
			   pauseButton.mins.y*GamePanel.screenY,
			   pauseButton.maxs.x*GamePanel.screenX,
			   pauseButton.maxs.y*GamePanel.screenY,p);

		p.setTextSize(pauseButton.size.y *0.5f* GamePanel.screenY);//sets height in pixels
		p.setARGB(255, 0, 0, 0);
		//Text is located from bottom left
		canvas.drawText("Pause", (pauseButton.center.x - 0.15f) * GamePanel.screenX,
			   (pauseButton.center.y + 0.025f) * GamePanel.screenY, p);
	}
}
