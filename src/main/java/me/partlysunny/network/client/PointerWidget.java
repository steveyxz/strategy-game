package me.partlysunny.network.client;

import org.jline.keymap.KeyMap;
import org.jline.reader.LineReader;
import org.jline.reader.Reference;
import org.jline.widget.Widgets;

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
        getKeyMap().bind(new Reference("cursor-move-up"), "w");
        getKeyMap().bind(new Reference("cursor-move-down"), "s");
        getKeyMap().bind(new Reference("cursor-move-left"), "a");
        getKeyMap().bind(new Reference("cursor-move-right"), "d");
        getKeyMap().bind(new Reference("cursor-move-up-fast"), KeyMap.ctrl('w'));
        getKeyMap().bind(new Reference("cursor-move-down-fast"), KeyMap.ctrl('s'));
        getKeyMap().bind(new Reference("cursor-move-right-fast"), KeyMap.ctrl('d'));
        getKeyMap().bind(new Reference("cursor-move-left-fast"), KeyMap.ctrl('a'));
    }

    private boolean moveCursorUp() {
        ClientLoop.cameraY++;
        return true;
    }
    
    private boolean moveCursorDown() {
        ClientLoop.cameraY--;
        return true;
    }
    
    private boolean moveCursorLeft() {
        ClientLoop.cameraX--;
        return true;
    }
    
    private boolean moveCursorRight() {
        ClientLoop.cameraX++;
        return true;
    }
    
    private boolean moveCursorUpFast() {
        ClientLoop.cameraY += 5;
        return true;
    }
    
    private boolean moveCursorDownFast() {
        ClientLoop.cameraY -= 5;
        return true;
    }
    
    private boolean moveCursorRightFast() {
        ClientLoop.cameraX += 5;
        return true;
    }
    
    private boolean moveCursorLeftFast() {
        ClientLoop.cameraX -= 5;
        return true;
    }


}
