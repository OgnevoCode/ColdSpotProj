package com.mygdx.game.Gunstuff.Stuff;

import static com.mygdx.game.Main.BIT_BULLET;
import static com.mygdx.game.Main.BIT_ENEMY;
import static com.mygdx.game.Main.BIT_PLAYER;
import static com.mygdx.game.Main.BIT_WALL;
import static com.mygdx.game.Main.PPM;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Bullet
{
    public Vector2 position = new Vector2();
    public Vector2 firstPosition = new Vector2();
    public Vector2 direction = new Vector2();
    public int speed, size;
    public Body body;

    public Bullet(Vector2 position, Vector2 direction, int speed, int size, World world)
    {
        //this.img = img;
        this.speed = speed;
        this.position.set(position);
        firstPosition.set(position);
        this.direction.set(direction);
        this.size = size;
        body = createCircleBody(world, position.x, position.y, size);
    }

//    public void draw(SpriteBatch batch) {
//        batch.draw(img, position.x, position.y, 56, 17);
//    }
    public void update() {
       position.add(direction.x * speed, direction.y * speed);
       body.setLinearVelocity(direction.x * speed, direction.y * speed);
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
        fd.filter.categoryBits = BIT_BULLET;
        fd.filter.maskBits = BIT_PLAYER | BIT_ENEMY | BIT_WALL;
        fd.filter.groupIndex = 1;
        pBody.createFixture(fd);
        pBody.setUserData("bullet");
        shape.dispose();
        return pBody;
    }
}
