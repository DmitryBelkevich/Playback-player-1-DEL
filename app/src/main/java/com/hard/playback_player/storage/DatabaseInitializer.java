package com.hard.playback_player.storage;

import com.hard.playback_player.builders.SongBuilder;
import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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

        Arrays.sort(bandsFolders, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return file1.getName().compareTo(file2.getName());
            }
        });

        for (int i = 0; i < bandsFolders.length; i++) {
            Band band = new Band();

            band.setId(bandId);
            band.setSongs(new ArrayList<>());
            band.setTitle(bandsFolders[i].getName());

            for (int j = 0; j < bandsFolders[i].listFiles().length; j++) {
                String bandTitle = band.getTitle();
                String fullSongTitle = bandsFolders[i].listFiles()[j].getName();
                String songTitle = fullSongTitle.substring(bandTitle.length() + 3);

                File[] songsFolders = bandsFolders[i].listFiles();

//                Arrays.sort(songsFolders, new Comparator<File>() {
//                    @Override
//                    public int compare(File file1, File file2) {
//                        return file1.getName().compareTo(file2.getName());
//                    }
//                });

                File songFolder = songsFolders[j];

                // find: .pdf
                File[] scoresFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".pdf");
                    }
                });

                Map<String, String> scoresTitles = new HashMap<>();
                for (int k = 0; k < scoresFiles.length; k++) {
                    File scoreFile = scoresFiles[k];
                    String scoreTitle = scoreFile.getName().substring(fullSongTitle.length() + 3, scoreFile.getName().length() - 4);

                    scoresTitles.put(scoreTitle, null);
                }

                // find: .mp3

                File[] playbacksFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".mp3");
                    }
                });

                Map<Integer, String> playbacks = new HashMap<>();

                for (int k = 0; k < playbacksFiles.length; k++) {
                    File playbackFile = playbacksFiles[k];

                    String fileName = playbackFile.getName();
                    String transposedString = fileName.substring(fullSongTitle.length(), fileName.length() - 4);

                    int transposed = 0;
                    if (!transposedString.equals(""))
                        transposed = Integer.parseInt(transposedString.substring(2, transposedString.length() - 1));

                    playbacks.put(transposed, playbackFile.getPath());
                }

                // builder

                Song song = new SongBuilder()
                        .buildId(songId)
                        .buildBand(band)
                        .buildTitle(songTitle)
                        .buildScoresPaths(scoresTitles)
                        .buildPlaybacks(playbacks)
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
