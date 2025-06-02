package me.partlysunny.game;

import me.partlysunny.game.tile.Tile;
import org.jline.jansi.Ansi;

import java.util.HashMap;
import java.util.Map;

public class ColorPalette {

    private static final Ansi ansi = Ansi.ansi();

    public static final ColorPalette DEFAULT = ColorPalette.of().def(ansi.fgDefault()).tag("empty", ansi.fgDefault()).tag("ground", ansi.fgBrightBlack());

    private Ansi defaultColor = Ansi.ansi().fg(Ansi.Color.DEFAULT);
    private Map<String, Ansi> tagColors = new HashMap<>();

    public static ColorPalette of() {
        return new ColorPalette();
    }

    public ColorPalette def(Ansi defaultColor) {
        this.defaultColor = defaultColor;
        return this;
    }

    public ColorPalette tag(String tag, Ansi color) {
        tagColors.put(tag, color);
        return this;
    }

    public String mark(Taggable tile) {
        String base = defaultColor.toString();
        for (String tag : tile.getTags()) {
            Ansi color = tagColors.get(tag);
            if (color != null) {
                base = color.toString();
            }
        }
        return base;
    }

}
