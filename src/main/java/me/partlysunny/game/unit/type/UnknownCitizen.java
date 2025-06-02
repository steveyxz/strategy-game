package me.partlysunny.game.unit.type;

import me.partlysunny.game.GameMap;
import me.partlysunny.game.unit.Unit;

public class UnknownCitizen extends Unit {
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
    }


}
