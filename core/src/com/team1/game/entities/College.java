package com.team1.game.entities;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class College {
    
    public int health;
    public int attackDmg;
    public String name;

    private Cell cell;

    public College(String name, int health, int attackDmg) {
        this.name = name;
        this.health = health;
        this.attackDmg = attackDmg;
    }

    public void setCell(Cell cell) {
        this.cell = cell; 
    }

    public Cell getCell() {
        return cell;
    }

    public void hit(int dmg) {
        health -= dmg;
        System.out.println("Hit");
    }

}
