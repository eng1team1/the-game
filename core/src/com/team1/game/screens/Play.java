package com.team1.game.screens;
 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.team1.game.TiledMapStage;
import com.team1.game.entities.College;
import com.team1.game.entities.Player;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Play implements Screen {

    public static final int TILE_SIZE = 64;

    private Player player;
    private ArrayList<College> colleges;

    OrthographicCamera camera;
    private float moveSpeed = 100;
    private float zoomSpeed = 1;

    private Stage stage;
    private Vector3 mousePos;

    private TiledMap map;
    private TiledMapTileLayer movementLayer;
    private OrthogonalTiledMapRenderer renderer;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/map.tmx");

        movementLayer = (TiledMapTileLayer) map.getLayers().get(0);

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.update();   

        initColleges(movementLayer);
        
        player = new Player(new Sprite(new Texture("img/player.png")), movementLayer);

        mousePos = new Vector3();

        stage = new TiledMapStage(map, player);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //#region Camera Movement
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            camera.translate(0, moveSpeed * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            camera.translate(0, -moveSpeed * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.translate(-moveSpeed * Gdx.graphics.getDeltaTime(), 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            camera.translate(moveSpeed * Gdx.graphics.getDeltaTime(), 0);
        }

        if(Gdx.input.isKeyPressed((Input.Keys.UP))){
            camera.zoom -= zoomSpeed * Gdx.graphics.getDeltaTime();
        }
        else if(Gdx.input.isKeyPressed((Input.Keys.DOWN))){
            camera.zoom += zoomSpeed * Gdx.graphics.getDeltaTime();
        }
        //#endregion
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        renderer.setView(camera);
        renderer.render();

        stage.act();

        if (player.getMoveFlag()) {
            camera.unproject(mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            renderer.getBatch().begin();
            player.moveTo(mousePos);
            renderer.getBatch().end();
            player.setMoveFlag(false);
        }
        
        /*
        if (Gdx.input.isButtonJustPressed(Buttons.LEFT)) {
            camera.unproject(mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            System.out.println("X Input: " + mousePos.x);

            renderer.getBatch().begin();
            player.moveTo(mousePos);
            renderer.getBatch().end();
        } */

        // TODO Make movement more fluid - e.g A*
        // Make it look better , e.g have player move through tiles

        // Add properties for enemy college or something on tile, also do stuff with different waters

        // Add stats and stuff to player and colleges

        // Add combat for colleges - right click college to target and start attacking

        // Add RNG e.g. make multiple maps and randomly choose which one to load
        // also random amount of xp

        // Upgrades?

        // Help system

        // Sort camera ?
        
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        renderer.getBatch().end();
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
        
    }

    private void initColleges(TiledMapTileLayer tiledLayer) {
        for (int x = 0; x < tiledLayer.getWidth(); x++) {
            for (int y = 0; y < tiledLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = tiledLayer.getCell(x, y);
                if (cell.getTile().getProperties().containsKey("college")) {
                    College college = new College(cell.getTile().getProperties().get("college").toString(), 100, 34);
                    colleges.add(college);
                }
                
            }
        }
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void hide() {
        dispose();
        
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
        
    }
    

}
