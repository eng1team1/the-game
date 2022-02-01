package com.team1.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;

/** Struct for combat */
public class Combat {
    boolean inCombat;
    Cell collegeCell;
    Vector3 targetPos;

    public Combat(boolean inCombat, Cell collegeCell, Vector3 targetPos) {
        this.inCombat = inCombat;
        this.collegeCell = collegeCell;
        this.targetPos = targetPos;
    }

    
    /** 
     * @return boolean
     */
    public boolean getInCombat() {
        return inCombat;
    }

    
    /** 
     * @return Cell
     */
    public Cell getCollegeCell() {
        return collegeCell;
    }

    
    /** 
     * @return Vector3
     */
    public Vector3 getTargetPos() {
        return targetPos;
    }
}
