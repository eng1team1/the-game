package com.team1.game.entities;

import java.util.Random;

import javax.swing.SpinnerDateModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.team1.game.Combat;
import com.team1.game.screens.Play;

/** Class that represents the object the user controls */
public class Player extends Sprite {

    private TiledMapTileLayer movementLayer;
    
    public Texture projectileImg;

    Vector3 targetPos;
    Cell collegeCell = null;
    boolean inCombat = false;
    boolean moveFlag = false;
    public Combat beingAttacked = null;
    private int moveRadius = 4;

    public boolean isDestroyed = false;
    public int health = 100;
    public int attackDmg = 20;
    public double attackSpd = 1; 
    // int moveSpeed = 1; // possibly? for when better movement has been implemented
    public int xp = 0;

    public Player(Sprite sprite, TiledMapTileLayer movementLayer) {
        super(sprite);
        this.movementLayer = movementLayer;
        projectileImg = new Texture("img/cannonball.png");
    }

    
    /** 
     * @param spriteBatch
     */
    public void draw(SpriteBatch spriteBatch) {
        // update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    
    /** 
     * @param delta
     */
    public void update(float delta) {
        
    }

    
    /** 
     * Will move the sprite of the player to the tile located at mousePos
     * 
     * @param mousePos : The position where the mouse clicked the tile
     */
    public void moveTo(Vector3 mousePos) {
        // might want to use https://github.com/xaguzman/pathfinding for pathfinding, would have to reconfigure some of the tiledmap stuff but its just different classes
        // or could just create a duplicate graph and run A* on that then tell paly to move to the tiles corresponding to it.
        int col = Math.round(Math.round(mousePos.x) / Play.TILE_SIZE);
        int row = Math.round(Math.round(mousePos.y) / Play.TILE_SIZE);

        boolean canMove = movementLayer.getCell(col, row).getTile().getProperties().containsKey("traversable") && canReach(mousePos);

        System.out.println("canMove: " + canMove);

        if (canMove) {
            setX(col * Play.TILE_SIZE);
            setY(row * Play.TILE_SIZE);
            
            Random rand = new Random();
            xp += 10 + rand.nextInt(40);
        }
    }

    
    /** 
     * Tetsing to see if player can reach new position, pos
     * 
     * @param pos : Position that player wants to reach
     * @return boolean : true if player can reach, false otherwise
     */
    public boolean canReach(Vector3 pos) {
        int col = Math.round(Math.round(pos.x) / Play.TILE_SIZE);
        int row = Math.round(Math.round(pos.y) / Play.TILE_SIZE);

        int currCol = Math.round(Math.round(getX()) / Play.TILE_SIZE);
        int currRow = Math.round(Math.round(getY()) / Play.TILE_SIZE);;

        // Euclidean
        int dist = (int) Math.round(Math.sqrt(Math.pow(col - currCol, 2) + Math.pow(row - currRow, 2)));
        
        System.out.println("(" + currCol + ", " + currRow + ") -> (" + col + ", " + row + ") = " + dist);
        System.out.println("moveRadius: " + moveRadius);

        return dist < moveRadius;
    }
    
    
    /** 
     * @param flag : will player move
     */
    public void setMoveFlag(boolean flag) {
        moveFlag = flag;
    }

    
    /** 
     * @return boolean : players moveFlag
     */
    public boolean getMoveFlag() {
        return moveFlag;
    }

    
    /** 
     * @param collegeCell : the cell of the college that the player wants to attack
     * @param targetPos : The position of the college that the user wants to attack
     */
    public void startCombat(Cell collegeCell, Vector3 targetPos) {
        inCombat = true;
        this.collegeCell = collegeCell;
        this.targetPos = targetPos;
    }

    
    /** 
     * @param spriteBatch : spriteBatch used to draw projectile
     * @param target : The college the projectile is aimed at
     * @param targetPos : The position of the target
     * @return Projectile : returns the projectile that was just created and 'shot'
     */
    public Projectile shoot(SpriteBatch spriteBatch, College target, Vector3 targetPos) {
        System.out.println("shoot");
        Projectile proj = new Projectile(new Sprite(projectileImg), movementLayer, this, target, targetPos, true);
        proj.draw(spriteBatch);
        return proj;
    }

    public void endCombat() {
        inCombat = false;
    }

    
    /** 
     * @return Combat
     */
    public Combat inCombat() {
        return new Combat(inCombat, collegeCell, targetPos);
    }

    
    /** 
     * @return int
     */
    public int getMoveRadius() {
        return moveRadius;
    }

    
    /** 
     * @param dmg : the damage the player will take from being hit
     */
    public void hit(int dmg) {
        health -= dmg;
        System.out.println("Hit, health: " + health);

        if (health <= 0) {
            isDestroyed = true;
        }
    }

}
