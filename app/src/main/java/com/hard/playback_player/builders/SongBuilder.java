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

    public SongBuilder buildText(String text) {
        song.setText(text);
        return this;
    }

    public SongBuilder buildScoresPaths(Map<String, String> scoresPaths) {
        song.setScores(scoresPaths);
        return this;
    }

    public SongBuilder buildPlaybacks(Map<Integer, String> playbacks) {
        song.setPlaybacks(playbacks);
        return this;
    }

    public Song build() {
        song.setScores(generateScoresPaths());

        return song;
    }

    private Map<String, String> generateScoresPaths() {
        Map<String, String> scoresPaths = song.getScores();

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
