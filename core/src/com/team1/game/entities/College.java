package com.team1.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;
import com.team1.game.Combat;
import com.team1.game.screens.Play;

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

    public void setTilePos(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public void setCell(Cell cell) {
        this.cell = cell; 
    }

    public Cell getCell() {
        return cell;
    }

    public void hit(int dmg) {
        health -= dmg;
        System.out.println("Hit, health: " + health);

        if (health <= 0) {
            isDestroyed = true;
            // cell.setTile(new tile)
        }
    }

    public boolean playerInRange(Player player) {
        int playerCol = Math.round(Math.round(player.getX()) / Play.TILE_SIZE);
        int playerRow = Math.round(Math.round(player.getY()) / Play.TILE_SIZE);

        // Euclidean
        int dist = (int) Math.round(Math.sqrt(Math.pow(col - playerCol, 2) + Math.pow(row - playerRow, 2)));
        
        System.out.println("College: " + name + " (" + col + ", " + row + ") -> (" + playerCol + ", " + playerRow + ") = " + dist);
        System.out.println("attackRange: " + attackRange);

        return dist < attackRange;
    }

    public void attack(Player player) {
        System.out.println(name + " attacking player");
        player.beingAttacked = new Combat(true, cell, new Vector3(player.getX(), player.getY(), 0));
        System.out.println(player.beingAttacked.getTargetPos());
    }

    public Projectile shoot(SpriteBatch spriteBatch, Player target, Vector3 targetPos, Texture projectileImg, TiledMapTileLayer movementLayer) {
        System.out.println(name + " shoot");
        Projectile proj = new Projectile(new Sprite(projectileImg), movementLayer, this, target, targetPos, false);
        proj.draw(spriteBatch);
        return proj;
    }

    public float getX() {
        return (float) col * Play.TILE_SIZE;
    }

    public float getY() {
        return (float) row * Play.TILE_SIZE;
    }

}
