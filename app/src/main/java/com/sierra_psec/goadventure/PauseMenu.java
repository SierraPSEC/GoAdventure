package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * Created by Luis on 4/14/2016.
 */
public class PauseMenu implements Scene
{
	//Resumes the game
	Button resumeButton;
	//Restarts the game
	Button restartButton;
	//Exits to the main menu
	Button quitButton;

	public boolean initialize()
	{
		//Place these buttons in the center
		Vector2 pos = new Vector2(0.1f,0.1f);
		resumeButton = new Button(pos,0.8f,0.2f);

		pos.x = 0.1f;
		pos.y = 0.4f;
		restartButton = new Button(pos,0.8f,0.2f);

		pos.x = 0.1f;
		pos.y = 0.7f;
		quitButton = new Button(pos,0.8f,0.2f);
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
			if(resumeButton.isInside(pos))
			{
				resumeButton.touchID = touchID;
				return;
			}
			if(restartButton.isInside(pos))
			{
				restartButton.touchID = touchID;
				return;
			}
			if(quitButton.isInside(pos))
			{
				quitButton.touchID = touchID;
				return;
			}
		}
		//Checking for touch release events
		//Don't perform the button's action until they release the finger on the button they pushed.
		if(action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)
		{
			//Checking if this touch event began on the resume button
			if(resumeButton.touchID == touchID)
			{
				//Make sure the touch event is still inside the button
				if (resumeButton.isInside(pos))
				{
					GamePanel.resumeGame();
					//Clear the other buttons
					restartButton.touchID = -1;
					quitButton.touchID = -1;
				}
				//We are no longer touching this button
				resumeButton.touchID = -1;
				return;
			}

			//Checking if this touch event began on the restart button
			if(restartButton.touchID == touchID)
			{
				//Make sure the touch event is still inside the button
				if (restartButton.isInside(pos))
				{
					GamePanel.startGame();
					//Clear the other buttons
					resumeButton.touchID = -1;
					quitButton.touchID = -1;
				}
				//We are no longer touching this button
				restartButton.touchID = -1;
				return;
			}

			//Checking if this touch event began on the quit button
			if(quitButton.touchID == touchID)
			{
				//Make sure the touch event is still inside the button
				if (quitButton.isInside(pos))
				{
					GamePanel.stopGame();
					//Clear the other buttons
					resumeButton.touchID = -1;
					restartButton.touchID = -1;
				}
				//We are no longer touching this button
				quitButton.touchID = -1;
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
		//Test drawing of buttons (green boxes for now)
		Paint p = new Paint();
		//=========================== Drawing Resume Button ========================
		p.setARGB(255, 20, 240, 20);
		canvas.drawRect(resumeButton.mins.x * GamePanel.screenX,
			   resumeButton.mins.y * GamePanel.screenY,
			   resumeButton.maxs.x * GamePanel.screenX,
			   resumeButton.maxs.y * GamePanel.screenY, p);

		p.setTextSize(resumeButton.size.y *0.5f* GamePanel.screenY);//sets height in pixels
		p.setARGB(255, 0, 0, 0);
		//Text is located from bottom left
		canvas.drawText("Resume", (resumeButton.center.x - 0.32f) * GamePanel.screenX,
			   (resumeButton.center.y + 0.03f) * GamePanel.screenY, p);
		//==========================================================================

		//=========================== Drawing Restart Button ========================
		p.setARGB(255, 20, 240, 20);
		canvas.drawRect(restartButton.mins.x * GamePanel.screenX,
			   restartButton.mins.y * GamePanel.screenY,
			   restartButton.maxs.x * GamePanel.screenX,
			   restartButton.maxs.y * GamePanel.screenY, p);

		p.setTextSize(restartButton.size.y * 0.5f * GamePanel.screenY);//sets height in pixels
		p.setARGB(255, 0, 0, 0);
		//Text is located from bottom left
		canvas.drawText("Restart", (restartButton.center.x - 0.28f) * GamePanel.screenX,
			   (restartButton.center.y + 0.03f) * GamePanel.screenY, p);
		//============================================================================

		//=========================== Drawing Quit Button ========================
		p.setARGB(255, 20, 240, 20);
		canvas.drawRect(quitButton.mins.x * GamePanel.screenX,
			   quitButton.mins.y * GamePanel.screenY,
			   quitButton.maxs.x * GamePanel.screenX,
			   quitButton.maxs.y * GamePanel.screenY, p);

		p.setTextSize(quitButton.size.y * 0.5f * GamePanel.screenY);//sets height in pixels
		p.setARGB(255, 0, 0, 0);
		//Text is located from bottom left
		canvas.drawText("Quit", (quitButton.center.x - 0.18f) * GamePanel.screenX,
			   (quitButton.center.y + 0.03f) * GamePanel.screenY, p);
		//========================================================================


	}
}
