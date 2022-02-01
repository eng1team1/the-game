package com.team1.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.team1.game.entities.Player;
import com.team1.game.screens.Play;

/** Click listener for tiles on tiledmap */
public class TiledMapClickListener extends ClickListener {

    private TiledMapActor actor;

    private Player player;

    private Team1Game main = (Team1Game) Gdx.app.getApplicationListener();
    private Play playScreen;
    private Camera camera;

    public TiledMapClickListener(TiledMapActor actor, Player player) {
        this.actor = actor;
        this.player = player;
        playScreen = (Play) main.getScreen();
        camera = playScreen.camera;
    }

    
    /** 
     * @param event
     * @param x
     * @param y
     */
    @Override
    public void clicked(InputEvent event, float x, float y) {
        // System.out.println(actor.cell + " has been clicked at X: " + x + " Y: " + y);

        Vector3 pos = camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        System.out.println(pos);

        // Checking if player can move to tile
        if (actor.cell.getTile().getProperties().containsKey("traversable")) {  
            player.setMoveFlag(true);
        }

        // Checking if tile is college, if so player start attacking
        if (actor.cell.getTile().getProperties().containsKey("college")) {
            System.out.println("clicked college");
            System.out.println(player.canReach(pos));
            if (player.canReach(pos)) {
                player.startCombat(actor.cell, pos);
            }
        }
    }
}
