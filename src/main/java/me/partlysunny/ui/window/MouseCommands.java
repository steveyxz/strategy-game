package me.partlysunny.ui.window;

import me.partlysunny.network.client.ClientLoop;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseCommands extends MouseAdapter {

    Integer lastX = null, lastY = null;

    @Override
    public void mouseDragged(MouseEvent e) {
        if (lastX == null || lastY == null) {
            lastX = e.getX();
            lastY = e.getY();
        }
        super.mouseDragged(e);
        int dx = e.getX() - lastX;
        int dy = e.getY() - lastY;
        int distSq = dx * dx + dy * dy;
//        System.out.println(distSq);
        int threshold = 50;
        if (distSq < threshold * threshold) {
            return;
        }
        int moveThreshold = threshold / 3;
        if (dx > moveThreshold) {
            ClientLoop.cameraX--;
            if (!e.isShiftDown()) {
                ClientLoop.selectedX--;
            }
        } else if (dx < -moveThreshold) {
            ClientLoop.cameraX++;
            if (!e.isShiftDown()) {
                ClientLoop.selectedX++;
            }
        }
        if (dy > moveThreshold) {
            ClientLoop.cameraY--;
            if (!e.isShiftDown()) {
                ClientLoop.selectedY--;
            }
        } else if (dy < -moveThreshold) {
            ClientLoop.cameraY++;
            if (!e.isShiftDown()) {
                ClientLoop.selectedY++;
            }
        }

        lastX = e.getX();
        lastY = e.getY();
        ClientLoop.refresh();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("mouseReleased");
        lastX = null;
        lastY = null;
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        System.out.println("mousePressed");
        lastX = null;
        lastY = null;
    }
}
