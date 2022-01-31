package com.team1.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector3;

public class Combat {
    boolean inCombat;
    Cell collegeCell;
    Vector3 targetPos;

    public Combat(boolean inCombat, Cell collegeCell, Vector3 targetPos) {
        this.inCombat = inCombat;
        this.collegeCell = collegeCell;
        this.targetPos = targetPos;
    }

    public boolean getInCombat() {
        return inCombat;
    }

    public Cell getCollegeCell() {
        return collegeCell;
    }

    public Vector3 getTargetPos() {
        return targetPos;
    }
}
