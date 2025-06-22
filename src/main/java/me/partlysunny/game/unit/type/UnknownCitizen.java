package me.partlysunny.game.unit.type;

import me.partlysunny.game.map.GameMap;
import me.partlysunny.game.unit.Unit;
import me.partlysunny.game.unit.UnitRegistry;

public class UnknownCitizen extends Unit {

    public UnknownCitizen(GameMap gameMap, Boolean real) {
        super(gameMap, real);
    }

    public UnknownCitizen(GameMap gameMap) {
        super(gameMap);
    }

    @Override
    public char displayChar() {
        return 'O';
    }

    @Override
    public void initTags() {
        tags.add("unit");
    }

    @Override
    public void initAttributes() {
        attributes.put("moveDist", 3);
        attributes.put("vision", 4);
    }

    @Override
    public UnitRegistry.UnitType getType() {
        return UnitRegistry.UnitType.UNKNOWN_CITIZEN;
    }


}
