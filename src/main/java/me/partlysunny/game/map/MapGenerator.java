package me.partlysunny.game.map;

import me.partlysunny.game.GameMap;

public interface MapGenerator {

    void propagate(GameMap map);

    void setSeed(long seed);

    long getSeed();

}
