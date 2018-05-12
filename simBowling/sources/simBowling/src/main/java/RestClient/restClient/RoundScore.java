package RestClient.restClient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RoundScore
{
    private int toss1placeholder;
    private int toss2placeholder;
    private int toss3placeholder;

    private int totalScore;

    public RoundScore() {
        this.isStrike = false;
        this.isSpare = false;
        this.isfinalRoundToss1Strike = false;
        this.isfinalRoundToss2Strike = false;
        this.isIsfinalRoundToss3Strike = false;
        this.isfinalRoundToss2Spare = false;
        this.isIsfinalRoundToss3Spare = false;
    }

    private boolean isStrike;
    private boolean isSpare;

    private boolean isfinalRoundToss1Strike;
    private boolean isfinalRoundToss2Strike;
    private boolean isIsfinalRoundToss3Strike;

    private boolean isfinalRoundToss2Spare;
    private boolean isIsfinalRoundToss3Spare;

    public boolean isIsfinalRoundToss1Strike() {
        return isfinalRoundToss1Strike;
    }

    public void setIsfinalRoundToss1Strike(boolean isfinalRoundToss1Strike) {
        this.isfinalRoundToss1Strike = isfinalRoundToss1Strike;
    }

    public boolean isIsfinalRoundToss2Strike() {
        return isfinalRoundToss2Strike;
    }

    public void setIsfinalRoundToss2Strike(boolean isfinalRoundToss2Strike) {
        this.isfinalRoundToss2Strike = isfinalRoundToss2Strike;
    }

    public boolean isIsfinalRoundToss3Strike() {
        return isIsfinalRoundToss3Strike;
    }

    public void setIsfinalRoundToss3Strike(boolean isfinalRoundToss3Strike) {
        isIsfinalRoundToss3Strike = isfinalRoundToss3Strike;
    }

    public boolean isIsfinalRoundToss2Spare() {
        return isfinalRoundToss2Spare;
    }

    public void setIsfinalRoundToss2Spare(boolean isfinalRoundToss2Spare) {
        this.isfinalRoundToss2Spare = isfinalRoundToss2Spare;
    }

    public boolean isIsfinalRoundToss3Spare() {
        return isIsfinalRoundToss3Spare;
    }

    public void setIsfinalRoundToss3Spare(boolean isfinalRoundToss3Spare) {
        isIsfinalRoundToss3Spare = isfinalRoundToss3Spare;
    }


    public boolean isStrike() {
        return isStrike;
    }

    public void setStrike(boolean strike) {
        isStrike = strike;
    }

    public boolean isSpare() {
        return isSpare;
    }

    public void setSpare(boolean spare) {
        isSpare = spare;
    }


    public int getToss1placeholder() {
        return toss1placeholder;
    }

    public void setToss1placeholder(int toss1placeholder) {
        this.toss1placeholder = toss1placeholder;
    }

    public int getToss2placeholder() {
        return toss2placeholder;
    }

    public void setToss2placeholder(int toss2placeholder) {
        this.toss2placeholder = toss2placeholder;
    }

    public int getToss3placeholder() {
        return toss3placeholder;
    }

    public void setToss3placeholder(int toss3placeholder) {
        this.toss3placeholder = toss3placeholder;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }



}
