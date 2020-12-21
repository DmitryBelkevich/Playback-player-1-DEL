package com.hard.playback_player.services;

import com.hard.playback_player.models.Song;
import com.hard.playback_player.repositories.SongRepository;

import java.util.Collection;

public class SongService {
    private SongRepository songRepository;

    public SongService() {
        songRepository = new SongRepository();
    }

    public Collection<Song> getAll() {
        return songRepository.getAll();
    }

    public Song getById(long id) {
        return songRepository.getById(id);
    }
}
