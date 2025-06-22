package me.partlysunny.game.ui.window;

import me.partlysunny.game.GameState;
import me.partlysunny.game.ui.Renderer;

public class SwingRenderer implements Renderer {

    public MainPanel frame;

    @Override
    public void run() {
        frame = new MainPanel();
        frame.setVisible(true);
    }

    @Override
    public void refresh(GameState state) {
        frame.refresh(state);
    }

    @Override
    public void frame() {
    }

}
