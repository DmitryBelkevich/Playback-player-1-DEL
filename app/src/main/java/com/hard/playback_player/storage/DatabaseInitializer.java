package com.hard.playback_player.storage;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
                // song

                Song song = new Song();

                song.setId(songId);
                song.setBand(band);

                String bandTitle = band.getTitle();
                String fullSongTitle = bandsFolders[i].listFiles()[j].getName();
                String songTitle = fullSongTitle.substring(bandTitle.length() + 3);

                song.setTitle(songTitle);

                File[] songsFolders = bandsFolders[i].listFiles();

                // TODO songs sorting

                File songFolder = songsFolders[j];

                // find: .txt

                File[] textsFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".txt");
                    }
                });

                String text = null;
                if (textsFiles.length != 0) {
                    File textFile = textsFiles[0];
                    text = textFile.getPath();
                }

                song.setText(text);

                // find: .pdf

                File[] scoresFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".pdf");
                    }
                });

                Map<String, String> scores = new TreeMap<>();
                for (int k = 0; k < scoresFiles.length; k++) {
                    File scoreFile = scoresFiles[k];
                    String scoreTitle = scoreFile.getName().substring(fullSongTitle.length() + 3, scoreFile.getName().length() - 4);

                    scores.put(scoreTitle, scoreFile.getPath());
                }

                // find: .mp3

                File[] playbacksFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().endsWith(".mp3");
                    }
                });

                Map<Integer, String> playbacks = new TreeMap<>();

                for (int k = 0; k < playbacksFiles.length; k++) {
                    File playbackFile = playbacksFiles[k];

                    String fileName = playbackFile.getName();
                    String transposedString = fileName.substring(fullSongTitle.length(), fileName.length() - 4);

                    int transposed = 0;
                    if (!transposedString.equals(""))
                        transposed = Integer.parseInt(transposedString.substring(2, transposedString.length() - 1));

                    playbacks.put(transposed, playbackFile.getPath());
                }

                song.setScores(scores);
                song.setPlaybacks(playbacks);

                Set<Integer> playbacksSet = playbacks.keySet();

                if (!playbacksSet.isEmpty() && !playbacksSet.contains(0)) {
                    song.setTransposition(Collections.min(playbacksSet));
                } else {
                    song.setTransposition(0);
                }

                band.getSongs().add(song);
                Database.songs.add(song);

                songId++;
            }

            Database.bands.add(band);

            bandId++;
        }
    }
}
