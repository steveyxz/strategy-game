package me.partlysunny.game.ui.window;

import me.partlysunny.network.client.ClientLoop;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyboardCommands extends KeyAdapter {

    @Override
    public void keyPressed(KeyEvent e) {
        super.keyPressed(e);
        process(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        process(e);
    }

    private void process(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
            ClientLoop.selectedX--;
            ClientLoop.refresh();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
            ClientLoop.selectedX++;
            ClientLoop.refresh();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
            ClientLoop.selectedY--;
            ClientLoop.refresh();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
            ClientLoop.selectedY++;
            ClientLoop.refresh();
        }
    }
}
