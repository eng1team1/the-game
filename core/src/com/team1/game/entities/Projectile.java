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
    private Object sender;
    private Object target;
    private Vector3 startPos;
    private Vector3 targetPos;
    private boolean sentByPlayer;

    public boolean shouldRemove = false;

    private int velocity = 20;
    
    public Projectile(Sprite sprite, TiledMapTileLayer movementLayer, Object sender, Object target, Vector3 targetPos, boolean sentByPlayer) {
        super(sprite);
        this.movementLayer = movementLayer;
        this.sender = sender;
        this.target = target;
        /// this.targetPos = targetPos;
        this.sentByPlayer = sentByPlayer;
        
        if (sentByPlayer) {
            setX(((Player) sender).getX() + Play.TILE_SIZE / 2);
            setY(((Player) sender).getY() + Play.TILE_SIZE / 2);
            this.targetPos = new Vector3(((College) target).getX() + Play.TILE_SIZE / 2, ((College) target).getY() + Play.TILE_SIZE / 2, 0);
        } else {
            setX(((College) sender).getX() + Play.TILE_SIZE / 2);
            setY(((College) sender).getY() + Play.TILE_SIZE / 2);
            this.targetPos = new Vector3(((Player) target).getX() + Play.TILE_SIZE / 2, ((Player) target).getY() + Play.TILE_SIZE / 2, 0);
        }
        
        startPos = new Vector3(getX(), getY(), 0);
    }

    public void draw(SpriteBatch spriteBatch) {
        System.out.println("Projectile - draw");
        update(Gdx.graphics.getDeltaTime(), spriteBatch);
        super.draw(spriteBatch);
    }

    public void update(float delta, SpriteBatch spriteBatch) {
        // projectile movement and stuff
        // System.out.println("projectile - update");
        /// System.out.println("X: " + getX() + ", Y: " + getY());
        
        Vector3 currPos = new Vector3(getX(), getY(), 0);

        // System.out.println("targetPos: " + targetPos);
        Vector3 norm = new Vector3(targetPos.x, targetPos.y, 0);
        norm = norm.sub(startPos).nor();

        // System.out.println("startPos: " + startPos + " currPos: " + currPos + " , targetPos: " + targetPos + " , norm: " + norm);

        currPos.add(norm.scl(velocity * Gdx.graphics.getDeltaTime()));
        setX(currPos.x);
        setY(currPos.y);

        // if norm.x is +ve then moving right else moving left
        // if norm.y is +ve then moving up else moving down

        if (!sentByPlayer) {
            System.out.println("(" + ((Player) target).getX() + ", " + ((Player) target).getY() + ") :: " + targetPos);
        }

        if (!sentByPlayer) {
            if ((((Player) target).getX() + Play.TILE_SIZE / 2) != targetPos.x || (((Player) target).getY() + Play.TILE_SIZE / 2) != targetPos.y) {
                shouldRemove = true;
            }
        } else {
            if (norm.x > 0 && norm.y > 0) {
                if (getX() >= targetPos.x && getY() >= targetPos.y) {
                    if (sentByPlayer) {
                        ((College) target).hit(((Player) sender).attackDmg);
                        if (((College) target).isDestroyed) { 
                            System.out.println("Destroyed");
                            ((Player) sender).endCombat();
                        }
                    } else {
                        ((Player) target).hit(((College) sender).attackDmg);
                        if (((Player) target).isDestroyed) { 
                            System.out.println("Destroyed");
                        }
                    }
                    
                    shouldRemove = true;
                }
            } else if (norm.x > 0 && norm.y < 0) {
                if (getX() >= targetPos.x && getY() <= targetPos.y) { 
                    if (sentByPlayer) {
                        ((College) target).hit(((Player) sender).attackDmg);
                        if (((College) target).isDestroyed) { 
                            System.out.println("Destroyed");
                            ((Player) sender).endCombat();
                        }
                    } else {
                        ((Player) target).hit(((College) sender).attackDmg);
                        if (((Player) target).isDestroyed) { 
                            System.out.println("Destroyed");
                        }
                    }
                    
                    shouldRemove = true;
                }
            } else if (norm.x < 0 && norm.y > 0) {
                if (getX() <= targetPos.x && getY() >= targetPos.y) { 
                    if (sentByPlayer) {
                        ((College) target).hit(((Player) sender).attackDmg);
                        if (((College) target).isDestroyed) { 
                            System.out.println("Destroyed");
                            ((Player) sender).endCombat();
                        }
                    } else {
                        ((Player) target).hit(((College) sender).attackDmg);
                        if (((Player) target).isDestroyed) { 
                            System.out.println("Destroyed");
                        }
                    }
                    
                    shouldRemove = true;
                }
            } else if (norm.x < 0 && norm.y < 0) {
                if (getX() <= targetPos.x && getY() <= targetPos.y) { // better hit detection needed
                    if (sentByPlayer) {
                        ((College) target).hit(((Player) sender).attackDmg);
                        if (((College) target).isDestroyed) { 
                            System.out.println("Destroyed");
                            ((Player) sender).endCombat();
                        }
                    } else {
                        ((Player) target).hit(((College) sender).attackDmg);
                        if (((Player) target).isDestroyed) { 
                            System.out.println("Destroyed");
                        }
                    }
                    
                    shouldRemove = true;
                }
            }
        }
        
        super.draw(spriteBatch);
    }
}
