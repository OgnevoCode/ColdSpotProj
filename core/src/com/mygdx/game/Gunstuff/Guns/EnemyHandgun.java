package com.mygdx.game.Gunstuff.Guns;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Characters.Enemy;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.UI.GUI.Joystick;
import com.mygdx.game.Gunstuff.Stuff.Bullet;

public class EnemyHandgun extends Gun{
    public boolean isFire;
    public int ammo = 15;
    private double startTime = 0;
    private double startTimeShoot = 0;
    private double endTime = 0;
    private double endTimeShoot = 0;
    private double interval = 1000 * 0.2;
    private double reloadTime = 1 * 1000;
    private Vector2 offset, barrelPosition;
    private Enemy en;
    private Vector2 barrelOffset;
    private Array<Bullet> bullets = new Array<Bullet>();
    public EnemyHandgun(Enemy en) {
        this.en = en;
        barrelOffset = new Vector2(170, 55).sub(en.img.getOriginX(), en.img.getOriginY());
    }
    @Override
    void reload_timer() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
            endTime = startTime + reloadTime;
        }
        if (endTime <= System.currentTimeMillis()) {
            startTime = 0;
            ammo = 15;
        }
    }

    @Override
    void shoot(Vector2 direcion) {
        if (startTimeShoot == 0) {
            startTimeShoot = System.currentTimeMillis();
            endTimeShoot = startTimeShoot + interval;
        }
        if (endTimeShoot <= System.currentTimeMillis()) {
            startTimeShoot = 0;
            GameScreen.bullets.add(new Bullet(barrelPosition, new Vector2(en.direction).rotateDeg((float)getRandomDoubleBetweenRange(-3, 3)), (int)getRandomDoubleBetweenRange(45, 48), 7, en.getWorld())); //GameScreen.player.position
            ammo--;
        }
    }
    public Array<Bullet> getBulletsArray() {
        return bullets;
    }

    @Override
    public void update(Joystick joystick) {
        offset = ((new Vector2(barrelOffset)).setAngleDeg(en.img.getRotation() - 10)).add(en.img.getOriginX(), en.img.getOriginY()); //add(sposoffset)
        barrelPosition = (new Vector2(en.img.getX(), en.img.getY())).add(offset);

        isFire = (joystick.circleBounds.contains(joystick.stickBounds)) ? false : true;

        if (ammo == 0) { //RELOADING TIME
            reload_timer();
        }

        if(isFire == true && ammo > 0) { //SHOOT
            shoot(joystick.direction);
        }
    }
}

