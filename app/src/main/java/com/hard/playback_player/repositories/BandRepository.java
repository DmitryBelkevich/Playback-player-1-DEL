package com.hard.playback_player.repositories;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.storage.Database;

import java.util.Collection;
import java.util.Iterator;

public class BandRepository {
    private Collection<Band> bands;

    public BandRepository() {
        bands = Database.bands;
    }

    public Collection<Band> getAll() {
        return bands;
    }

    public Band getById(long id) {
        Iterator<Band> iterator = bands.iterator();
        while (iterator.hasNext()) {
            Band band = iterator.next();
            if (band.getId() == id)
                return band;
        }

        return null;
    }

    public void add(Band band) {
        bands.add(band);
    }

    public void update(long id, Band band) {
        // TODO
    }

    public void delete(long id) {
        // TODO
    }

    public long size() {
        return bands.size();
    }
}
