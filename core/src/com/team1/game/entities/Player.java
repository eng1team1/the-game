package com.team1.game.entities;

import javax.swing.SpinnerDateModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.team1.game.Combat;
import com.team1.game.screens.Play;

public class Player extends Sprite {

    private TiledMapTileLayer movementLayer;
    
    Cell collegeCell = null;
    boolean inCombat = false;
    boolean moveFlag = false;
    private int moveRadius = 4;

    int health = 100;
    int attackDmg = 20;
    // int moveSpeed = 1; // possibly? for when better movement has been implemented

    public Player(Sprite sprite, TiledMapTileLayer movementLayer) {
        super(sprite);
        this.movementLayer = movementLayer;
    }

    public void draw(SpriteBatch spriteBatch) {
        // update(Gdx.graphics.getDeltaTime());
        super.draw(spriteBatch);
    }

    public void update(float delta) {
        
    }

    public void moveTo(Vector3 mousePos) {
        // might want to use https://github.com/xaguzman/pathfinding for pathfinding, would have to reconfigure some of the tiledmap stuff but its just different classes
        // or could just create a duplicate graph and run A* on that then tell paly to move to the tiles corresponding to it.
        int col = Math.round(Math.round(mousePos.x) / Play.TILE_SIZE);
        int row = Math.round(Math.round(mousePos.y) / Play.TILE_SIZE);

        boolean canMove = movementLayer.getCell(col, row).getTile().getProperties().containsKey("traversable") && canReach(mousePos);

        if (canMove) {
            setX(col * Play.TILE_SIZE);
            setY(row * Play.TILE_SIZE);
        }
    }

    public boolean canReach(Vector3 pos) {
        int col = Math.round(Math.round(pos.x) / Play.TILE_SIZE);
        int row = Math.round(Math.round(pos.y) / Play.TILE_SIZE);

        int currCol = Math.round(Math.round(getX()) / Play.TILE_SIZE);
        int currRow = Math.round(Math.round(getY()) / Play.TILE_SIZE);;

        // Euclidean
        int dist = (int) Math.round(Math.sqrt(Math.pow(col - currCol, 2) + Math.pow(row - currRow, 2)));

        System.out.println("(" + currCol + ", " + currRow + ") -> (" + col + ", " + row + ") = " + dist);

        return dist < moveRadius;
    }
    
    public void setMoveFlag(boolean flag) {
        moveFlag = flag;
    }

    public boolean getMoveFlag() {
        return moveFlag;
    }

    public void startCombat(Cell collegeCell) {
        inCombat = true;
        this.collegeCell = collegeCell;
    }

    public void endCombat() {
        inCombat = false;
    }

    public Combat inCombat() {
        return new Combat(inCombat, collegeCell);
    }

    public int getMoveRadius() {
        return moveRadius;
    }

}
