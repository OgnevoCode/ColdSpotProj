package com.mygdx.game.Screens;

import static com.mygdx.game.Main.PPM;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.ColorAction;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.mygdx.game.Map.MapParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Camera.Rumble;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Gunstuff.Guns.PlayerGun;
import com.mygdx.game.Gunstuff.Stuff.Bullet;
import com.mygdx.game.UI.GUI.Joystick;
import com.mygdx.game.Main;
import com.mygdx.game.UI.GUI.Text;
import com.mygdx.game.Utils.HitContactListener;

public class GameScreen implements Screen {
    private Main parent;
    private Joystick joystickL, joystickR;
    private static Player player;
    public static Array<Bullet> bullets;
    private static float angle;
    private Text ammo_view;
    private Array<Enemy> enemies = new Array<Enemy>();
    private Array<Enemy> dead_enemies = new Array<Enemy>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static World world;
    private HitContactListener hcl = new HitContactListener();
    private Array<Body> bodiesToDestroy = new Array<>();
    private Color color = new Color(Color.ORANGE);
    private Stage stage;
    private Touchpad touchpad;

    public GameScreen(Main main){
        parent = main;
        stage = new Stage(Main.viewport);
    }

    //CONTROLS TOUCH DETECTION
    @Override
    public void show()  {
        loadActors();
        Gdx.input.setInputProcessor(new InputProcessor() {
                                            @Override
                                            public boolean keyDown(int keycode) {
                                                return false;
                                            }

                                            @Override
                                            public boolean keyUp(int keycode) {
                                                return false;
                                            }

                                            @Override
                                            public boolean keyTyped(char character) {
                                                return false;
                                            }

                                            @Override
                                            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                                                screenY = (Gdx.graphics.getHeight() - screenY);
                                                multitouch( screenX, screenY, true, pointer);
                                                return false;
                                            }

                                            @Override
                                            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                                                screenY =  (Gdx.graphics.getHeight() - screenY);
                                                multitouch(screenX, screenY, false, pointer);
                                                return false;
                                            }

                                            @Override
                                            public boolean touchDragged(int screenX, int screenY, int pointer) {
                                                screenY = (Gdx.graphics.getHeight() - screenY);
                                                multitouch(screenX, screenY, true, pointer);
                                                return false;
                                            }


                                            @Override
                                            public boolean mouseMoved(int screenX, int screenY) {
                                                return false;
                                            }

                                            @Override
                                            public boolean scrolled(float amountX, float amountY) {
                                                return false;
                                            }
                                        }
            );
        }


    public void loadActors() {
        bullets = new Array<Bullet>();
        //MAP
        world = new World(new Vector2(0, 0), false);
        world.setContactListener(hcl);
        map = new TmxMapLoader().load("map/kartapapka/final_map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1.5f);
        MapParser.buildShapes(map.getLayers().get("Obstacles").getObjects(), world, mapRenderer.getUnitScale());

        //PLAYER CONFIG
        player = new Player(new Sprite(new Texture("main_sprites/main_char_ak_d.png")),  new Vector2(52.8f, 7.5f).scl(PPM), 7, world);
        player.getGun(new PlayerGun(player));

        //GRIPS CONFIG
        joystickR = new Joystick(new Texture("grips/circle.png"),  new Texture("grips/red_grip.png"), new Vector2((float) (Main.WIDTH - (Main.WIDTH * 0.15)), (Main.HEIGHT / 3) / 2 + (Main.HEIGHT / 3) / 4), Main.HEIGHT / 3);
        joystickL = new Joystick(new Texture("grips/circle.png"),  new Texture("grips/red_grip.png"), new Vector2((float)(Main.WIDTH - (Main.WIDTH * 0.85)), (Main.HEIGHT / 3) / 2 + (Main.HEIGHT / 3) / 4), Main.HEIGHT / 3);


        //ENEMIES GENERATION
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(18.6f,30f).scl(PPM), 9, world));
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(35f,24f).scl(PPM), 9, world));
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(61.4f,18f).scl(PPM), 9, world));
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(80.6f,35f).scl(PPM), 9, world));
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(58f, 38.4f).scl(PPM), 9, world));
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(30f, 45f).scl(PPM), 9, world));
        enemies.add(new Enemy(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_knife.png"))), new Vector2(27.8f, 62f).scl(PPM), 9, world));
        for (Enemy enemy: enemies) {
            dead_enemies.add(enemy);
        }

        //FONT GENERATION
        ammo_view = new Text("fonts/ammo.ttf",(float) Main.WIDTH - 100, (float) Main.HEIGHT - 80, 80, Color.WHITE);

        //FIRST BULLET TO AVOID SPAWN TROUBLES
        bullets.add(new Bullet(new Vector2(), new Vector2(), 0, 0, world));
        bullets.get(0).body.setActive(false);

        hcl.setEnemies(enemies);
        hcl.setPlayer(player);

        //ADDING ACTORS
        stage.addActor(joystickR);
        stage.addActor(joystickL);
        stage.addActor(ammo_view);

    }

    @Override
    public void render(float delta) {
        //CHANGING BACKGROUND COLOR AND CLEARING BACKGROUND
        changeBackgroundColor(color, Color.PURPLE, 2, delta);
        Gdx.gl.glClearColor(color.r, color.g, color.b, color.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameUpdate();
        Main.shapeRenderer.setProjectionMatrix(Main.camera.combined);

        //CAMERA UPDATING
        Main.camera.position.set(player.getBody().getPosition().x * PPM, player.getBody().getPosition().y * PPM, 0);
        rumble(player.gun.isFire, player.gun.ammo, delta);
        Main.camera.update();

        //MAP RENDERING
        mapRenderer.setView(Main.camera);
        mapRenderer.render();
        //b2dr.render(world, new Matrix4(main.camera.combined).scl(PPM));

        //RENDERING SPRITES
        Main.batch.setProjectionMatrix(Main.camera.combined);
        Main.batch.begin();
        gameRender(Main.batch);
        Main.batch.end();

        //STAGE DRAWING
        stage.act(delta);
        stage.draw();

        //BULLETS DRAWING
        Main.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Main.shapeRenderer.setColor(Color.GOLDENROD);
        for (int i = 0; i < bullets.size; i++) {
            Main.shapeRenderer.circle(bullets.get(i).position.x, bullets.get(i).position.y, bullets.get(i).size);
        }
        Main.shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        Main.camera.viewportWidth = width;
        Main.camera.viewportHeight = height;
        Main.camera.update();
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
        stage.dispose();
        map.dispose();
        mapRenderer.dispose();
        world.dispose();
    }
    private Color changeBackgroundColor(Color start, Color end, int time, float delta) {
        ColorAction colorAction = new ColorAction();
        colorAction.setColor(start);
        colorAction.setDuration(time);
        colorAction.setEndColor(end);
        colorAction.act(delta);
        return start;
    }

    //SCREEN RUMBLING WHILE SHOOTING
    private void rumble(boolean isFire, int ammo, float delta) {
        if (isFire && ammo > 0) {
            if (Rumble.getRumbleTimeLeft() > 0){
                Rumble.tick(delta);
                Main.camera.translate(Rumble.getPos());
            }
        }
    }
    public void gameRender(SpriteBatch batch) {
        //PLAYER ROTATION
        if (joystickL.direction.angleDeg() != 0) angle = joystickL.direction.angleDeg();
        if (joystickR.direction.angleDeg() != 0) angle = joystickR.direction.angleDeg();
        player.img.setRotation(angle);

        //AMMO VIEW UPDATING
        ammo_view.update(String.valueOf(player.gun.ammo));

        //ENEMY ROTATION
        for(int i = 0; i < enemies.size; i++) {
            enemies.get(i).img.setRotation(enemies.get(i).direction.angleDeg());
            enemies.get(i).draw(batch);
        }
        player.draw(batch);
    }

    //REMOVING USELESS BODIES
    private void deadBodiesDestroying(Array<Body> bodiesToDestroy) {
        for (Body body : bodiesToDestroy) {
            if (body != null && body.getUserData() != null && body.isActive()) {
                body.setActive(false);
                bodiesToDestroy.removeValue(body, true);
            }
        }
    }
    public void gameUpdate(){
        //WORLD UPDATING
        world.step(1 / 60f, 6, 2);
        deadBodiesDestroying(hcl.bodiesToDestroy);

        //PLAYER UPDATING
        player.update();
        player.direction.set(joystickL.direction);
        player.gun.update(joystickR);

        //ENEMY UPDATING
        for(int i = 0; i < enemies.size; i++) {
            enemies.get(i).update();
            enemies.get(i).attackPlayer(player);
//            enemies.get(i).handgun.update(joystickR);
            if (!enemies.get(i).getBody().isActive() && enemies.get(i).isAlive) {
                enemies.get(i).die();
            }
        }
        for (Enemy enemy: enemies) {
            if (!enemy.isAlive) {
                dead_enemies.removeValue(enemy, true);
            }
        }
        if (dead_enemies.size == 0) {
            parent.setScreen(new WinScreen(parent));
        }
        //BULLETS UPDATING
        for (int i = 0; i < bullets.size; i++) {
            bullets.get(i).update();
            if (!bullets.get(i).body.isActive()) {
                bullets.removeIndex(i--);
            }
        }
        //SCREEN CHANGING CONTROL
        if (!player.isAlive) {
            parent.setScreen(new GameOverScreen(parent));
        }
    }
    //MULTITOUCH CONTROL
    private void multitouch(float x, float y, boolean isDownTouched, int pointer) {
        for (int i = 0; i < 5; i++) {
            joystickL.update(x, y, isDownTouched, pointer);
            joystickR.update(x, y, isDownTouched, pointer);
        }
    }
}

