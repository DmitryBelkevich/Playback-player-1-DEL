package com.hard.playback_player.repositories;

import com.hard.playback_player.models.Song;
import com.hard.playback_player.storage.Database;

import java.util.Collection;
import java.util.Iterator;

public class SongRepository {
    private Collection<Song> songs;

    public SongRepository() {
        songs = Database.songs;
    }

    public Collection<Song> getAll() {
        return songs;
    }

    public Song getById(long id) {
        Iterator<Song> iterator = songs.iterator();
        while (iterator.hasNext()) {
            Song song = iterator.next();
            if (song.getId() == id)
                return song;
        }

        return null;
    }

    public void add(Song song) {
        songs.add(song);
    }

    public void update(long id, Song song) {
        // TODO
    }

    public void delete(long id) {
        // TODO
    }

    public long size() {
        return songs.size();
    }
}
