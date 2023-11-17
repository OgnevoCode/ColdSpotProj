package com.mygdx.game.GraphicsObj;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GraphicsObj {
    public Sprite img; //Sprite

    public GraphicsObj(Sprite img) {
        this.img = img;
    }
    public abstract void draw(SpriteBatch batch);
    public abstract void update();
}
