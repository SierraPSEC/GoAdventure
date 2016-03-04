package com.sierra_psec.goadventure;

import java.io.PrintStream;

/**
 * Created by Luis on 3/10/2016.
 * Contains useful functions for vectors and vector operations.
 */

//Should I make a duplicate for int data types?
public class Vector2
{
    //I know these should be public, but this data struct is so simple that it does not require protection
    public float x;
    public float y;
    public Vector2()
    {
        this.x = this.y = 0;
    }

    public Vector2(Vector2 other)
    {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    //============ Static Math Functions for Vectors ==============

    //Adds the two vectors and returns a new vector
    public static Vector2 add(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.x + v2.x,v1.y + v2.y);
    }
    public static Vector2 sub(Vector2 v1, Vector2 v2)
    {
        return new Vector2(v1.x - v2.x, v1.y - v2.y);
    }
    //Returns the dot product of the two vectors
    public static float dot(Vector2 v1, Vector2 v2)
    {
        return v1.x*v2.x + v1.y*v2.y;
    }
    //Returns this vector multiplied by a scalar
    public static Vector2 mul(Vector2 vec, float scalar)
    {
        return new Vector2(vec.x * scalar, vec.y * scalar);
    }
    //Returns the vector length
    public static float len(Vector2 vec)
    {
        return (float) Math.sqrt((double)(vec.x*vec.x + vec.y*vec.y));
    }

    //Returns the vector length squared for faster calculations
    //(Technically the same as calling dot with itself, but for readability-sake)
    public static float lenSquared(Vector2 vec)
    {
        return vec.x*vec.x + vec.y*vec.y;
    }

    public static Vector2 normalize(Vector2 vec)
    {
        return Vector2.mul(vec,1/Vector2.len(vec));
    }

    //Accepts angle in radians
    public static Vector2 angleToVec(float rot)
    {
        return new Vector2((float) Math.cos(rot),(float)Math.sin(rot));
    }

    //Returns angle in radians
    public static float vecToAngle(Vector2 vec)
    {
        vec = Vector2.normalize(vec);
        float angle = (float) Math.atan(vec.y / vec.x);
        if(vec.x < 0)
            angle += Math.PI;

        return angle;
    }

    public static Vector2 RIGHT = new Vector2(1,0);
    public static Vector2 UP = new Vector2(0,1);
}
