package com.sierra_psec.goadventure;

import java.util.Vector;

/**
* Created by E6410 on 4/28/2016.
 */
public class Prism {
    public BoundingTri btri;
    public Vector2 pos;

    public Vector2 vertex;

    Prism(Vector2 position, boolean r) {
        btri = new BoundingTri(pos, r);
        vertex = btri.getLRvertex();
        pos = new Vector2(position);
    }

    public void onCollide() {
        //do something to the player
    }
}
