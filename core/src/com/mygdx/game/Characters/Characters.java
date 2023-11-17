package com.mygdx.game.Characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.GraphicsObj.GraphicsObj;

public abstract class Characters extends GraphicsObj {
    public Vector2 position;
    public int speed;
    public Vector2 direction;

    public Characters(Sprite img, Vector2 position, int speed) {
        super(img);
        this.position = new Vector2(position);
        this.speed = speed;
        direction = new Vector2(0,0);
    }
    public Characters(Sprite img, Vector2 position) {
        super(img);
        this.position = new Vector2(position);
        direction = new Vector2(0,0);
    }

}
