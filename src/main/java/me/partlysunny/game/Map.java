package me.partlysunny.game;

import me.partlysunny.ColorPalette;
import org.jline.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;

public class Map {

    public ColorPalette colorPalette = ColorPalette.DEFAULT;

    private final List<List<Tile>> map;

    // HALF_WIDTHS
    private static final int DISPLAY_WIDTH = 25;
    private static final int DISPLAY_HEIGHT = 10;

    public Map(int width, int height) {
        map = new ArrayList<>(width);
        for (int x = 0; x < width; x++) {
            List<Tile> column = new ArrayList<>(height);
            for (int y = 0; y < height; y++) {
                Tile tile = new EmptyTile(this);
                column.add(tile);
            }
            map.add(column);
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= map.size() || y < 0 || y >= map.get(x).size()) {
            return null; // Out of bounds
        }
        return map.get(x).get(y);
    }

    public int getHeight() {
        return map.getFirst().size();
    }

    public int getWidth() {
        return map.size();
    }

    public String render(int centreX, int centreY) {
        StringBuilder sb = new StringBuilder();
        for (int y = centreY - DISPLAY_HEIGHT; y <= centreY + DISPLAY_HEIGHT; y++) {
            for (int x = centreX - DISPLAY_WIDTH; x <= centreX + DISPLAY_WIDTH; x++) {
                if (x < 0 || y < 0 || x >= map.size() || y >= map.get(x).size()) {
                    sb.append(" ");
                    continue;
                }
                Tile tile = getTile(x, y);
                if (x == centreX && y == centreY) {
                    sb.append(Ansi.ansi().bgBrightRed());
                }
                if (tile != null) {
                    sb.append(tile.render());
                } else {
                    sb.append(" "); // Empty space for out of bounds
                }
                sb.append(Ansi.ansi().fgDefault());
                sb.append(Ansi.ansi().bgDefault());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
