package com.mygdx.game.Characters;

import static com.mygdx.game.Main.BIT_BULLET;
import static com.mygdx.game.Main.BIT_ENEMY;
import static com.mygdx.game.Main.BIT_PLAYER;
import static com.mygdx.game.Main.BIT_WALL;
import static com.mygdx.game.Main.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Gunstuff.Guns.EnemyHandgun;
import com.badlogic.gdx.utils.Array;


public class Enemy extends Characters{
    public EnemyHandgun handgun;
    private double startTime = 0;
    private double findTime = 7 * 1000;
    private double endTime = 0;
    public boolean isAlive = true;
    private Array<Sprite> dead_img = new Array<Sprite>();
    private Body body;
    private World world;

    public Enemy(Sprite img, Vector2 position, int speed, World world) {
        super(img, position, speed);
        this.world = world;
        this.body = createCircleBody(world, position.x, position.y, 65);
        img.setOrigin(50f, img.getHeight()/2);
        //direction.set((float) Math.sin(Math.toRadians(Math.random() * 360)), (float) Math.cos(Math.toRadians(Math.random() * 360)));
        direction.set(1f,1f);
        dead_img.add(new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_dead2.png"))), new Sprite(new Texture(Gdx.files.internal("main_sprites/enemy_dead.png"))));
        for (int i = 0; i < dead_img.size; i++) {dead_img.get(i).setScale(5f);}
    }

    @Override
    public void draw(SpriteBatch batch) {
        img.setPosition(body.getPosition().x * PPM - 50, body.getPosition().y * PPM - 78);
        if (!isAlive) {
            img.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
        }
        img.draw(batch);
//        batch.draw(img, position.x, position.y, img.getWidth(), img.getHeight());
    }
    public Body getBody() {
        return body;
    }

    public void die() {
        img.set(dead_img.random());
        Gdx.input.vibrate((int) (0.2 * 1000));
        direction.set(direction.scl(-1));
        speed = 0;
        isAlive = false;
    }
    public World getWorld() {
        return world;
    }

//    public void getGun(Enemy_handgun handgun) {
//        this.handgun = handgun;
//    }
    @SuppressWarnings("SuspiciousIndentation")
    public void attackPlayer(Player pl) {
        if (isAlive && (pl.getBody().getPosition().sub(body.getPosition())).len() <= 10)
        direction.set(new Vector2(pl.getBody().getPosition().sub(body.getPosition())).nor());
    }

    @Override
    public void update() {
        body.setLinearVelocity(direction.x * speed, direction.y * speed);
    }

//    public void detectPlayer(Player pl) {
//        if (pl.body.getPosition().sub(body.getPosition()).len() <= 10) {
//            direction.set(pl.body.getPosition());
//        }
//    }
    private Body createCircleBody(World world, float x, float y, float radius) {
        Body pBody;
        BodyDef def = new BodyDef();

        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x / PPM, y / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius / PPM);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.density = 1.0f;
        fd.filter.categoryBits = BIT_ENEMY;
        fd.filter.maskBits = BIT_PLAYER | BIT_WALL | BIT_BULLET;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        pBody.setUserData("enemy");
        shape.dispose();
        return pBody;
    }
}
