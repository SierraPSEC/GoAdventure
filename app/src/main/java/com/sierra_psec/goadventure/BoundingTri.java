package com.sierra_psec.goadventure;

import android.util.LruCache;

/**
 * Created by E6410 on 4/24/2016
 * special case bounding for prisms, which will be triangle shapes, and possibly filters if we give them a trapezoid shape
 * triangles will be the 45-45-90 variant
 */
public class BoundingTri {

    private final double ROOT_TWO = 1.41421; //square root of 2 to five decimals
    private final float DEFAULT_HEIGHT = 0.08f; //temp value
    private final float DEFAULT_LEN = DEFAULT_HEIGHT * 2.0f; //temp value

    public float h; //distance from border x to LRvertex.x
    public Vector2 topVertex;
    public boolean isRight; //true = right, false = left

    public Vector2 LRvertex;
    public Vector2 botVertex;
    public float l; //vertical distance of triangle, 2*h

    //create a default size triangle w/ predefined height (might only use this if out prisms are only 1 size)
    BoundingTri(Vector2 startPoint, boolean right)
    {
        topVertex = new Vector2(startPoint);
        botVertex = new Vector2(startPoint.x, startPoint.y + DEFAULT_LEN);
        l = DEFAULT_LEN;
        h = DEFAULT_HEIGHT;

        if (right == true) {
            isRight = true;
            LRvertex = new Vector2(startPoint.x + (DEFAULT_HEIGHT * GamePanel.ASPECT_RATIO), startPoint.y + DEFAULT_HEIGHT );
        }
        else {
            isRight = false;
            LRvertex = new Vector2(startPoint.x - (DEFAULT_HEIGHT * GamePanel.ASPECT_RATIO), startPoint.y + DEFAULT_HEIGHT);
        }

    }

    //variable size triangle, might not be needed
    BoundingTri(Vector2 startPoint, float heightTri, boolean right)
    {
        h = heightTri;
        l = h * 2;
        topVertex = new Vector2(startPoint);
        botVertex = new Vector2(startPoint.x, startPoint.y + l);

        if (right == true) {
            isRight = true;
            LRvertex = new Vector2(startPoint.x + h, startPoint.y + h);
        }
        else {
            isRight = false;
            LRvertex = new Vector2(startPoint.x - h, startPoint.y + h);
        }
    }

    public Vector2 getLRvertex() {
        return LRvertex;
    }

    public void updateYPos(float delta)
    {
        topVertex.y += delta;
        LRvertex.y += delta;
        botVertex.y += delta;
    }

    public void resetYPos(float delta)
    {
        topVertex.y = delta;
        LRvertex.y = delta + h;
        botVertex.y = delta + l;
    }
}
