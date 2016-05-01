package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
* Created by E6410 on 4/28/2016.
 */
public class Prism {
    public BoundingTri btri;
    public Vector2 initPos;
    public Vector2 pos;
    public Vector2 vertex;

    Prism(Vector2 position, boolean r) {
        btri = new BoundingTri(position, r);
        vertex = btri.getLRvertex();
        pos = new Vector2(position);
        initPos = new Vector2(position);
    }

    public void onCollide() {
        //do something to the player
        System.out.println("player collided with a prism");
    }

    public void updatePos(float delta) {
        btri.updateYPos(delta);
    }
    public void resetPos(float delta)
    {
        pos.y = initPos.y;
        btri.resetYPos(delta);
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setStrokeWidth(2);
        p.setColor(android.graphics.Color.RED);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setAntiAlias(true);

        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(btri.topVertex.x * GamePanel.screenX, btri.topVertex.y * GamePanel.screenY);
        path.lineTo(btri.LRvertex.x * GamePanel.screenX, btri.LRvertex.y * GamePanel.screenY);
        path.lineTo(btri.botVertex.x * GamePanel.screenX, btri.botVertex.y * GamePanel.screenY);
        path.lineTo(btri.topVertex.x * GamePanel.screenX, btri.topVertex.y * GamePanel.screenY);
        path.close();

        canvas.drawPath(path,p);
    }
}
