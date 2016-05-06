package com.sierra_psec.goadventure;

/**
 * Created by E6410 on 4/24/2016.
 */
public class BoundingBox
{

	public Vector2 topLeft;
	public Vector2 botRight;

	public Vector2 topRight;
	public Vector2 botLeft;

	//center of the button (used as a shortcut for positioning)
	public Vector2 center;
	//x and y Size (used as a shortcut for positioning)
	public Vector2 size;
	//Half of the size vector (used as a shortcut for positioning)
	public Vector2 halfSize;

	BoundingBox(Vector2 min, Vector2 max)
	{
		topLeft = new Vector2(min);
		botRight = new Vector2(max);
		topRight = new Vector2(max.x, min.y);
		botLeft = new Vector2(min.x, max.y);

		size = new Vector2(botRight.x - topLeft.x, botRight.y - topLeft.y);
		halfSize = Vector2.mul(size, 0.5f);
		center = new Vector2(topLeft.x + halfSize.x, topLeft.y + halfSize.y);
	}

	BoundingBox(Vector2 pos, float width, float height)
	{
		topLeft = new Vector2(pos);
		botRight = Vector2.add(pos, new Vector2(width, height));
		topRight = new Vector2(pos.x + width, pos.y);
		botLeft = new Vector2(pos.x, pos.y + height);

		size = new Vector2(botRight.x - topLeft.x, botRight.y - topLeft.y);
		halfSize = Vector2.mul(size, 0.5f);
		center = new Vector2(topLeft.x + halfSize.x, topLeft.y + halfSize.y);
	}

	public boolean isColliding(Vector2 pos) //TODO: make sure this works correctly, might just use ands
	{
		if (pos.x < topLeft.x || pos.x > botRight.x || pos.y < topLeft.y || pos.y > botRight.y)
			return false;

		return true;
	}

	public boolean isColliding(BoundingBox box)
	{
		//First checking if this object's corners are in the other box
		if (botLeft.x > box.topLeft.x && botLeft.x < box.topRight.x && botLeft.y > box.topLeft.y && botLeft.y < box.botLeft.y)
			return true;
		//Bottom right corner
		if (botRight.x > box.topLeft.x && botRight.x < box.topRight.x && botRight.y > box.topLeft.y && botRight.y < box.botLeft.y)
			return true;
		//Top right corner
		if (topRight.x > box.topLeft.x && topRight.x < box.topRight.x && topRight.y > box.topLeft.y && topRight.y < box.botLeft.y)
			return true;
		//Top left corner
		if (topLeft.x > box.topLeft.x && topLeft.x < box.topRight.x && topLeft.y > box.topLeft.y && topLeft.y < box.botLeft.y)
			return true;

		//Second checking if the other's objects corners are in our box
		//Bottom left corner
		if(box.botLeft.x > topLeft.x && box.botLeft.x < topRight.x && box.botLeft.y > topLeft.y && box.botLeft.y < botLeft.y)
			return true;
		//Bottom right corner
		if(box.botRight.x > topLeft.x && box.botRight.x < topRight.x && box.botRight.y > topLeft.y && box.botRight.y < botLeft.y)
			return true;
		//Top right corner
		if(box.topRight.x > topLeft.x && box.topRight.x < topRight.x && box.topRight.y > topLeft.y && box.topRight.y < botLeft.y)
			return true;
		//Top left corner
		if(box.topLeft.x > topLeft.x && box.topLeft.x < topRight.x && box.topLeft.y > topLeft.y && box.topLeft.y < botLeft.y)
			return true;
		//No collision
		return false;
	}

	public boolean isColliding(BoundingTri tri)
	{
		//since only the player will be colliding with prisms, we're only concerned with the top left and right corners of the box
		boolean ret = false;
		if (!tri.isRight)
		{
			if (topRight.x > tri.LRvertex.x && topRight.y < tri.botVertex.y && topRight.y > tri.LRvertex.y) {
				if (((topRight.y - tri.LRvertex.y) / (topRight.x - tri.LRvertex.x)) < 1.0f)
					ret = true;
			}
		}
		else
		{ //right facing triangle

			if (topLeft.x < tri.LRvertex.x && topLeft.y > tri.LRvertex.y) {
				if (((topLeft.y - tri.LRvertex.y) / (tri.LRvertex.x - topLeft.x)) < 1.0f)
					ret = true;
			}
		}
		return ret;
	}

	public void updateXPos(float delta)
	{
		topLeft.x += delta;
		topRight.x += delta;
		botLeft.x += delta;
		botRight.x += delta;
		center.x += delta;
	}

	public void updateYPos(float delta)
	{
		topLeft.y += delta;
		topRight.y += delta;
		botLeft.y += delta;
		botRight.y += delta;
		center.y += delta;
	}

	public void resetYPos(float delta, float height)
	{
		topLeft.y = delta;
		topRight.y = delta;
		botLeft.y = delta + height;
		botRight.y = delta + height;
		center.y = delta + (height/2);
	}

}
