package me.partlysunny.game.unit;

import io.netty.buffer.ByteBuf;
import me.partlysunny.game.map.GameMap;
import me.partlysunny.game.unit.type.UnknownCitizen;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class UnitRegistry {

    private static final Map<Integer, Class<? extends Unit>> units = new HashMap<>();
    private static final Map<Class<? extends Unit>, Integer> unitsByClass = new HashMap<>();

    public enum UnitType {
        UNKNOWN_CITIZEN
    }

    private static void registerUnit(UnitType type, Class<? extends Unit> unit) {
        int id = type.ordinal();
        units.put(id, unit);
        unitsByClass.put(unit, id);
    }

    static {
        registerUnit(UnitType.UNKNOWN_CITIZEN, UnknownCitizen.class);
    }

    public static Unit generate(ByteBuf data, GameMap map, boolean serverSide) {
        int type = data.readInt();
        if (units.containsKey(type)) {
            Class<? extends Unit> clazz = units.get(type);
            Unit unit = null;
            try {
                unit = clazz.getDeclaredConstructor(GameMap.class, Boolean.class).newInstance(map, serverSide);
                unit.load(data);
                return unit;
            } catch (InstantiationException | InvocationTargetException | IllegalAccessException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        throw new IllegalStateException("Unknown unit type: " + type);
    }

}
