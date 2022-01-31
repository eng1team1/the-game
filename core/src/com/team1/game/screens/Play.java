package com.team1.game.screens;
 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.team1.game.Combat;
import com.team1.game.TiledMapStage;
import com.team1.game.entities.College;
import com.team1.game.entities.Player;
import com.team1.game.entities.Projectile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import java.util.ArrayList;

import javax.swing.plaf.synth.SynthSplitPaneUI;

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

    public OrthographicCamera camera;
    private float moveSpeed = 100;
    private float zoomSpeed = 1;
    // Do this better and also bound zoom
    private int boundLeft = Gdx.graphics.getWidth() / 2;
    private int boundRight = 960; // 20 * TILE_SIZE;
    private int boundBottom = Gdx.graphics.getHeight() / 2;
    private int boundTop = 1040; // 20 * TILE_SIZE;

    private Stage stage;
    private Vector3 mousePos;

    private TiledMap map;
    private TiledMapTileLayer movementLayer;
    private OrthogonalTiledMapRenderer renderer;

    private boolean foundCollegeInCombat = false;

    private ArrayList<Projectile> projectilesOnScreen;
    private double timeUntilNextAttack;
    double attackSpdDecrement = 0.01;

    @Override
    public void show() {
        TmxMapLoader loader = new TmxMapLoader();
        map = loader.load("maps/map.tmx");

        movementLayer = (TiledMapTileLayer) map.getLayers().get(0);

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
        camera.update();   

        colleges = new ArrayList<College>();

        initColleges(movementLayer);

        for (College c : colleges) {
            System.out.println(c.getCell());
        }
        
        player = new Player(new Sprite(new Texture("img/player.png")), movementLayer);

        timeUntilNextAttack = 0.01;
        projectilesOnScreen = new ArrayList<Projectile>();

        mousePos = new Vector3();

        stage = new TiledMapStage(map, player);
        stage.getViewport().setCamera(camera);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        //#region Camera Movement
        if (Gdx.input.isKeyPressed(Input.Keys.W) && camera.position.y < boundTop) {
            camera.translate(0, moveSpeed * Gdx.graphics.getDeltaTime());
        } else if (Gdx.input.isKeyPressed(Input.Keys.S) && camera.position.y > boundBottom) {
            camera.translate(0, -moveSpeed * Gdx.graphics.getDeltaTime());
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A) && camera.position.x > boundLeft) {
            camera.translate(-moveSpeed * Gdx.graphics.getDeltaTime(), 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D) && camera.position.x < boundRight) {
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
            System.out.println("moveTo mousePos: " + mousePos);
            player.moveTo(mousePos);
            renderer.getBatch().end();
            player.setMoveFlag(false);
            player.endCombat();
            foundCollegeInCombat = false;
        }
        
        Combat combat = player.inCombat();
        if (combat.getInCombat()) {
            Cell collegeCell = combat.getCollegeCell();
            College target = null;
            System.out.println("In combat");
            if (!foundCollegeInCombat) {
                for (int i = 0; i < colleges.size(); i++) {
                    System.out.println(collegeCell + " :: " + colleges.get(i).getCell());
                    if (collegeCell == colleges.get(i).getCell()) {
                        System.out.println("college: " + colleges.get(i).name);
                        target = colleges.get(i);
                        foundCollegeInCombat = true;
                    }
                }
            } 

            timeUntilNextAttack -= attackSpdDecrement;
            // System.out.println("time until next attack: " + timeUntilNextAttack);
            if (timeUntilNextAttack <= 0) {
                renderer.getBatch().begin();
                System.out.println("mousePos: " + mousePos);
                Projectile proj = player.shoot((SpriteBatch) renderer.getBatch(), target, combat.getTargetPos()); // new Vector3(mousePos.x, mousePos.y, 0));
                renderer.getBatch().end();
                projectilesOnScreen.add(proj);
                timeUntilNextAttack = (double) player.attackSpd;
            }
            
        }

        // TODO Make movement more fluid - e.g A*
        // Make it look better , e.g have player move through tiles

        // Add properties for enemy college or something on tile, also do stuff with different waters

        // Add stats and stuff to player and colleges

        // Add combat for colleges - left click college to target and start attacking

        // Add RNG e.g. make multiple maps and randomly choose which one to load
        // also random amount of xp

        // Upgrades?

        // Help system

        // Sort camera ?

        // System.out.println("projectile count: " + projectilesOnScreen.size());
        
        renderer.getBatch().begin();
        player.draw(renderer.getBatch());
        for (int i = 0; i < projectilesOnScreen.size(); i++) {
            Projectile projectile = projectilesOnScreen.get(i);
            System.out.println("projectiles loop");
            System.out.println("mousePos: " + mousePos);
            // projectile.draw(renderer.getBatch());
            projectile.update(Gdx.graphics.getDeltaTime(), (SpriteBatch) renderer.getBatch());

            if (projectile.getX() > boundRight || projectile.getX() < 0) {
                projectilesOnScreen.remove(i);
            } 

            if (projectile.getY() > boundTop || projectile.getY() < 0) {
                projectilesOnScreen.remove(i);
            }

            if (projectile.shouldRemove) {
                projectilesOnScreen.remove(i);
            }
        }
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
                    college.setCell(cell);
                    colleges.add(college);
                    System.out.println("College: " + college.name + "X: " + x + " Y: " + y);
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
        for (Projectile projectile : projectilesOnScreen) {
            projectile.getTexture().dispose();
        }
    }
    

}
