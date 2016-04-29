package com.sierra_psec.goadventure;

/**
 * Created by E6410 on 4/28/2016.
 */
public class Block {
    private float width = 0.2f;
    private float height = 0.2f;

    public BoundingBox bbox;
    public Vector2 pos;

    Block(Vector2 position, float width, float height) {
        bbox = new BoundingBox(position, width, height);
        pos = new Vector2(position);
    }

    public void onCollide() {
        //do something to the player
    }
}
