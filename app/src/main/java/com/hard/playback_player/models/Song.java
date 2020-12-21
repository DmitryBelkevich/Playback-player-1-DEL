package com.hard.playback_player.models;

public class Song extends AbstractModel {
    private Band band;
    private String title;
    private String textPath;
    private String scorePath;
    private String soundPath;

    public Band getBand() {
        return band;
    }

    public void setBand(Band band) {
        this.band = band;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTextPath() {
        return textPath;
    }

    public void setTextPath(String textPath) {
        this.textPath = textPath;
    }

    public String getScorePath() {
        return scorePath;
    }

    public void setScorePath(String scorePath) {
        this.scorePath = scorePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    @Override
    public String toString() {
        return "Song{" +
                ", title='" + title + '\'' +
                '}';
    }
}
