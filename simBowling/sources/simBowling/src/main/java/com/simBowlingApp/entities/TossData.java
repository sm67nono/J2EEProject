package com.simBowlingApp.entities;

public class TossData
{
    private Game game;

    private int tossScore;

    public TossData(Game game, int tossScore, int tossID) {
        this.game = game;
        this.tossScore = tossScore;
        this.tossID = tossID;
    }

    public int getTossID() {
        return tossID;
    }

    public void setTossID(int tossID) {
        this.tossID = tossID;
    }

    private int tossID;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getTossScore() {
        return tossScore;
    }

    public void setTossScore(int toss1Score) {
        this.tossScore = toss1Score;
    }


}
