package com.sierra_psec.goadventure;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Neal on 5/5/2016.
 */
public class Photon {

        public Vector2 pos;
        public Vector2[] srcCircle;
        public Vector2[] srcNormal;
        public float[] angle;
        public  float rad;


        Photon(Vector2 position, float radius)
        {
            pos = position;
            rad =  radius;
            srcCircle = new Vector2[720];
            srcNormal = new Vector2[720];
            angle = new float[720];

            for (int i = 0; i < 720; i++)
            {
                angle[i] = 0.0f;

                float theta = ((float)i)/2.0f;

                srcNormal[i] = new Vector2((float)Math.cos((float)theta+90),(float)Math.sin((float)theta+90));
                srcCircle[i] = Vector2.add(pos,Vector2.mul(srcNormal[i], rad));

            }


        }

        public void draw(Canvas canvas)
        {

        }
}
