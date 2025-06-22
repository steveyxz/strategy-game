package me.partlysunny.ui;

import me.partlysunny.game.GameState;

public interface Renderer extends Runnable {

    /**
     * refreshes the current state - do this after receiving new map data
     */
    void refresh(GameState newState);

    /**
     *
     */
    void frame();

}
