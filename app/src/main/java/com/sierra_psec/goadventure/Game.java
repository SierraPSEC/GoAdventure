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
	static float playerSpeed = 0.75f; //75% of the screen per second

	//arrays of the obstacles the player will encounter
	Block[] blocks;
	Filter[] filters;
	Prism[] prisms;

	Block block1;
	Filter filter1, filter2, filter3;
	Prism prism1, prism2;


	public boolean initialize()
	{
		blocks = new Block[1];
		filters = new Filter[3];
		prisms = new Prism[2];

		//Place this button in the top right corner
		Vector2 pos = new Vector2(0.55f, 0.04f);
		pauseButton = new Button(pos, 0.35f, 0.125f);
		pos = new Vector2(0, 0);
		leftBorder = new BoundingBox(pos, 0.1f, 1.0f);
		pos = new Vector2((0.9f), 0); //9/10s of the way across the top of the screen
		rightBorder = new BoundingBox(pos, 0.1f, 1.0f);
		pos = new Vector2((0.5f), (0.75f));
		player = new Player(pos);

		pos = new Vector2((0.4f), (-0.2f));
		block1= new Block(pos, 0.2f, 0.2f);

		pos = new Vector2((0.1f), (-0.4f));
		filter1 = new Filter(pos, 0.15f, 0.15f, 0);
		pos = new Vector2((0.7f), (-0.4f));
		filter2 = new Filter(pos, 0.15f, 0.15f, 1);
		pos = new Vector2((0.4f), (-0.4f));
		filter3 = new Filter(pos, 0.15f, 0.15f, 2);

		pos = new Vector2((0.1f), (-0.3f));
		prism1 = new Prism(pos, true);
		pos = new Vector2((0.9f), (-0.3f));
		prism2 = new Prism(pos, false);

		blocks[0] = block1;
		filters[0] = filter1;
		filters[1] = filter2;
		filters[2] = filter3;
		prisms[0] = prism1;
		prisms[1] = prism2;
		return true;
	}

	public void handleInput(MotionEvent event)
	{
		int touchID = event.getActionIndex();
		int action = event.getAction();
		Vector2 pos = new Vector2(event.getX() / GamePanel.screenX, event.getY() / GamePanel.screenY);

		//Check for new touch begin events
		if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_POINTER_DOWN)
		{
			if (pauseButton.isInside(pos))
			{
				pauseButton.touchID = touchID;
				return;
			}
			else {
				player.vel.x *= -1;
			}

		}
		//Checking for touch release events
		//Don't perform the button's action until they release the finger on the button they pushed.
		if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_POINTER_UP)
		{
			//Checking if this touch event began on the pause button
			if (pauseButton.touchID == touchID)
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
		//updating player vs borders
		if (player.bbox.isColliding(leftBorder) && player.vel.x < 0)
		{
			System.out.println("Colliding with left border.\n");
			player.vel.x *= -1;
		}
		if (player.bbox.isColliding(rightBorder) && player.vel.x > 0)
		{
			System.out.println("Colliding with right border.\n");
			player.vel.x *= -1;
		}
		//updating blocks
		for (int i = 0; i < blocks.length; i++) {
			if (player.bbox.isColliding(blocks[i].bbox)) {
				blocks[i].onCollide();
				player.resetColor();//temp color behavior for demo purposes
			}

			if (blocks[i].bbox.topLeft.y > 1.0f) {//box just disappeared off bottom of screen
				blocks[i].resetPos(-0.2f); //TODO: figure out a prescreen start y pos for game objects
			}
			blocks[i].pos.y += (delta * playerSpeed);
			blocks[i].updatePos(delta * playerSpeed);
		}
		//updating filters
		for (int i = 0; i < filters.length; i++) {
			if (player.bbox.isColliding(filters[i].bbox)) {
				filters[i].onCollide();
				player.changeColor(filters[i].color);
			}

			if (filters[i].bbox.topLeft.y > 1.0f) {
				filters[i].resetPos(-0.4f);
			}
			filters[i].pos.y += (delta * playerSpeed);
			filters[i].updatePos(delta * playerSpeed);
		}

		//update prisms
		for (int i = 0; i < prisms.length; i++) {
			if (player.bbox.isColliding(prisms[i].btri)) {
				prisms[i].onCollide();
				player.resetColor();
			}

			if (prisms[i].btri.topVertex.y > 1.0f) {
				prisms[i].resetPos(-0.4f);
			}
			prisms[i].pos.y += (delta * playerSpeed);
			prisms[i].updatePos(delta * playerSpeed);
		}

		player.pos.x += (delta * player.vel.x);
		player.updatePos(delta * player.vel.x);
	}

	public void render(Canvas canvas)
	{
		Paint p = new Paint();
		//TODO: a lot
		//Draw the background
		//Draw the player
		player.draw(canvas);

		//Draw other obstacles
		for (int i = 0;i < blocks.length;i++) {
			blocks[i].draw(canvas);
		}
		for (int i = 0;i < filters.length;i++) {
			filters[i].draw(canvas);
		}
		for (int i = 0;i < prisms.length;i++) {
			prisms[i].draw(canvas);
		}

		//Draw the side wall borders
		p.setARGB(255, 60, 60, 60);
		canvas.drawRect(leftBorder.topLeft.x * GamePanel.screenX,
			   leftBorder.topLeft.y * GamePanel.screenY,
			   leftBorder.botRight.x * GamePanel.screenX,
			   leftBorder.botRight.y * GamePanel.screenY, p);

		canvas.drawRect(rightBorder.topLeft.x * GamePanel.screenX,
			   rightBorder.topLeft.y * GamePanel.screenY,
			   rightBorder.botRight.x * GamePanel.screenX,
			   rightBorder.botRight.y * GamePanel.screenY, p);

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
