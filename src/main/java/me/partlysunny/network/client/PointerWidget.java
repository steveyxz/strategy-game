package me.partlysunny.network.client;

import me.partlysunny.TUtil;
import org.jline.keymap.KeyMap;
import org.jline.reader.LineReader;
import org.jline.reader.Reference;
import org.jline.widget.Widgets;

import static org.jline.keymap.KeyMap.*;

public class PointerWidget extends Widgets {
    /**
     * Creates a new Widgets instance for the specified LineReader.
     *
     * @param reader the LineReader to associate with these widgets
     */
    public PointerWidget(LineReader reader) {
        super(reader);
        addWidget("cursor-move-up", this::moveCursorUp);
        addWidget("cursor-move-down", this::moveCursorDown);
        addWidget("cursor-move-left", this::moveCursorLeft);
        addWidget("cursor-move-right", this::moveCursorRight);
        addWidget("cursor-move-up-fast", this::moveCursorUpFast);
        addWidget("cursor-move-down-fast", this::moveCursorDownFast);
        addWidget("cursor-move-right-fast", this::moveCursorRightFast);
        addWidget("cursor-move-left-fast", this::moveCursorLeftFast);
        getKeyMap().bind(new Reference("cursor-move-up"), alt('w'));
        getKeyMap().bind(new Reference("cursor-move-down"), alt('s'));
        getKeyMap().bind(new Reference("cursor-move-left"), alt('a'));
        getKeyMap().bind(new Reference("cursor-move-right"), alt('d'));
        getKeyMap().bind(new Reference("cursor-move-up-fast"), alt(ctrl('W')));
        getKeyMap().bind(new Reference("cursor-move-down-fast"), alt(ctrl('S')));
        getKeyMap().bind(new Reference("cursor-move-right-fast"), alt(ctrl('D')));
        getKeyMap().bind(new Reference("cursor-move-left-fast"), alt(ctrl('A')));
    }

    private boolean moveCursorUp() {
        ClientLoop.cameraY--;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorDown() {
        ClientLoop.cameraY++;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorLeft() {
        ClientLoop.cameraX--;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorRight() {
        ClientLoop.cameraX++;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorUpFast() {
        ClientLoop.cameraY -= 5;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorDownFast() {
        ClientLoop.cameraY += 5;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorRightFast() {
        ClientLoop.cameraX += 5;
        ClientLoop.refresh();
        return true;
    }
    
    private boolean moveCursorLeftFast() {
        ClientLoop.cameraX -= 5;
        ClientLoop.refresh();
        return true;
    }


}
