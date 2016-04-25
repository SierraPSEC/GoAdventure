package com.sierra_psec.goadventure;

import android.view.animation.BounceInterpolator;

/**
 * Created by E6410 on 4/24/2016.
 */
public class Player {

    private final int WI = 30; //TODO: figure out how large the hitbox for the player should be, these are temp vals, should be even num
    private final int HI = 30;

    public Vector2 pos; //y is fixed
    public Vector2 vel; //y will always be zero, only moving x -maybe have this be float instead then
    //public float velX; //TODO: how fast are we moving left and right?
    public BoundingBox bbox;


    Player(Vector2 position) {
        pos = new Vector2(position.x - (WI/2), position.y);
        bbox = new BoundingBox(pos, WI, HI);
    }

    public void updatePos(float delta) {
        bbox.updateXPos(delta);
    }
}
