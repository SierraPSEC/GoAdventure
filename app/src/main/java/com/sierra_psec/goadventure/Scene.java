package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Luis on 4/14/2016.
 */
public interface Scene
{
	public boolean initialize();
	public void handleInput(MotionEvent event);
	public void update(float delta);
	public void render(Canvas canvas);
}
