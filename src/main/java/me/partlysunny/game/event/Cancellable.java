package me.partlysunny.game.event;

public interface Cancellable {

    boolean isCancelled();

    void setCancelled(boolean cancel);

}
