package me.partlysunny.game.unit;

import io.netty.buffer.ByteBuf;
import me.partlysunny.game.GameMap;
import me.partlysunny.game.Taggable;
import me.partlysunny.game.event.EventExecutor;
import me.partlysunny.game.event.GameListener;
import me.partlysunny.network.Serialisable;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Unit implements Serialisable, GameListener, Taggable {

    protected Map<String, Object> attributes;
    protected Set<String> tags;
    protected int ownerID;
    protected int unitType;
    protected GameMap gameMap;

    public Unit(GameMap gameMap) {
        this(gameMap, false);
    }

    public Unit(GameMap gameMap, boolean real) {
        this.gameMap = gameMap;
        if (real) {
            EventExecutor.register(this);
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

    public String[] getTags() {
        if (tags == null) {
            return new String[0];
        }
        return tags.toArray(new String[0]);
    }

    public abstract char displayChar();
    public abstract void initTags();
    public abstract void initAttributes();

    public String render() {
        return gameMap.colorPalette.mark(this) + displayChar();
    }

    @Override
    public void load(ByteBuf buf) {
        // again, we skip reading the ID as the registry needs to read the ID to be able to properly instantiate the Unit
        ownerID = buf.readInt();
        tags = new HashSet<>();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            int tagSize = buf.readInt();
            tags.add(String.valueOf(buf.readCharSequence(tagSize, StandardCharsets.UTF_8)));
        }
        int attributeSize = buf.readInt();
        attributes = new HashMap<>();
        for (int i = 0; i < attributeSize; i++) {
            int type = buf.readInt();
            // 0 is int, 1 is string
            if (type == 0) {
                int keySize = buf.readInt();
                String key = String.valueOf(buf.readCharSequence(keySize, StandardCharsets.UTF_8));
                int value = buf.readInt();
                attributes.put(key, value);
            } else if (type == 1) {
                int keySize = buf.readInt();
                String key = String.valueOf(buf.readCharSequence(keySize, StandardCharsets.UTF_8));
                int valueSize = buf.readInt();
                String value = String.valueOf(buf.readCharSequence(valueSize, StandardCharsets.UTF_8));
                attributes.put(key, value);
            }
        }
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        base.writeInt(unitType);
        base.writeInt(ownerID);
        base.writeInt(tags.size());
        for (String tag : tags) {
            base.writeInt(tag.getBytes(StandardCharsets.UTF_8).length);
            base.writeCharSequence(tag, StandardCharsets.UTF_8);
        }
        base.writeInt(attributes.size());
        for (Map.Entry<String, Object> entry : attributes.entrySet()) {
            if (entry.getValue() instanceof Integer) {
                base.writeInt(0);
                base.writeInt(entry.getKey().getBytes(StandardCharsets.UTF_8).length);
                base.writeCharSequence(entry.getKey(), StandardCharsets.UTF_8);
                base.writeInt((int) entry.getValue());
            } else if (entry.getValue() instanceof String) {
                base.writeInt(1);
                base.writeInt(entry.getKey().getBytes(StandardCharsets.UTF_8).length);
                base.writeCharSequence(entry.getKey(), StandardCharsets.UTF_8);
                base.writeInt(((String) entry.getValue()).getBytes(StandardCharsets.UTF_8).length);
                base.writeCharSequence((String) entry.getValue(), StandardCharsets.UTF_8);
            }
        }
        return base;
    }

}
