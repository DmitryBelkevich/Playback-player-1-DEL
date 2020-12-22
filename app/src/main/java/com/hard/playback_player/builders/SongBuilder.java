package com.hard.playback_player.builders;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SongBuilder {
    private Song song;

    public SongBuilder() {
        song = new Song();
    }

    public SongBuilder buildId(long id) {
        song.setId(id);
        return this;
    }

    public SongBuilder buildBand(Band band) {
        song.setBand(band);
        return this;
    }

    public SongBuilder buildTitle(String title) {
        song.setTitle(title);
        return this;
    }

    public SongBuilder buildScoresPaths(Map<String, String> scoresPaths) {
        song.setScoresPaths(scoresPaths);
        return this;
    }

    public Song build() {
        song.setTextPath(generateTextPath());
        song.setScoresPaths(generateScoresPaths());
        song.setSoundPath(generateMp3Path());

        return song;
    }

    private String generateTextPath() {
        String path = generatePath();

        StringBuilder stringBuilder = new StringBuilder(path);

        stringBuilder.append(".txt");

        return stringBuilder.toString();
    }

    private Map<String, String> generateScoresPaths() {
        Map<String, String> scoresPaths = song.getScoresPaths();

        String path = generatePath();

        Set<String> keySet = scoresPaths.keySet();
        Iterator<String> iterator = keySet.iterator();
        while(iterator.hasNext()) {
            String partName = iterator.next();

            StringBuilder stringBuilder = new StringBuilder(path);

            stringBuilder.append(" - ");
            stringBuilder.append(partName);
            stringBuilder.append(".pdf");

            scoresPaths.put(partName, stringBuilder.toString());
        }

        return scoresPaths;
    }

    private String generateMp3Path() {
        String path = generatePath();

        StringBuilder stringBuilder = new StringBuilder(path);

        stringBuilder.append(".mp3");

        return stringBuilder.toString();
    }

    private String generatePath() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("/");
        stringBuilder.append(song.getBand().getTitle());

        stringBuilder.append("/");
        stringBuilder.append(song.getBand().getTitle());
        stringBuilder.append(" - ");
        stringBuilder.append(song.getTitle());

        stringBuilder.append("/");
        stringBuilder.append(song.getBand().getTitle());
        stringBuilder.append(" - ");
        stringBuilder.append(song.getTitle());

        return stringBuilder.toString();
    }
}
