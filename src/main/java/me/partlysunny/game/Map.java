package me.partlysunny.game;

import me.partlysunny.ColorPalette;
import me.partlysunny.game.menu.InfoMenu;
import me.partlysunny.game.menu.SideMenu;
import org.jline.jansi.Ansi;

import java.util.ArrayList;
import java.util.List;

public class Map {

    public ColorPalette colorPalette = ColorPalette.DEFAULT;

    private final List<List<Tile>> map;

    private SideMenu currentMenu = new InfoMenu();

    // HALF_WIDTHS
    private static final int DISPLAY_WIDTH = 30;
    private static final int DISPLAY_HEIGHT = 13;

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
        List<String> menus = currentMenu.buildMenu(this);
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
            int trueY = y - (centreY - DISPLAY_HEIGHT);
            sb.append(menus.get(trueY));
            sb.append(Ansi.ansi().fgDefault());
            sb.append(Ansi.ansi().bgDefault());
            sb.append("\n");
        }
        return sb.toString();
    }
}
