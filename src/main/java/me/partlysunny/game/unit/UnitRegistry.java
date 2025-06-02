package me.partlysunny.game.unit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.partlysunny.game.GameMap;
import me.partlysunny.network.Packet;
import me.partlysunny.network.client.ServerboundIdRequest;
import me.partlysunny.network.server.ClientboundIdResponse;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UnitRegistry {

    private static final Map<Integer, Class<? extends Unit>> units = new HashMap<>();
    private static final Map<Class<? extends Unit>, Integer> unitsByClass = new HashMap<>();

    static {

    }

    public static Unit generate(ByteBuf data, GameMap map, boolean serverSide) {
        int type = data.readInt();
        if (units.containsKey(type)) {
            Class<? extends Unit> clazz = units.get(type);
            Unit unit = null;
            try {
                unit = clazz.getDeclaredConstructor(GameMap.class, Boolean.class).newInstance(map, serverSide);
                return unit;
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("Unknown packet type: " + type);
    }

}
