package me.partlysunny.ui.window;

import me.partlysunny.ui.window.menu.InfoMenu;
import me.partlysunny.ui.window.menu.MenuBuilder;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class UIPanel extends JPanel {

    private static final Map<String, MenuBuilder> menuBuilders = new HashMap<>();

    static {
        menuBuilders.put("info", new InfoMenu());
    }

    public JLabel heading;
    public JLabel subtext;

    private JPanel bodyPanel;

    public UIPanel() {
        GridBagLayout mgr = new GridBagLayout();
        mgr.rowHeights = new int[] {30, 30, getHeight() - 60};
        mgr.columnWidths = new int[] {getWidth()};
        setLayout(mgr);
        heading = new JLabel("TEST HEADING");
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        subtext = new JLabel("TEST SUBHEADING");
        subtext.setHorizontalAlignment(SwingConstants.CENTER);
        bodyPanel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.BOTH;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(heading, gbc);

        gbc.gridy = 1;
        add(subtext, gbc);

        gbc.gridy = 2;
        add(bodyPanel, gbc);
    }

    public void rebuild(String selected) {
        if (!menuBuilders.containsKey(selected)) {
            throw new IllegalStateException("No such menu builder: " + selected);
        }
        bodyPanel.removeAll();
        menuBuilders.get(selected).build(this);
    }

}
