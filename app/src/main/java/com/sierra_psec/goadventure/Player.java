package com.sierra_psec.goadventure;

/**
 * Created by E6410 on 4/24/2016.
 */
public class Player {

    private final int WI = 35; //TODO: figure out how large the hitbox for the player should be, these are temp vals
    private final int HI = 35;

    public Vector2 pos; //y is fixed
    public Vector2 vel; //y will always be zero, only moving x -maybe have this be float instead then
    //public float velX; //TODO: how fast are we moving left and right?
    public BoundingBox bbox;


    Player() {
        //vel = new Vector2(velX, 0);

    }
}
