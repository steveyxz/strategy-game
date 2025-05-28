package me.partlysunny.game;

import me.partlysunny.ColorPalette;
import org.jline.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;

public class Map {

    public ColorPalette colorPalette = ColorPalette.DEFAULT;

    private final List<List<Tile>> map;


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

    public String render() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.getFirst().size(); y++) {
            for (int x = 0; x < map.size(); x++) {
                Tile tile = getTile(x, y);
                if (tile != null) {
                    sb.append(tile.render());
                } else {
                    sb.append(" "); // Empty space for out of bounds
                }
                sb.append(Ansi.ansi().fgDefault());
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
