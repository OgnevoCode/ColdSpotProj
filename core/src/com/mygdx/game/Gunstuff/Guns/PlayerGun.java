package com.mygdx.game.Gunstuff.Guns;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Camera.Rumble;
import com.mygdx.game.Characters.Player;
import com.mygdx.game.Screens.GameScreen;
import com.mygdx.game.UI.GUI.Joystick;
import com.mygdx.game.Gunstuff.Stuff.Bullet;

public class PlayerGun extends Gun{
    private Sound shotSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shot.mp3"));
    private Sound reloadingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/reloading.mp3"));
    public boolean isFire;
    public int ammo = 20;
    private double startTime = 0;
    private double startTimeShoot = 0;
    private double endTime = 0;
    private double endTimeShoot = 0;
    private double interval = 1000 * 0.2;
    private double reloadTime = 4.0 * 1000;
    private Vector2 offset, barrelPosition;
    private final Player pl;
    private Vector2 barrelOffset;
    private Array<Bullet> bullets = new Array<>();

    public PlayerGun(Player pl) {
        this.pl = pl;
        barrelOffset = new Vector2(170, 55).sub(pl.img.getOriginX(), pl.img.getOriginY());
    }

    @Override
    void reload_timer() {
        if (startTime == 0) {
            startTime = System.currentTimeMillis();
            endTime = startTime + reloadTime;
            reloadingSound.play(1.0f);
        }
        if (endTime <= System.currentTimeMillis()) {
            startTime = 0;
            ammo = 20;
        }
    }
    @Override
    void shoot(Vector2 direction) {
        if (startTimeShoot == 0) {
            startTimeShoot = System.currentTimeMillis();
            endTimeShoot = startTimeShoot + interval;
        }
        if (endTimeShoot <= System.currentTimeMillis()) {
            startTimeShoot = 0;
            GameScreen.bullets.add(new Bullet(barrelPosition, new Vector2(direction).rotateDeg((float)getRandomDoubleBetweenRange(-2, 2)), (int)getRandomDoubleBetweenRange(55, 58), 7, pl.getWorld())); //GameScreen.player.position
            ammo--;
            shotSound.play(1.0f);
            Rumble.rumble(5f, 0.2f);
        }
    }
    public Array<Bullet> getBulletsArray() {
        return bullets;
    }

    @Override
    public void update(Joystick joystick) {
        offset = ((new Vector2(barrelOffset)).setAngleDeg(pl.img.getRotation() - 10)).add(pl.img.getOriginX(), pl.img.getOriginY()); //add(sposoffset)
        barrelPosition = (new Vector2(pl.img.getX(), pl.img.getY())).add(offset);
        //System.out.println(pl.img.getRotation());
        isFire = (joystick.circleBounds.contains(joystick.stickBounds)) ? false : true;

        if (ammo == 0) { //RELOADING TIME
            reload_timer();
        }

        if(isFire && ammo > 0) { //SHOOT
            shoot(joystick.direction);

        }
    }
}