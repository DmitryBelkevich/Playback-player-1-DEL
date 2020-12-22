package com.hard.playback_player.models;

import java.util.Map;

public class Song extends AbstractModel {
    private Band band;
    private String title;
    private String textPath;
    private Map<String, String> scoresPaths;
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

    public Map<String, String> getScoresPaths() {
        return scoresPaths;
    }

    public void setScoresPaths(Map<String, String> scoresPaths) {
        this.scoresPaths = scoresPaths;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }
}
