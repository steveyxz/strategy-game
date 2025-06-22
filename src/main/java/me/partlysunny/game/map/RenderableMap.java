package me.partlysunny.game.map;

import java.awt.*;

public class RenderableMap {

    public int displaySize() {
        return GameMap.DISPLAY_SIZE * 2 + 1;
    }

    public static class RenderableTile {
        //int x, int y, char c, Color textColor, Color highlightColor
        private char c = ' ';
        private Color textColor = Color.GRAY;
        private Color highlightColor = null;

        public RenderableTile() {
        }

        public char getC() {
            return c;
        }

        public RenderableTile setC(char c) {
            this.c = c;
            return this;
        }

        public Color getTextColor() {
            return textColor;
        }

        public RenderableTile setTextColor(Color textColor) {
            this.textColor = textColor;
            return this;
        }

        public Color getHighlightColor() {
            return highlightColor;
        }

        public RenderableTile setHighlightColor(Color highlightColor) {
            this.highlightColor = highlightColor;
            return this;
        }
    }

    private GameMap parent;
    private RenderableTile[][] renderableTiles;

    public RenderableMap(GameMap parent) {
        this.parent = parent;
        renderableTiles = new RenderableTile[parent.getWidth()][parent.getHeight()];
        for (int i = 0; i < parent.getWidth(); i++) {
            for (int j = 0; j < parent.getHeight(); j++) {
                renderableTiles[i][j] = new RenderableTile();
            }
        }
    }

    public void set(int x, int y, RenderableTile t) {
        renderableTiles[x][y] = t;
    }

    public RenderableTile get(int x, int y) {
        return renderableTiles[x][y];
    }

    public int height() {
        return renderableTiles[0].length;
    }

    public int width() {
        return renderableTiles.length;
    }

}
