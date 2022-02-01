package com.team1.game.entities;

import java.util.Random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.team1.game.Combat;
import com.team1.game.screens.Play;

/** College class represents enemies for player to attack and defend against */
public class College {
    
    public int health;
    public int attackDmg;
    public String name;
    public boolean isDestroyed;
    public int attackRange = 3;
    public double timeUntilNextAttack;
    public double attackSpd = 2;

    private Cell cell;
    private int col;
    private int row;

    public College(String name, int health, int attackDmg) {
        this.name = name;
        this.health = health;
        this.attackDmg = attackDmg;
        timeUntilNextAttack = attackSpd;
    }

    
    /** 
     * @param col : Column college is in
     * @param row : Row the college is in
     */
    public void setTilePos(int col, int row) {
        this.col = col;
        this.row = row;
    }

    
    /** 
     * @param cell
     */
    public void setCell(Cell cell) {
        this.cell = cell; 
    }

    
    /** 
     * @return Cell
     */
    public Cell getCell() {
        return cell;
    }

    
    /** 
     * @param dmg : the damage the college will take when hit
     * @param player : hte player the damage will come from, will be used to add plunder to player
     */
    public void hit(int dmg, Player player) {
        health -= dmg;
        System.out.println("Hit, health: " + health);

        if (health <= 0) {
            isDestroyed = true;
            // cell.setTile(new tile)
        }
    }

    
    /** 
     * Testing to see if the player is in attack range
     * 
     * @param player : The player
     * @return boolean : true if college can attack player, false otherwise
     */
    public boolean playerInRange(Player player) {
        int playerCol = Math.round(Math.round(player.getX()) / Play.TILE_SIZE);
        int playerRow = Math.round(Math.round(player.getY()) / Play.TILE_SIZE);

        // Euclidean
        int dist = (int) Math.round(Math.sqrt(Math.pow(col - playerCol, 2) + Math.pow(row - playerRow, 2)));
        
        System.out.println("College: " + name + " (" + col + ", " + row + ") -> (" + playerCol + ", " + playerRow + ") = " + dist);
        System.out.println("attackRange: " + attackRange);

        return dist < attackRange;
    }

    
    /** 
     * Tell teh player they are being attacked to update in the render method
     * 
     * @param player : player beinng attacked
     */
    public void attack(Player player) {
        System.out.println(name + " attacking player");
        player.beingAttacked = new Combat(true, cell, new Vector3(player.getX(), player.getY(), 0));
        System.out.println(player.beingAttacked.getTargetPos());
    }

    
    /** 
     * 
     * 
     * @param spriteBatch : spriteBatch used to draw projectile
     * @param target : The college the projectile is aimed at
     * @param targetPos : The position of the target
     * @param projectileImg : Image of projectile - could make them unique for each college for ease of identification
     * @param movementLayer : Needed for projectile
     * @return Projectile : returns the projectile that was just created and 'shot'
     */
    public Projectile shoot(SpriteBatch spriteBatch, Player target, Vector3 targetPos, Texture projectileImg, TiledMapTileLayer movementLayer) {
        System.out.println(name + " shoot");
        Projectile proj = new Projectile(new Sprite(projectileImg), movementLayer, this, target, targetPos, false);
        proj.draw(spriteBatch);
        return proj;
    }

    
    /** 
     * @return float
     */
    public float getX() {
        return (float) col * Play.TILE_SIZE;
    }

    
    /** 
     * @return float
     */
    public float getY() {
        return (float) row * Play.TILE_SIZE;
    }

    
    /** 
     * @param player : player that will stop being attacked
     */
    public void stopAttacking(Player player) {
        player.beingAttacked = null;
    }

}
