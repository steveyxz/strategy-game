package me.partlysunny.game;

import java.util.HashSet;
import java.util.Set;

public abstract class Tile {

    protected Set<String> tags;
    protected Map map;

    public Tile(Map map) {
        this.map = map;
        tags = baseTags();
        if (tags == null) {
            tags = new HashSet<>();
        }
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void removeTag(String tag) {
        tags.remove(tag);
    }

    public boolean hasTag(String tag) {
        return tags.contains(tag);
    }

    public String render() {
        return map.colorPalette.mark(this) + visual();
    }

    protected abstract char visual();
    public abstract Set<String> baseTags();

    public String[] getTags() {
        if (tags == null) {
            return new String[0];
        }
        return tags.toArray(new String[0]);
    }
}
