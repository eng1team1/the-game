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

        int currCol = Math.round(Math.round(getX()) / Play.TILE_SIZE);
        int currRow = Math.round(Math.round(getY()) / Play.TILE_SIZE);;

        int moveRadius = getMoveRadius();

        // Euclidean
        int dist = (int) Math.round(Math.sqrt(Math.pow(col - currCol, 2) + Math.pow(row - currRow, 2)));

        boolean canMove = movementLayer.getCell(col, row).getTile().getProperties().containsKey("traversable") && (dist < moveRadius);

        if (canMove) {
            setX(col * Play.TILE_SIZE);
            setY(row * Play.TILE_SIZE);
        }
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
