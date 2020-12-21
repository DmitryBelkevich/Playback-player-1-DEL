package com.hard.playback_player.storage;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;

import java.util.ArrayList;
import java.util.Collection;

public class Database {
    public static Collection<Band> bands = new ArrayList<>();
    public static Collection<Song> songs = new ArrayList<>();

    public static void clear() {
        if (bands != null)
            bands.clear();

        if (songs != null)
            songs.clear();
    }

    public static void select() {
        // TODO
    }

    public static void insert() {
        // TODO
    }

    public static void update() {
        // TODO
    }

    public static void delete() {
        // TODO
    }
}
