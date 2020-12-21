package com.hard.playback_player.services;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.repositories.BandRepository;

import java.util.Collection;

public class BandService {
    private BandRepository bandRepository;

    public BandService() {
        bandRepository = new BandRepository();
    }

    public Collection<Band> getAll() {
        return bandRepository.getAll();
    }
}
