package com.hard.playback_player.models;

import java.util.Map;

public class Song extends AbstractModel {
    private Band band;
    private String title;
    private String text;
    private Map<String, String> scores;
    private Map<Integer, String> playbacks;
    private int transposition;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public int getTransposition() {
        return transposition;
    }

    public void setTransposition(int transposition) {
        this.transposition = transposition;
    }
}
