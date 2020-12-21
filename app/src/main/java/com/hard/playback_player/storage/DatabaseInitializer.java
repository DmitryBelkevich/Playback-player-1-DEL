package com.hard.playback_player.storage;

import com.hard.playback_player.builders.SongBuilder;
import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;

import java.io.File;
import java.util.ArrayList;

public class DatabaseInitializer {
    public static void init() {
        File file = new File(Constants.STORAGE);
        File[] bands_folders = file.listFiles();

        long bandId = 1;
        long songId = 1;

        if (bands_folders == null)
            return;

        for (int i = 0; i < bands_folders.length; i++) {
            Band band = new Band();

            band.setId(bandId);
            band.setSongs(new ArrayList<>());
            band.setTitle(bands_folders[i].getName());

            for (int j = 0; j < bands_folders[i].listFiles().length; j++) {
                String bandTitle = band.getTitle();
                String fullSongTitle = bands_folders[i].listFiles()[j].getName();
                String songTitle = fullSongTitle.substring(bandTitle.length() + 3);

                Song song = new SongBuilder()
                        .buildId(songId)
                        .buildBand(band)
                        .buildTitle(songTitle)
                        .build();

                band.getSongs().add(song);
                Database.songs.add(song);

                songId++;
            }

            Database.bands.add(band);

            bandId++;
        }
    }
}
