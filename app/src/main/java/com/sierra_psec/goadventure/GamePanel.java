package com.sierra_psec.goadventure;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Created by Melanos on 2/15/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback
{
	//What state we are currently in.
	static int gameState = 1;
	public static float ASPECT_RATIO;
	//Constants for game state.
	static final int STATE_SPLASHSCREEN = 0;//TODO: implement this
	static final int STATE_MAINMENU = 1;
	static final int STATE_GAME = 2;
	static final int STATE_PAUSEMENU = 3;
	//... etc...

	//All Scenes in this application
	MainMenu mainMenu;
	Game game;
	PauseMenu pauseMenu;


	// The size of the screen in pixels
	public static int screenX;
	public static int screenY;

	private MainThread thread;

	public GamePanel(Context context, int x, int y)
	{

		super(context);

		//add callback to surfaceholder to intercept events
		getHolder().addCallback(this);

		thread = new MainThread(getHolder(), this);

		screenX = x; // width of screen
		screenY = y; // height of screen

		ASPECT_RATIO = ((float)screenY)/((float)screenX);

		//make gamePanel focusable to handle events
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		boolean retry = true;
		while (retry)
		{
			try
			{
				thread.setRunning(false);
				thread.join();
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			retry = false;
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		gameState = STATE_MAINMENU;
		mainMenu = new MainMenu();
		game = new Game();
		pauseMenu = new PauseMenu();

		mainMenu.initialize();
		game.initialize();
		pauseMenu.initialize();

		thread.setRunning(true);
		thread.start();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch(gameState)
		{
			case STATE_SPLASHSCREEN:
				//TODO
				break;
			case STATE_MAINMENU:
				mainMenu.handleInput(event);
				break;
			case STATE_GAME:
				game.handleInput(event);
				break;
			case STATE_PAUSEMENU:
				pauseMenu.handleInput(event);
				break;
			default:
				break;
		}
		//return super.onTouchEvent(event);
		//Note, we need to return true for this to work, super.onTouchEvent is returning false for some reason
		//(returning false causes issues with not picking up subsequent touch events)
		return true;
	}

	public void update()
	{
		//TODO: get actual delta time. (time since last frame)
		float delta = 1.0f/60.0f;//this is assuming exactly 60 fps
		//NOTE: looks like 30fps to me, we should research phone screen refresh rates, to
		// see if 30 is all we can get or if we can go up to 60 -Nick
		switch(gameState)
		{
			case STATE_SPLASHSCREEN:
				//TODO
				break;
			case STATE_MAINMENU:
				mainMenu.update(delta);
				break;
			case STATE_GAME:
				game.update(delta);
				break;
			case STATE_PAUSEMENU:
				pauseMenu.update(delta);
				break;
			default:
				break;
		}
	}

	@Override
	public void draw(Canvas canvas)
	{
		//Shut up compiler
		super.draw(canvas);

		final float scaleFactorX = getWidth() / (screenX * 1.f);
		final float scaleFactorY = getHeight() / (screenY * 1.f);
		if (canvas == null)
			return;

		final int savedState = canvas.save();
		canvas.scale(scaleFactorX, scaleFactorY);

		switch(gameState)
		{
			case STATE_SPLASHSCREEN:
				break;
			case STATE_MAINMENU:
				//Drawing background as red to test
				mainMenu.render(canvas);
				break;
			case STATE_GAME:
				//Drawing background as yellow to test
				canvas.drawColor(Color.BLACK);
				game.render(canvas);
				break;
			case STATE_PAUSEMENU:
				//Drawing background as blue to test
				canvas.drawColor(Color.BLUE);
				pauseMenu.render(canvas);
				break;
			default:
				break;
		}

		canvas.restoreToCount(savedState);
	}

	//===================== Static functions for buttons ==========================
	//Start the game if it has not been started
	//If the game is already running, restart the game
	public static void startGame()
	{
		gameState = STATE_GAME;
	}

	public static void stopGame()
	{
		gameState = STATE_MAINMENU;
	}

	public static void pauseGame()
	{
		gameState = STATE_PAUSEMENU;
	}

	public static void resumeGame()
	{
		gameState = STATE_GAME;
	}


}
