package com.team1.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Projectile extends Sprite {

    private TiledMapTileLayer movementLayer;
    private Player player;
    
    public Projectile(Sprite sprite, TiledMapTileLayer movementLayer, Player player) {
        super(sprite);
        this.movementLayer = movementLayer;
        this.player = player;
    }

    public void draw(SpriteBatch spriteBatch) {
        update(Gdx.graphics.getDeltaTime());
        setX(player.getX());
        setY(player.getY());
        super.draw(spriteBatch);
        System.out.println("should have been drawn");
    }

    public void update(float delta) {
        
    }

}
