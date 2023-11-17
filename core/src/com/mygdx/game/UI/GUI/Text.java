package com.mygdx.game.UI.GUI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
//TEXT VISUALISATION
public class Text extends Actor {
    private BitmapFont text;
    private CharSequence str;
    private float x, y;
    public Text(String path, float x, float y, int size, Color color) {
        this.y = y;
        this.x = x;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(path));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        text = generator.generateFont(parameter);
        text.setColor(color);
        generator.dispose();
    }

    public void draw(Batch batch, float parentalAlpha) {
        text.draw(batch, str, x, y);
    }

    @Override
    public float getY() {
        return y;
    }

    @Override
    public float getX() {
        return x;
    }

    public void update(String text) {
        str = String.valueOf(text);
    }

}
