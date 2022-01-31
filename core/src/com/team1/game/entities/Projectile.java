package com.team1.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.team1.game.screens.Play;

public class Projectile extends Sprite {

    private TiledMapTileLayer movementLayer;
    private Player player;
    private College target;
    private Vector3 startPos;
    private Vector3 targetPos;

    public boolean shouldRemove = false;

    private int velocity = 20;
    
    public Projectile(Sprite sprite, TiledMapTileLayer movementLayer, Player player, College target, Vector3 targetPos) {
        super(sprite);
        this.movementLayer = movementLayer;
        this.player = player;
        this.target = target;
        this.targetPos = targetPos;
        
        setX(player.getX() + Play.TILE_SIZE / 2);
        setY(player.getY() + Play.TILE_SIZE / 2);
        startPos = new Vector3(getX(), getY(), 0);
    }

    public void draw(SpriteBatch spriteBatch) {
        System.out.println("Projectile - draw");
        update(Gdx.graphics.getDeltaTime(), spriteBatch);
        super.draw(spriteBatch);
    }

    public void update(float delta, SpriteBatch spriteBatch) {
        // projectile movement and stuff
        System.out.println("projectile - update");
        System.out.println("X: " + getX() + ", Y: " + getY());
        
        Vector3 currPos = new Vector3(getX(), getY(), 0);

        System.out.println("targetPos: " + targetPos);
        Vector3 norm = new Vector3(targetPos.x, targetPos.y, 0);
        norm = norm.sub(startPos).nor();

        System.out.println("startPos: " + startPos + " currPos: " + currPos + " , targetPos: " + targetPos + " , norm: " + norm);

        currPos.add(norm.scl(velocity * Gdx.graphics.getDeltaTime()));
        setX(currPos.x);
        setY(currPos.y);

        /* if (getX() <= targetPos.x && getY() <= targetPos.y) {
            shouldRemove = true;
            target.hit(player.attackDmg);
        } */

        super.draw(spriteBatch);
    }

}
