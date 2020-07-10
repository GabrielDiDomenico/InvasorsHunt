package com.gddomenico.ih.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gddomenico.ih.handlers.GameStateManager;
import com.gddomenico.ih.invasorsHunt;

public abstract class GameState {
    protected GameStateManager gsm;
    protected invasorsHunt game;

    protected SpriteBatch sb;
    protected OrthographicCamera cam;
    protected OrthographicCamera hudCam;

    protected GameState(GameStateManager gsm) {
        this.gsm = gsm;
        game = gsm.game();
        sb = game.getSpriteBatch();
        cam = game.getCam();
        hudCam = game.getHudCam();
    }

    public abstract void handleInput();
    public abstract void update(float dt);
    public abstract void render();
    public abstract void dispose();
}
