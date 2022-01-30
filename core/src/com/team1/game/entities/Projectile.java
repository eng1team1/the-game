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

        Vector3 norm = targetPos.sub(currPos).nor();

        currPos.add(norm.scl(-velocity * Gdx.graphics.getDeltaTime()));
        setX(currPos.x);
        setY(currPos.y);
        /* 
        float temp = getX() + velocity * delta;
        System.out.println(temp + " = " + getX() + " + " + velocity + " * " + delta);
        setX(temp);
        System.out.println("setX(temp).  getX(): " + getX()); */
        super.draw(spriteBatch);
    }

}
