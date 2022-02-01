package com.team1.game;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.team1.game.entities.Player;

/** Stage for tiled map */
public class TiledMapStage extends Stage {

    private TiledMap tiledMap;
    private Player player;

    public TiledMapStage(TiledMap tiledMap, Player player) {
        this.tiledMap = tiledMap;

        this.player = player;

        for (MapLayer layer : tiledMap.getLayers()) {
            TiledMapTileLayer tiledLayer = (TiledMapTileLayer)layer;
            createActorsForLayer(tiledLayer, player);
        }
    }

    /** 
     * @param tiledLayer
     * @param player
     */
    private void createActorsForLayer(TiledMapTileLayer tiledLayer, Player player) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                TiledMapActor actor = new TiledMapActor(tiledMap, tiledLayer, cell);
                actor.setBounds(x * tiledLayer.getTileWidth(), y * tiledLayer.getTileHeight(), tiledLayer.getTileWidth(),
                        tiledLayer.getTileHeight());
                addActor(actor);
                EventListener eventListener = new TiledMapClickListener(actor, player);
                actor.addListener(eventListener);
            }
        }
    }
}