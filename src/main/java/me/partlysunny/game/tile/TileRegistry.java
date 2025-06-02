package me.partlysunny.game.tile;

import io.netty.buffer.ByteBuf;
import me.partlysunny.game.GameMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TileRegistry {

    private static final Map<Integer, Tile.TileData> data = new HashMap<>();

    public enum TileType {
        EMPTY,
        GRASS
    }

    static {
        data.put(TileType.EMPTY.ordinal(), new Tile.TileData(0, Set.of("empty"), '.'));
        data.put(TileType.GRASS.ordinal(), new Tile.TileData(1, Set.of("grassy", "ground"), '^'));
    }

    public static Tile generateTile(int tileType, GameMap gameMap, boolean serverSide) {
        if (!data.containsKey(tileType)) {
            throw new IllegalStateException("Tile type " + tileType + " not found");
        }
        return new Tile(gameMap, data.get(tileType), serverSide);
    }

    public static Tile generateTile(TileType tileType, GameMap gameMap, boolean serverSide) {
        return generateTile(tileType.ordinal(), gameMap, serverSide);
    }

    public static Tile generateTile(GameMap gameMap, ByteBuf data) {
        // again, this would never be at the server level, as the client would never send map data over to the server
        int type = data.readInt();
        Tile t = generateTile(type, gameMap, false);
        t.load(data);
        return t;
    }

    public static ByteBuf serializeTile(Tile tile, ByteBuf base) {
        return tile.serialise(base);
    }

}
