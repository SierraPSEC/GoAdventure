package com.sierra_psec.goadventure;

/**
 * Created by Luis on 4/14/2016.
 */
public class Button
{
	public Vector2 mins;
	public Vector2 maxs;

	//center of the button (used as a shortcut for positioning)
	public Vector2 center;
	//x and y Size (used as a shortcut for positioning)
	public Vector2 size;
	//Half of the size vector (used as a shortcut for positioning)
	public Vector2 halfSize;

	//ID for what finger pushed it.
	public int touchID = -1;

	Button(Vector2 min, Vector2 max)
	{
		mins = new Vector2(min);
		maxs = new Vector2(max);

		size = new Vector2(maxs.x - mins.x, maxs.y - mins.y);
		halfSize = Vector2.mul(size,0.5f);
		center = new Vector2(mins.x + halfSize.x, mins.y + halfSize.y);
	}

	Button(Vector2 pos, float width, float height)
	{
		mins = new Vector2(pos);
		maxs = Vector2.add(pos,new Vector2(width,height));

		size = new Vector2(maxs.x - mins.x, maxs.y - mins.y);
		halfSize = Vector2.mul(size, 0.5f);
		center = new Vector2(mins.x + halfSize.x, mins.y + halfSize.y);
	}

	public boolean isInside(Vector2 pos)
	{
		if(pos.x < mins.x || pos.x > maxs.x)
			return false;
		if(pos.y < mins.y || pos.y > maxs.y)
			return false;

		return true;
	}

	//TODO: if we decide on doing polygon based button drawing, add a general-purpose draw function here.
}
