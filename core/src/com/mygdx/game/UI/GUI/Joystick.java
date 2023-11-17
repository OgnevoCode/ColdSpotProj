package com.mygdx.game.UI.GUI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Joystick extends Actor{
    private Texture circleImg, stickImg;
    public Circle circleBounds, stickBounds;
    private float rCircle, rStick;
    public Vector2 direction;
    private int pointer = -1;
    public Vector2 position;


    public Joystick(Texture cimg, Texture simg, Vector2 point, int size) {
        position = point;
        circleImg = cimg;
        stickImg = simg;
        rCircle = size / 2;
        rStick = rCircle / 2;
        circleBounds = new Circle(point, rCircle);
        stickBounds = new Circle(point, rStick);
        direction = new Vector2(0,0);
    }


    public void draw(Batch batch, float alpha) {
        batch.draw(circleImg, (circleBounds.x - rCircle), (circleBounds.y - rCircle), rCircle * 2, rCircle * 2);
        batch.draw(stickImg, (stickBounds.x - rStick + 9), (stickBounds.y - rStick + 8), rStick * 2, rStick * 2);
    }

    public void update(float x, float y, boolean isDownTouched, int pointer) {
        Vector2 touch = new Vector2(x, y);
        if (circleBounds.contains(touch) && isDownTouched && this.pointer == -1) this.pointer = pointer;
        if ((circleBounds.contains(touch) && isDownTouched && pointer == this.pointer)) atControl(new Vector2(x, y));
        //if (circleBounds.contains(stickBounds) && isDownTouched && pointer == this.pointer) atControl(new Vector2(x, y));
        if ((!isDownTouched && pointer == this.pointer)) returnStick(); // || (isDownTouched && pointer == this.pointer && !circleBounds.overlaps(stickBounds)))
    }
    private void atControl(Vector2 point) {
      stickBounds.setPosition(point);
      float dx = circleBounds.x - point.x;
      float dy = circleBounds.y - point.y;
      float dist = (float)Math.sqrt(dx * dx + dy * dy);

      direction.set(-(dx / dist), -(dy / dist));
    }
    private void returnStick() {
        stickBounds.setPosition(circleBounds.x, circleBounds.y);
        direction.set(0,0);
        pointer = -1;
    }
}