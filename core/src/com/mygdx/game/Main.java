package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Screens.SplashScreen;

//MAIN CLASS LOADS SOME GAME OBJECTS AND RENDERS SCREENS
public class Main extends Game {
	public static SpriteBatch batch;
	public static ScreenViewport viewport = new ScreenViewport();
	public static OrthographicCamera camera;
	public static final float PPM = 64;
	public static final short BIT_WALL = 2;
	public static final short BIT_PLAYER = 4;
	public static final short BIT_ENEMY= 8;
	public static final short BIT_BULLET= 16;

	public static int WIDTH, HEIGHT;
	public static ShapeRenderer shapeRenderer;

	private SplashScreen splashScreen;

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void create () {
		Gdx.input.setOnscreenKeyboardVisible(false);
		//VISUAL CONFIGURATION
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		shapeRenderer = new ShapeRenderer();

		//SCREEN PARAMETERS
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();


		splashScreen = new SplashScreen(this);
		setScreen(splashScreen);
	}

	@Override
	public void dispose () {
		batch.dispose();
		shapeRenderer.dispose();
		splashScreen.dispose();
	}
}
