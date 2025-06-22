package me.partlysunny.game.ui.window;

import me.partlysunny.TUtil;
import me.partlysunny.game.map.GameMap;
import me.partlysunny.game.map.RenderableMap;
import me.partlysunny.network.client.ClientLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CharMapTable extends JPanel implements MouseListener {

    private static final Color SELECTOR_COLOR = new Color(179, 62, 62);
    private RenderableMap map;

    private JLabel waitingLabel;

    public CharMapTable() {
        addMouseListener(this);
        addMouseListener(new MouseCommands());
        addMouseMotionListener(new MouseCommands());
        waitingLabel = new JLabel("Server is loading... type `start` in server to begin");
        waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
        waitingLabel.setVerticalAlignment(SwingConstants.CENTER);
        add(waitingLabel);
    }

    public void setMap(RenderableMap map) {
        this.map = map;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        setSize(getParent().getWidth() / 3 * 2, getParent().getWidth() / 3 * 2);
        g.clearRect(0, 0, getWidth(), getHeight());
        if (map == null) {
            waitingLabel.setVisible(true);
            return;
        }
        waitingLabel.setVisible(false);
        int width = getWidth();
        int height = getHeight();
        g.setColor(Color.black);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        g.drawRect(0, 0, width, height);

        int cellSize = width / map.displaySize();
        g.setFont(new Font("Monospaced", Font.BOLD, cellSize));

        FontMetrics fm = g.getFontMetrics();
        
        for (int mapX = ClientLoop.cameraX - GameMap.DISPLAY_SIZE; mapX <= ClientLoop.cameraX + GameMap.DISPLAY_SIZE; mapX++) {
            for (int mapY = ClientLoop.cameraY - GameMap.DISPLAY_SIZE; mapY <= ClientLoop.cameraY + GameMap.DISPLAY_SIZE; mapY++) {
                if (mapY < 0 || mapX < 0 || mapY >= map.height() || mapX >= map.width()) {
                    continue;
                }
                int displayY = (mapY - ClientLoop.cameraY + GameMap.DISPLAY_SIZE) * cellSize;
                int displayX = (mapX - (ClientLoop.cameraX - GameMap.DISPLAY_SIZE)) * cellSize;
                RenderableMap.RenderableTile tile = map.get(mapX, mapY);
                // Draw background
                if (tile.getHighlightColor() == null) {
                    g.setColor(Color.black);
                } else {
                    g.setColor(tile.getHighlightColor());
                }
                if (mapX == ClientLoop.selectedX && mapY == ClientLoop.selectedY) {
                    g.setColor(SELECTOR_COLOR);
                }
                g.fillRect(displayX, displayY, cellSize, cellSize);

                // Draw character centered in cell
                g.setColor(tile.getTextColor());
                char ch = tile.getC();
                int charWidth = fm.charWidth(ch);
                int charHeight = fm.getAscent(); // baseline to top

                int charX = displayX + (cellSize - charWidth) / 2;
                int charY = displayY + (cellSize + charHeight) / 2 - fm.getDescent();

                g.drawString(String.valueOf(ch), charX, charY);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int cellSize = getWidth() / map.displaySize();
        int mapRelX = x / cellSize;
        int mapRelY = y / cellSize;
        int mapTrueX = (mapRelX - GameMap.DISPLAY_SIZE) + ClientLoop.cameraX;
        int mapTrueY = (mapRelY - GameMap.DISPLAY_SIZE) + ClientLoop.cameraY;
        ClientLoop.selectedX = mapTrueX;
        ClientLoop.selectedY = mapTrueY;
        ClientLoop.refresh();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
