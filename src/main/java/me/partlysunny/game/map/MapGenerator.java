package me.partlysunny.game.map;

public interface MapGenerator {

    void propagate(GameMap map);

    void setSeed(long seed);

    long getSeed();

}
