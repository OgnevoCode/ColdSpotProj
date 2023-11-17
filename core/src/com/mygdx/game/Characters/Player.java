package com.mygdx.game.Characters;

import static com.mygdx.game.Main.BIT_BULLET;
import static com.mygdx.game.Main.BIT_ENEMY;
import static com.mygdx.game.Main.BIT_PLAYER;
import static com.mygdx.game.Main.BIT_WALL;
import static com.mygdx.game.Main.PPM;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Gunstuff.Guns.PlayerGun;

public class Player extends Characters {
    public PlayerGun gun;
    private Body body;
    private World world;
    public boolean isAlive = true;
    public Player(Sprite img, Vector2 position, int speed, World world) {
        super(img, position, speed); //w, h = 210.0 210.0
        img.setOrigin(50f, img.getHeight()/2);
        this.world = world;
        body = createCircleBody(world, position.x, position.y, 65);
    }
    public void getGun(PlayerGun ak) {
        gun = ak;
    }
    public World getWorld() {
        return world;
    }


    @Override
    public void draw(SpriteBatch batch) {
        img.setPosition(body.getPosition().x * PPM - 50, body.getPosition().y * PPM - 78);
        img.draw(batch);
    }
    public Body getBody() {
        return body;
    }

    @Override
    public void update() {
        body.setLinearVelocity(direction.x * speed, direction.y * speed);
    }
    public void die() {
        speed = 0;
        isAlive = false;
    }
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
        fd.filter.categoryBits = BIT_PLAYER;
        fd.filter.maskBits = BIT_ENEMY | BIT_WALL | BIT_BULLET;
        fd.filter.groupIndex = 0;
        pBody.createFixture(fd);
        pBody.setUserData("player");
        shape.dispose();
        return pBody;
        }
    }
