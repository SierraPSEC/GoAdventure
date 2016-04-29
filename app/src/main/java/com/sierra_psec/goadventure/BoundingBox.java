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
		/*System.out.println("Top Left " + box.topLeft.x + "," + box.topLeft.y);
		System.out.println("Top Right " + box.topRight.x + "," + box.topRight.y);
		System.out.println("Bot Right " + box.botRight.x + "," + box.botRight.y);
		System.out.println("Bot Left " + box.botLeft.x + "," + box.botLeft.y);
		System.out.println("Center " + box.center.x + "," + box.center.y);*/
		/*
		System.out.println("Checking collision...\n");
		//Bottom left corner
		if (box.botLeft.x > topLeft.x && box.botLeft.x < topRight.x && box.botLeft.y > topLeft.y && box.botLeft.y < botLeft.y)
		{
			System.out.println("Collision 1...\n");
			return true;
		}
		//Bottom right corner
		else if (box.botRight.x > topLeft.x && box.botRight.x < topRight.x && box.botRight.y > topLeft.y && box.botRight.y < botLeft.y)
		{
			System.out.println("Collision 2...\n");
			return true;
		}
		//Top right corner
		else if (box.topRight.x > topLeft.x && box.topRight.x < topRight.x && box.topRight.y > topLeft.y && box.topRight.y < botLeft.y)
		{
			System.out.println("Collision 3...\n");
			return true;
		}
		//Top left corner
		else if (box.topLeft.x > topLeft.x && box.topLeft.x < topRight.x && box.topLeft.y > topLeft.y && box.topLeft.y < botLeft.y)
		{
			System.out.println("Collision 4...\n");
			return true;
		}
		else
		{//no box corners are inside our box
			System.out.println("not colliding\n");
			return false;
		}*/
	}

	public boolean isColliding(BoundingTri tri)
	{
		//since only the player will be colliding with prisms, we're only concerned with the top left and right corners of the box
		boolean ret = false;
		if (tri.isRight == false)
		{
			double rat = (topRight.y - tri.LRvertex.y) / (topRight.x - tri.LRvertex.x);
			if (rat < 1)
				ret = true;
		} else
		{ //right facing triangle
			double rat = (tri.LRvertex.y - topLeft.y) / (tri.LRvertex.x - topLeft.x);
			if (rat < 1)
				ret = true;
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

}
