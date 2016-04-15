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

	// Drawables and Bitmaps
	private Bitmap grassbg1;
	private Bitmap helicopter;
	private Bitmap missile;

	//public static final int WIDTH = 800;
	//public static final int HEIGHT = 480;
	private MainThread thread;

	public GamePanel(Context context, int x, int y)
	{

		super(context);

		//add callback to surfaceholder to intercept events
		getHolder().addCallback(this);

		thread = new MainThread(getHolder(), this);

		screenX = x; // width of screen
		screenY = y; // height of screen

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

		//Set up user interface
/*		playInterface = new UserInterface(getContext());

		player = new Player(BitmapFactory.decodeResource(getResources(), R.drawable.helicopter), 98, 40, 3);

		grassbg1 = BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1);


		int bgWidth = grassbg1.getWidth();
		grassbg1 = Bitmap.createScaledBitmap(grassbg1, bgWidth, screenY, false); // rescale background to for screen

		helicopter = BitmapFactory.decodeResource(getResources(), R.drawable.helicopter);
		helicopter = Bitmap.createScaledBitmap(helicopter, 600, 115, false);


		missile = BitmapFactory.decodeResource(getResources(), R.drawable.missile);
		missile = Bitmap.createScaledBitmap(missile, 150, 655, false);

		bg = new Background(grassbg1); //create new background using newly scaled bitmap
		player = new Player(helicopter, 195, 100, 3);
		projectile = new Projectile(missile, 150, 50, 13);


		testEnemy1 = new Enemy(70, 70);
		testEnemy2 = new Enemy(200, 300);
		testEnemy3 = new Enemy(70, 800);

*/
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
/*
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			//Might want to do some stuff if the person pressed down. Not sure.
			if (playInterface.isPlayPressed(event.getX(), event.getY()))
			{

			}
			if (playInterface.isFirePressed(event.getX(), event.getY()))
			{
				System.out.println("Touched");

			}

			player.setUp(true);
			return true;
		}

		if (event.getAction() == MotionEvent.ACTION_UP)
		{
			if (!player.getPlaying() && playInterface.isPlayPressed(event.getX(), event.getY()))
			{
				player.setPlaying(true);
				playInterface.removePlayButton();
			}
			player.setUp(false);
			playInterface.resetFireButton();
			return true;
		}
*/
		//return super.onTouchEvent(event);
		//Note, we need to return true for this to work, super.onTouchEvent is returning false for some reason
		//(returning false causes issues with not picking up subsequent touch events)
		return true;
	}

	public void update()
	{
		//TODO: get actual delta time. (time since last frame)
		float delta = 1.0f/30.0f;//this is assuming exactly 60 fps
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
		/*if (player.getPlaying())
		{
			bg.update();
			player.update();
			projectile.update();
			testEnemy1.update();
			testEnemy2.update();
			testEnemy3.update();
			playInterface.update();
		}*/
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
				canvas.drawColor(Color.RED);
				mainMenu.render(canvas);
				break;
			case STATE_GAME:
				//Drawing background as yellow to test
				canvas.drawColor(Color.YELLOW);
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

/*		//While we mock up the look of the game, this section is going to be
		// a clusterfuck
		//TODO: refactor all of these individual objects into classes
		bg.draw(canvas);
		//Set the background to black
		canvas.drawColor(Color.BLACK);

		player.draw(canvas);
		testEnemy1.draw(canvas);
		testEnemy2.draw(canvas);
		testEnemy3.draw(canvas);

		Paint p = new Paint();
		p.setARGB(255, 104, 104, 104);
		canvas.drawRect(0, 0, 30, screenY, p);
		canvas.drawRect(screenX - 30, 0, screenX, screenY, p);

		projectile.draw(canvas);
		playInterface.draw(canvas);
*/		canvas.restoreToCount(savedState);
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
