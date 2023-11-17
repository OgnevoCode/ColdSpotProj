package com.mygdx.game.Screens;

import static com.mygdx.game.Main.HEIGHT;
import static com.mygdx.game.Main.WIDTH;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.mygdx.game.Main;

public class GameOverScreen implements Screen {
    private Main parent;
    private Image gameOver;
    private Texture gameOverTexture;
    private final Stage stage;
    public GameOverScreen(Main main){
        parent = main;
        stage = new Stage(new FitViewport(WIDTH, HEIGHT));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        gameOverTexture = new Texture(Gdx.files.internal("main_sprites/logo/game_over.png"));
        gameOver = new Image(gameOverTexture);
        gameOver.setSize(WIDTH, HEIGHT);
        stage.addActor(gameOver);
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int i) {
                return false;
            }

            @Override
            public boolean keyUp(int i) {
                return false;
            }

            @Override
            public boolean keyTyped(char c) {
                return false;
            }

            @Override
            public boolean touchDown(int i, int i1, int i2, int i3) {
                //Gdx.app.exit();
                parent.setScreen(new GameScreen(parent));
                return false;
            }

            @Override
            public boolean touchUp(int i, int i1, int i2, int i3) {

                return false;
            }

            @Override
            public boolean touchDragged(int i, int i1, int i2) {

                return false;
            }

            @Override
            public boolean mouseMoved(int i, int i1) {
                return false;
            }

            @Override
            public boolean scrolled(float v, float v1) {
                return false;
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameOverTexture.dispose();
        stage.dispose();
    }
}
