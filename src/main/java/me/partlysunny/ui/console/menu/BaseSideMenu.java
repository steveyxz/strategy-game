package me.partlysunny.ui.console.menu;

import me.partlysunny.game.map.GameMap;
import org.jline.jansi.Ansi;

import java.util.List;

public abstract class BaseSideMenu implements SideMenu {

    public List<String> buildMenu(GameMap gameMap) {
        String[] menu = new String[100];
        for (int i = 0; i < 100; i++) {
            menu[i] = "  ";
        }
        Ansi A = Ansi.ansi();
        menu[1] += A.fgBrightBlue() + "MENU" + A.fgDefault();
        menu[2] += A.fgBrightBlack() + "Alt+WASD to move " + A.fgDefault() + A.fgGreen() + "Ctrl+Alt+WASD to fast move" + A.fgDefault();
        int i = 3;
        for (String s : getLines(gameMap)) {
            menu[i] += s;
            i++;
            if (i >= 100) break;
        }
        return List.of(menu);
    }

    protected abstract List<String> getLines(GameMap gameMap);

}
