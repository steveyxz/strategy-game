package me.partlysunny.ui.window;

import me.partlysunny.game.GameState;
import me.partlysunny.game.map.RenderableMap;
import me.partlysunny.network.client.ClientLoop;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JFrame {

    public CharMapTable charMapTable;

    public MainPanel() {
        super();
        setTitle("Game");
        setLocationRelativeTo(null);
        setBounds(0, 0, 800, 600);
        setResizable(false);
        setVisible(true);
        // setup layout
        GridBagLayout manager = new GridBagLayout();
        manager.columnWidths = new int[]{getWidth() / 3 * 2, getWidth() / 3};
        manager.rowHeights = new int[] {getHeight()};
        setLayout(manager);

        GridBagConstraints c = new GridBagConstraints();

        // add two sides -> left side char map, right side visuals
        CharMapTable charMapTable = new CharMapTable();
        c.fill = GridBagConstraints.BOTH;
        c.gridx = 0;
        c.gridy = 0;
        add(charMapTable, c);
        this.charMapTable = charMapTable;
        addKeyListener(new KeyboardCommands());

        UIPanel uiPanel = new UIPanel();
        c.gridx = 1;
        add(uiPanel, c);


    }

    public void refresh(GameState state) {
        RenderableMap render = state.gameMap.render(ClientLoop.ID);
        charMapTable.setMap(render);
        repaint();
    }


}
