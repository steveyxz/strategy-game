package me.partlysunny.ui.console.menu;

import me.partlysunny.game.map.GameMap;

import java.util.List;

public interface SideMenu {

    List<String> buildMenu(GameMap gameMap);

}
