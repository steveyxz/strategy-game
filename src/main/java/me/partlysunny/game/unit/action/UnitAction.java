package me.partlysunny.game.unit.action;

import me.partlysunny.game.unit.Unit;

public interface UnitAction {

    void action(Unit unit);

    String id();

}
