package com.sierra_psec.goadventure;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by adamr_000 on 3/28/2016.
 */
public class UserInterface {

	private Bitmap attackButton;
	private Bitmap attackDown;
	private Bitmap attackUp;
	private Context context;

	private int screenX, screenY;

	public UserInterface(Context context, int screenX, int screenY){
		this.context = context;
		this.screenX = screenX;
		this.screenY = screenY;
		setupResources();
	}


	private void setupResources(){
		attackButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.attackup);
		attackDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.attackdown);
		//Scale bitmaps
		attackDown = Bitmap.createScaledBitmap(attackDown, 200,200, false);
		attackButton = Bitmap.createScaledBitmap(attackButton, 200, 200, false);
		attackUp = attackButton;
	}

	/**
	 * Checks to see if the area where the button is occupied is pressed or not.
	 * @param touchX The x Position of the touch
	 * @param touchY The y position of the touch
	 * @return If it's within range or not.
	 */
	public boolean isFirePressed(float touchX, float touchY) {
		if (touchX >= screenX / 2 && touchX < (screenX / 2 + attackButton.getWidth())
				&& touchY >= screenY / 2 && touchY < (screenY / 2 + attackButton.getHeight())) {
			attackButton = attackDown;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Resets the Bitmap to it's default for the Fire button.
	 */
	public void resetFireButton(){
		attackButton = attackUp;
	}

	public void update(){

	}
	public void draw(Canvas canvas){

		canvas.drawBitmap(attackButton,screenX/2,screenY/2,null);
	}

}
