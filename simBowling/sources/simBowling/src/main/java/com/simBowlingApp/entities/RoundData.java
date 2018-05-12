package com.simBowlingApp.entities;

public class RoundData
{
    private Player player;

    private int roundID;

    private int toss1Score;
    private int toss2Score;
    private int toss3Score;

    private int roundScore;

    public int getToss3Score() {
        return toss3Score;
    }

    public void setToss3Score(int toss3Score) {
        this.toss3Score = toss3Score;
    }

    public int getRoundID() {
        return roundID;
    }

    public void setRoundID(int roundID) {
        this.roundID = roundID;
    }

    public int getToss1Score() {
        return toss1Score;
    }

    public void setToss1Score(int toss1Score) {
        this.toss1Score = toss1Score;
    }

    public int getToss2Score() {
        return toss2Score;
    }

    public void setToss2Score(int toss2Score) {
        this.toss2Score = toss2Score;
    }



    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public int getRoundScore() {
        return roundScore;
    }

    public void setRoundScore(int roundScore) {
        this.roundScore = roundScore;
    }


}
