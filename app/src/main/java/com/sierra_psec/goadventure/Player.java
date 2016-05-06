package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by E6410 on 4/24/2016.
 */
public class Player
{

	private final float WI = 0.1f; //TODO: figure out how large the hitbox for the player should be, these are temp vals, should be even num
	private final float HI = 0.1f * ((float)GamePanel.screenX / (float)GamePanel.screenY);


	public Vector2 pos; //y is fixed
	public Vector2 vel; //y will always be zero, only moving x -maybe have this be float instead then
	//public float velX; //TODO: how fast are we moving left and right?
	public BoundingBox bbox;
	public int color;

	/*player color key
	*0=white
	* 1=red
	* 2=green
	* 3=blue
	* 4=yellow
	* 5=teal/aquamarine
	* 6=violet
	 */


	Player(Vector2 position)
	{
		pos = new Vector2(position.x - (WI / 2), position.y);
		bbox = new BoundingBox(pos, WI, HI);
		color = 0; //start out white
		vel = new Vector2(Game.playerSpeed,0);
	}

	public void updatePos(float delta)
	{
		bbox.updateXPos(delta);
	}

	public void changeColor(int c)
	{
		switch(color){
			case 0://white
				if (c == 0) {//white losing red
					color = 5;
				}
				else if (c == 1) {//white losing green
					color = 6;
				}
				else {//white losing blue
					color = 4;
				}
				break;
			case 1://red
				if (c == 0) {
					//kill player
				}
				break;
			case 2://green
				if (c == 1) {
					//kill player
				}
				break;
			case 3://blue
				if (c == 2) {
					//kill player
				}
				break;
			case 4://yellow
				if (c == 0) {//losing red, becomes green
					color = 2;
				}
				else if (c == 1) {//losing green, becomes red
					color = 1;
				}
				break;
			case 5://teal/aqua
				if (c == 1) {//losing green, becomes blue
					color = 3;
				}
				else if (c == 2) {//losing blue, becomes green
					color = 2;
				}
				break;
			case 6://violet
				if (c == 0) {//losing red, becomes blue
					color = 3;
				}
				else if (c == 2) {//losing blue, becomes red;
					color = 1;
				}
				break;
		}
	}

	public void resetColor() {
		color = 0;
	}

	public void draw(Canvas canvas)
	{
		Paint p = new Paint();
		switch(color)
		{
			case 0:
				p.setARGB(255, 255, 255, 255);
				break;
			case 1:
				p.setARGB(255, 255, 0, 0);
				break;
			case 2:
				p.setARGB(255, 0, 255, 0);
				break;
			case 3:
				p.setARGB(255, 0, 0, 255);
				break;
			case 4:
				p.setARGB(255, 255, 255, 0);
				break;
			case 5:
				p.setARGB(255, 0, 255, 255);
				break;
			case 6:
				p.setARGB(255, 125, 5, 240);
				break;
		}
		canvas.drawRect(bbox.topLeft.x * GamePanel.screenX,
				bbox.topLeft.y * GamePanel.screenY,
				bbox.botRight.x * GamePanel.screenX,
				bbox.botRight.y * GamePanel.screenY, p);
	}
}
