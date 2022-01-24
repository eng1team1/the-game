package com.team1.game;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class Combat {
    boolean inCombat;
    Cell collegeCell;

    public Combat(boolean inCombat, Cell collegeCell) {
        this.inCombat = inCombat;
        this.collegeCell = collegeCell;
    }

    public boolean getInCombat() {
        return inCombat;
    }

    public Cell getCollegeCell() {
        return collegeCell;
    }
}
