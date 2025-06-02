package me.partlysunny.game;

import io.netty.buffer.ByteBuf;
import me.partlysunny.network.Serialisable;

import java.util.HashMap;

public class GameState implements Serialisable {

    public GameMap gameMap;
    public HashMap<Integer, Integer> scores = new HashMap<>();

    @Override
    public void load(ByteBuf buf) {
        gameMap = GameMap.create(buf);
        // load scores here TODO
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        // write scores here TODO
        return gameMap.serialise(base);
    }
}
