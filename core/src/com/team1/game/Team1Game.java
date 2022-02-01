package com.team1.game;

import com.badlogic.gdx.Game;
import com.team1.game.screens.Play;
import com.team1.game.screens.Start;

/** Main game class */
public class Team1Game extends Game {

    @Override
    public void create() {
		// setScreen(new Play(this));
    setScreen(new Start(this));
    }

    @Override
    public void render() {
		super.render();
	}
    
    @Override
    public void dispose() {
		super.dispose();
    }
}
