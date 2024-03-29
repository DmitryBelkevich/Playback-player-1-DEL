package com.hard.playback_player.storage;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;
import com.hard.playback_player.settings.Constants;
import com.hard.playback_player.utils.Reader;

import java.io.File;
import java.io.FileNotFoundException;
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
        File rootDirectory = new File(Constants.STORAGE + "/database");
        File[] bandsFolders = rootDirectory.listFiles();

        if (bandsFolders == null)
            return;

        Arrays.sort(bandsFolders, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                return file1.getName().compareTo(file2.getName());
            }
        });

        long bandId = 1;
        long songId = 1;

        // insert bands

        for (int i = 0; i < bandsFolders.length; i++) {
            Band band = new Band();

            band.setId(bandId);
            band.setSongs(new ArrayList<>());
            band.setTitle(bandsFolders[i].getName());

            File bandFolder = bandsFolders[i];
            File[] songsFolders = bandFolder.listFiles();

            Arrays.sort(songsFolders, new Comparator<File>() {
                @Override
                public int compare(File file1, File file2) {
                    return file1.getName().compareTo(file2.getName());
                }
            });

            // insert songs

            for (int j = 0; j < songsFolders.length; j++) {
                Song song = new Song();

                song.setId(songId);
                song.setBand(band);

                File songFolder = songsFolders[j];
                String fullSongTitle = songFolder.getName();
                String songTitle = fullSongTitle.substring(band.getTitle().length() + " - ".length());

                song.setTitle(songTitle);

                // find: .txt

                File[] textsFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().contains("text");
                    }
                });

                String text = null;
                if (textsFiles.length != 0) {
                    File textFile = textsFiles[0];
                    try {
                        text = Reader.readFromFile(textFile.getPath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
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
                    String scoreTitle = scoreFile.getName().substring(fullSongTitle.length() + " - ".length(), scoreFile.getName().length() - ".pdf".length());

                    scores.put(scoreTitle, scoreFile.getPath());
                }

                song.setScores(scores);

                // find: .mp3

                File[] playbacksFiles = songFolder.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        return name.toLowerCase().contains("playback");
                    }
                });

                Map<Integer, String> playbacks = new TreeMap<>();

                for (int k = 0; k < playbacksFiles.length; k++) {
                    File playbackFile = playbacksFiles[k];

                    String fileName = playbackFile.getName();
                    String postfix = fileName.substring("playback".length());

                    int transposed = 0;
                    if (!postfix.equals(""))
                        transposed = Integer.parseInt(postfix.substring(" (".length(), postfix.length() - ")".length()));

                    String playback = null;
                    try {
                        playback = Reader.readFromFile(playbackFile.getPath());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    playbacks.put(transposed, playback);
                }

                song.setPlaybacks(playbacks);

                // setTransposition

                Set<Integer> playbacksSet = playbacks.keySet();

                if (!playbacksSet.isEmpty() && !playbacksSet.contains(0)) {
                    song.setTransposition(Collections.min(playbacksSet));
                } else {
                    song.setTransposition(0);
                }

                // fill other fields

                band.getSongs().add(song);
                Database.songs.add(song);

                songId++;
            }

            Database.bands.add(band);

            bandId++;
        }
    }
}
