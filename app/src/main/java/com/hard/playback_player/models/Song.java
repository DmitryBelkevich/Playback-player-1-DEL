package com.hard.playback_player.models;

import java.util.Map;

public class Song extends AbstractModel {
    private Band band;
    private String title;
    private String textPath;
    private Map<String, String> scores;
    private Map<Integer, String> playbacks;

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

    public Map<String, String> getScores() {
        return scores;
    }

    public void setScores(Map<String, String> scores) {
        this.scores = scores;
    }

    public Map<Integer, String> getPlaybacks() {
        return playbacks;
    }

    public void setPlaybacks(Map<Integer, String> playbacks) {
        this.playbacks = playbacks;
    }
}
