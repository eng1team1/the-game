package com.team1.game;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team1.game.entities.Player;

public class TiledMapClickListener extends ClickListener {

    private TiledMapActor actor;

    private Player player;

    public TiledMapClickListener(TiledMapActor actor, Player player) {
        this.actor = actor;
        this.player = player;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        System.out.println(actor.cell + " has been clicked.");

        // Checking if play can move to tile
        if (actor.cell.getTile().getProperties().containsKey("traversable")) {  
            player.setMoveFlag(true);
        }

        if (actor.cell.getTile().getProperties().containsKey("college")) {
            player.startCombat(actor.cell);
        }
    }
}
