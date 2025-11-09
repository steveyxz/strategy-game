package me.partlysunny.ui.window.menu;

import me.partlysunny.ui.window.UIPanel;

import javax.swing.*;

public class InfoMenu implements MenuBuilder{
    @Override
    public void build(UIPanel panel) {
        panel.add(new JLabel("Info"));
    }
}
