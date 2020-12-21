package com.hard.playback_player.models;

import java.util.Collection;

public class Band extends AbstractModel {
    private Collection<Song> songs;
    private String title;

    public Collection<Song> getSongs() {
        return songs;
    }

    public void setSongs(Collection<Song> songs) {
        this.songs = songs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
