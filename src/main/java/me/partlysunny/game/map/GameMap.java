package me.partlysunny.game.map;

import io.netty.buffer.ByteBuf;
import me.partlysunny.game.ColorPalette;
import me.partlysunny.ui.console.menu.InfoMenu;
import me.partlysunny.ui.console.menu.SideMenu;
import me.partlysunny.game.tile.Tile;
import me.partlysunny.game.tile.TileRegistry;
import me.partlysunny.game.unit.Unit;
import me.partlysunny.network.Serialisable;

import java.awt.*;

public class GameMap implements Serialisable {

    public ColorPalette colorPalette = ColorPalette.DEFAULT;

    private final Tile[][] map;

    private final SideMenu currentMenu = new InfoMenu();

    // HALF_WIDTH
    public static final int DISPLAY_SIZE = 6;

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


    public RenderableMap render(int player) {
        boolean[][] inVision = new boolean[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = getTile(x, y);
                Unit unit = tile.getUnit();
                if (unit == null) {
                    continue;
                }
                if (unit.getOwnerID() != player) {
                    continue;
                }
                if (unit.hasAttr("vision")) {
                    int visionRad = unit.getAttr("vision");
                    for (int i = x - visionRad; i <= x + visionRad; i++) {
                        for (int j = y - visionRad; j <= y + visionRad; j++) {
                            int distSq1 = (i - x) * (i - x) + (j - y) * (j - y);
                            int distSq2 = visionRad * visionRad;
                            if (distSq1 < distSq2) {
                                inVision[i][j] = true;
                            }
                        }
                    }
                }
            }
        }
        RenderableMap map = new RenderableMap(this);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (!inVision[x][y]) {
                    map.get(x, y).setTextColor(Color.GRAY);
                    map.get(x, y).setC('#');
                } else {
                    Tile tile = getTile(x, y);
                    if (tile != null) {
                        map.set(x, y, tile.render());
                    }
                }
            }
        }
        return map;
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

    public boolean summon(Unit unit, int x, int y) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        return map[x][y].setUnit(unit);
    }
}
