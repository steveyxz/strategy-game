package me.partlysunny.game;

import java.util.Set;

public class EmptyTile extends Tile {
    public EmptyTile(Map map) {
        super(map);
    }

    @Override
    protected char visual() {
        return '#';
    }

    @Override
    public Set<String> baseTags() {
        return Set.of("empty");
    }
}
