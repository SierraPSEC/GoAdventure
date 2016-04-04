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

	//Attack Button
	private Bitmap attackButton, attackDown, attackUp;
	private int attackButtonX, attackButtonY;
	//Play Button
	private Bitmap playButton, playDown, playUp;
	private int playButtonX, playButtonY;
	private boolean drawPlayButton = true;



	private Context context;


	public UserInterface(Context context){
		this.context = context;
		setupResources();
	}

	/**
	 * Sets up the resources for the button.
	 */
	private void setupResources(){
		//*******************************************//
		//**********SET UP ATTACK BUTTON*************//
		//*******************************************//
		attackButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.attackup);
		attackDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.attackdown);
		//Scale bitmaps
		attackDown = Bitmap.createScaledBitmap(attackDown, 200,200, false);
		attackButton = Bitmap.createScaledBitmap(attackButton, 200, 200, false);
		attackUp = attackButton;
		attackButtonX=800;
		attackButtonY=1500;

		//******************************************//
		//************SET UP PLAY BUTTON************//
		//******************************************//
		playButton = BitmapFactory.decodeResource(context.getResources(), R.drawable.playup);
		playDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.playdown);
		//Scale bitmaps
		playButton = Bitmap.createScaledBitmap(playButton, 200, 200, false);
		playDown = Bitmap.createScaledBitmap(playDown, 200, 200, false);
		playUp = playButton;
		playButtonX=500;
		playButtonY=500;





	}

	/**
	 * Checks to see if the area where the button is occupied is pressed or not.
	 * @param touchX The x Position of the touch
	 * @param touchY The y position of the touch
	 * @return If it's within range or not.
	 */
	public boolean isFirePressed(float touchX, float touchY) {
		if (touchX >= attackButtonX && touchX < (attackButtonX + attackButton.getWidth())
				&& touchY >= attackButtonY && touchY < (attackButtonY + attackButton.getHeight())) {
			attackButton = attackDown;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Checks to see if the play button was pressed.
	 * @param touchX
	 * @param touchY
	 * @return
	 */
	public boolean isPlayPressed(float touchX, float touchY){
		if (touchX >= playButtonX && touchX < (playButtonX + playButton.getWidth())
				&& touchY >= playButtonY && touchY < (playButtonY + playButton.getHeight())
				&& drawPlayButton == true) {
			playButton = playDown;
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

	/**
	 * Removes the play button. For pressing.
	 */
	public void removePlayButton(){
		drawPlayButton=false;
	}

	public void update(){

	}
	public void draw(Canvas canvas){
		if(drawPlayButton == true) {
			canvas.drawBitmap(playButton, playButtonX, playButtonY, null);
		}
		canvas.drawBitmap(attackButton,attackButtonX,attackButtonY,null);
	}

}
