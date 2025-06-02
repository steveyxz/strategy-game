package me.partlysunny.game;

import io.netty.buffer.ByteBuf;
import me.partlysunny.game.map.MapGenerator;
import me.partlysunny.game.map.TestMapGenerator;
import me.partlysunny.game.menu.InfoMenu;
import me.partlysunny.game.menu.SideMenu;
import me.partlysunny.game.tile.Tile;
import me.partlysunny.game.tile.TileRegistry;
import me.partlysunny.network.Serialisable;
import org.jline.jansi.Ansi;

import java.util.List;

public class GameMap implements Serialisable {

    public ColorPalette colorPalette = ColorPalette.DEFAULT;

    private final Tile[][] map;

    private final SideMenu currentMenu = new InfoMenu();

    // HALF_WIDTHS
    private static final int DISPLAY_WIDTH = 30;
    private static final int DISPLAY_HEIGHT = 13;

    private final int width;
    private final int height;

    private final boolean serverSide;

    public GameMap(int width, int height, boolean serverSide) {
        map = new Tile[width][height];
        this.width = width;
        this.height = height;
        this.serverSide = serverSide;
        if (serverSide) {
            MapGenerator generator = new TestMapGenerator();
            generator.propagate(this);
        }
    }

    public Tile getTile(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null; // Out of bounds
        }
        return map[x][y];
    }

    public void setTile(int x, int y, Tile tile) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return;
        }
        map[x][y] = tile;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String render(int centreX, int centreY) {
        StringBuilder sb = new StringBuilder();
        List<String> menus = currentMenu.buildMenu(this);
        for (int y = centreY - DISPLAY_HEIGHT; y <= centreY + DISPLAY_HEIGHT; y++) {
            for (int x = centreX - DISPLAY_WIDTH; x <= centreX + DISPLAY_WIDTH; x++) {
                if (x < 0 || y < 0 || x >= width || y >= height) {
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

    // CREATE IS ONLY EVER CALLED AS A CLIENT!!!!
    // this is because we will never need to reconstruct the map as the server? TODO maybe this is necessary?
    public static GameMap create(ByteBuf data) {
        int width = data.readInt();
        int height = data.readInt();
        GameMap theGameMap = new GameMap(width, height, false);
        theGameMap.load(data);
        return theGameMap;
    }

    @Override
    public void load(ByteBuf buf) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = TileRegistry.generateTile(this, buf);
            }
        }
    }

    @Override
    public ByteBuf serialise(ByteBuf base) {
        base.writeInt(width);
        base.writeInt(height);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Tile tile = getTile(x, y);
                if (tile != null) {
                    base = TileRegistry.serializeTile(tile, base);
                }
            }
        }
        return base;
    }

    public boolean isServerSide() {
        return serverSide;
    }
}
