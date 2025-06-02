package me.partlysunny.game.event;

import me.partlysunny.game.unit.Unit;

public class UnitEvent implements Event {

    private final Unit unit;

    public UnitEvent(final Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

}
