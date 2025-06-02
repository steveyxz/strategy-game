package me.partlysunny.game.tile;

import io.netty.buffer.ByteBuf;
import me.partlysunny.game.GameMap;
import me.partlysunny.game.Taggable;
import me.partlysunny.game.unit.Unit;
import me.partlysunny.game.unit.UnitRegistry;
import me.partlysunny.network.Serialisable;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

public class Tile implements Serialisable, Taggable {

    public record TileData(int typeID, Set<String> tags, char display) {
    }

    protected Set<String> tags;
    protected GameMap gameMap;
    // can be null
    protected Unit unit;
    protected char display;
    protected int typeID;
    protected boolean serverSide;

    public Tile(GameMap gameMap, TileData tileData, boolean serverSide) {
        this.serverSide = serverSide;
        this.gameMap = gameMap;
        if (tileData == null) {
            tags = new HashSet<>();
        } else {
            this.tags = new HashSet<>(tileData.tags);
            this.display = tileData.display;
            this.typeID = tileData.typeID;
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
        if (unit != null) {
            return unit.render();
        }
        return gameMap.colorPalette.mark(this) + display;
    }

    public String[] getTags() {
        if (tags == null) {
            return new String[0];
        }
        return tags.toArray(new String[0]);
    }

    @Override
    public void load(ByteBuf buf) {
        // this will be loaded AFTER tileType is already loaded in, no need to read in the ID
        int tagsSize = buf.readInt();
        for (int i = 0; i < tagsSize; i++) {
            // read number of 8 byte strings
            int stringLength = buf.readInt();
            String s = (String) buf.readCharSequence(stringLength, StandardCharsets.UTF_8);
            tags.add(s);
        }
        boolean isUnit = buf.readBoolean();
        if (isUnit) {
            // now we load unit details from UnitRegistry
            // this again will always be not serverside!!
            unit = UnitRegistry.generate(buf, gameMap, false);
        }

    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        base.writeInt(typeID);
        base.writeInt(tags.size());
        for (String tag : tags) {
            base.writeInt(tag.getBytes(StandardCharsets.UTF_8).length);
            base.writeCharSequence(tag, StandardCharsets.UTF_8);
        }
        if (unit == null) {
            base.writeBoolean(false);
        } else {
            base.writeBoolean(true);
            unit.serialise(base);
        }
        return base;
    }
}
