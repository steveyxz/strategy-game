package me.partlysunny.game;

import java.util.Set;

public interface Taggable {

    void addTag(String tag);
    void removeTag(String tag);
    boolean hasTag(String tag);
    String[] getTags();

}
