package com.hard.playback_player.storage;

import com.hard.playback_player.builders.SongBuilder;
import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseInitializer {
    public static void init() {
        File file = new File(Constants.STORAGE);
        File[] bandsFolders = file.listFiles();

        long bandId = 1;
        long songId = 1;

        if (bandsFolders == null)
            return;

        for (int i = 0; i < bandsFolders.length; i++) {
            Band band = new Band();

            band.setId(bandId);
            band.setSongs(new ArrayList<>());
            band.setTitle(bandsFolders[i].getName());

            for (int j = 0; j < bandsFolders[i].listFiles().length; j++) {
                String bandTitle = band.getTitle();
                String fullSongTitle = bandsFolders[i].listFiles()[j].getName();
                String songTitle = fullSongTitle.substring(bandTitle.length() + 3);

                File currentSongFolder = bandsFolders[i].listFiles()[j];
                File[] scoresFolders = currentSongFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".pdf");
                    }
                });

                Map<String, String> scores = new HashMap<>();
                for (int k = 0; k < scoresFolders.length; k++) {
                    File scoreFolder = scoresFolders[k];
                    String scoreTitle = scoreFolder.getName().substring(fullSongTitle.length() + 3);

                    scores.put(scoreTitle, null);
                }

                Song song = new SongBuilder()
                        .buildId(songId)
                        .buildBand(band)
                        .buildScoresPaths(scores)
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
