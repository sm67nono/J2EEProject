package com.simBowlingApp.entities;

public class Game {
    public static int rounds=10;
    public static int totalPins=10;
    public static int strikeBonusCount=2;
    public static int spareBonusCount=1;

    private boolean strike;
    private boolean spare;

    public Game(boolean strike, boolean spare, int roundNumber) {
        this.strike = strike;
        this.spare = spare;
        this.roundNumber = roundNumber;
    }


    public int getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    private int roundNumber;

    public boolean isStrike() {
        return strike;
    }

    public void setStrike(boolean strike) {
        this.strike = strike;
    }

    public boolean isSpare() {
        return spare;
    }

    public void setSpare(boolean spare) {
        this.spare = spare;
    }





}
