package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by Luis on 4/14/2016.
 */
public class MainMenu implements Scene
{
	Button playButton;

	public boolean initialize()
	{
		//Place this button in the center
		Vector2 pos = new Vector2(0.1f, 0.4f);
		playButton = new Button(pos, 0.8f, 0.2f);
		return true;
	}

	public void handleInput(MotionEvent event)
	{
		//Need to run this function once per finger
		int touchID = event.getActionIndex();
		int action = event.getAction();

		Vector2 pos = new Vector2(event.getX() / GamePanel.screenX, event.getY() / GamePanel.screenY);


		//Check for new touch begin events
		if (action == MotionEvent.ACTION_DOWN)
		{
			if (playButton.isInside(pos))
			{
				playButton.touchID = touchID;
				return;
			}
		}
		//Checking for touch release events
		//Don't perform the button's action until they release the finger on the button they pushed.
		if (action == MotionEvent.ACTION_UP)
		{
			//Checking if this touch event began on the pause button
			if (playButton.touchID == touchID)
			{
				//Make sure the touch event is still inside the button
				if (playButton.isInside(pos))
				{
					GamePanel.startGame();
				}
				//We are no longer touching this button
				playButton.touchID = -1;
				return;
			}
		}
	}

	public void update(float delta)
	{
		//Nothing to do here, yet...
	}

	public void render(Canvas canvas)
	{
		//Drawing the play button TODO should this be a bitmap, or a polygon rendering?
		//Test drawing of button (green boxes for now)
		Paint p = new Paint();
		p.setARGB(255, 20, 240, 20);

		canvas.drawRect(playButton.mins.x * GamePanel.screenX,
			   playButton.mins.y * GamePanel.screenY,
			   playButton.maxs.x * GamePanel.screenX,
			   playButton.maxs.y * GamePanel.screenY, p);
		p.setTextSize(playButton.size.y * GamePanel.screenY);//sets height in pixels
		p.setARGB(255,0,0,0);
		//Text is located from bottom left
		canvas.drawText("Play",(playButton.center.x-0.35f) * GamePanel.screenX,
			   (playButton.center.y+0.05f)* GamePanel.screenY,p);
	}
}
