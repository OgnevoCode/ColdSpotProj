package com.mygdx.game.Utils;

import static com.mygdx.game.Main.BIT_BULLET;
import static com.mygdx.game.Main.BIT_ENEMY;
import static com.mygdx.game.Main.BIT_PLAYER;
import static com.mygdx.game.Main.BIT_WALL;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Characters.Player;

public class HitContactListener implements ContactListener {
    private Array<Enemy> enemies = new Array<Enemy>();
    private Player player;
    public Array<Body> bodiesToDestroy = new Array<Body>();

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null) System.out.println("no fixture");
        if(fa.getBody() == null || fb.getBody() == null) System.out.println("no body");
        if(fa.getBody().getUserData() == null || fb.getBody().getUserData() == null) System.out.println("no data");
        hitWallBullet(fa, fb);
        hitEnemyBullet(fa, fb);
        hitEnemyWall(fa, fb);
        hitEnemyPlayer(fa, fb);
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        if(fa == null || fb == null) System.out.println("no fixture at the end");
        if(fa.getBody() == null || fb.getBody() == null) System.out.println("no body at the end");
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {

    }
    private void hitWallBullet(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == BIT_WALL || b.getFilterData().categoryBits == BIT_WALL) && (a.getFilterData().categoryBits == BIT_BULLET || b.getFilterData().categoryBits == BIT_BULLET)) {
            Body toRemove = (a.getBody().getType() == BodyDef.BodyType.DynamicBody ? a.getBody() : b.getBody());
            toRemove.setUserData("remove");
            bodiesToDestroy.add(toRemove);
        }
    }
    private void hitEnemyBullet(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == BIT_ENEMY || b.getFilterData().categoryBits == BIT_ENEMY) && (a.getFilterData().categoryBits == BIT_BULLET || b.getFilterData().categoryBits == BIT_BULLET)) {
            bodiesToDestroy.add(a.getBody(), b.getBody());
        }
    }
    private void hitEnemyWall(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == BIT_ENEMY || b.getFilterData().categoryBits == BIT_ENEMY) && (a.getFilterData().categoryBits == BIT_WALL || b.getFilterData().categoryBits == BIT_WALL)) {
            Body enemy = (a.getBody().getType() == BodyDef.BodyType.DynamicBody ? a.getBody() : b.getBody());
            for (Enemy en: enemies) {
                if (en.getBody() == enemy && en.isAlive) {
                    en.direction.scl(-1.0f);
                }
            }
        }
    }
    private void hitEnemyPlayer(Fixture a, Fixture b) {
        if ((a.getFilterData().categoryBits == BIT_ENEMY || b.getFilterData().categoryBits == BIT_ENEMY) && (a.getFilterData().categoryBits == BIT_PLAYER || b.getFilterData().categoryBits == BIT_PLAYER)) {
            player.die();
        }
    }
    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEnemies(Array<Enemy> enemies) {
        this.enemies = enemies;
    }
}
