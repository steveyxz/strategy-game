package me.partlysunny.game.event;

import me.partlysunny.game.unit.Unit;

public class NewTurnEvent extends UnitEvent {

    private final int turnNumber;

    public NewTurnEvent(Unit unit, int turnNumber) {
        super(unit);
        this.turnNumber = turnNumber;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
