package me.partlysunny.game.map;

import me.partlysunny.game.tile.TileRegistry;

import java.util.Random;

public class TestMapGenerator implements MapGenerator {

    private long seed;
    private final Random random;

    public TestMapGenerator() {
        this(System.currentTimeMillis());
    }

    public TestMapGenerator(long seed) {
        this.seed = seed;
        random = new Random(seed);
        random.nextInt();
        random.nextInt();
        random.nextInt();
    }

    @Override
    public void propagate(GameMap map) {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                if (random.nextBoolean()) {
                    map.setTile(x, y, TileRegistry.generateTile(TileRegistry.TileType.SAND, map, true));
                } else {
                    map.setTile(x, y, TileRegistry.generateTile(TileRegistry.TileType.GRASS, map, true));
                }
            }
        }
    }

    @Override
    public void setSeed(long seed) {
        this.seed = seed;
    }

    @Override
    public long getSeed() {
        return seed;
    }
}
