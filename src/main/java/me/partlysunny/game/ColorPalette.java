package me.partlysunny.game;

import me.partlysunny.game.map.RenderableMap;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ColorPalette {

    public static final ColorPalette DEFAULT = ColorPalette.of().defFg(Color.GRAY).tagFg("empty", Color.WHITE).tagFg("grass", Color.GREEN).tagFg("sand", Color.YELLOW);

    private Color defaultFgColor = Color.BLACK;
    private Color defaultBgColor = new Color(179, 179, 179);
    private final Map<String, Color> tagFgColors = new HashMap<>();
    private final Map<String, Color> tagBgColors = new HashMap<>();

    public static ColorPalette of() {
        return new ColorPalette();
    }

    public ColorPalette defFg(Color defaultColor) {
        this.defaultFgColor = defaultColor;
        return this;
    }

    public ColorPalette defBg(Color defaultColor) {
        this.defaultBgColor = defaultColor;
        return this;
    }

    public ColorPalette tagFg(String tag, Color color) {
        tagFgColors.put(tag, color);
        return this;
    }

    public ColorPalette tagBg(String tag, Color color) {
        tagBgColors.put(tag, color);
        return this;
    }

    public void mark(Taggable tile, RenderableMap.RenderableTile target) {
        Color fg = defaultFgColor;
        Color bg = defaultBgColor;
        for (String tag : tile.getTags()) {
            Color color = tagFgColors.get(tag);
            if (color != null) {
                fg = color;
            }
            color = tagBgColors.get(tag);
            if (color != null) {
                bg = color;
            }
        }
        target.setTextColor(fg).setHighlightColor(bg);
    }

}
