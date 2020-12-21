package com.hard.playback_player.builders;

import com.hard.playback_player.models.Band;
import com.hard.playback_player.models.Song;

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

    public Song build() {
        song.setTextPath(generateTextPath(song));
        song.setScorePath(generateScorePath(song));
        song.setSoundPath(generateMp3Path(song));

        return song;
    }

    private static String generateTextPath(Song song) {
        String path = generatePath(song);

        StringBuilder stringBuilder = new StringBuilder(path);

        stringBuilder.append(".txt");

        return stringBuilder.toString();
    }

    private static String generateScorePath(Song song) {
        String path = generatePath(song);

        StringBuilder stringBuilder = new StringBuilder(path);

        stringBuilder.append(" - ");
        stringBuilder.append("Full Score");
        stringBuilder.append(".pdf");

        return stringBuilder.toString();
    }

    private static String generateMp3Path(Song song) {
        String path = generatePath(song);

        StringBuilder stringBuilder = new StringBuilder(path);

        stringBuilder.append(".mp3");

        return stringBuilder.toString();
    }

    private static String generatePath(Song song) {
        StringBuilder stringBuilder = new StringBuilder();

//        stringBuilder.append("/storage/emulated/0/Android/data/com.hard.app");//getExternalFilesDir(filepath);
//        stringBuilder.append("/files");

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
