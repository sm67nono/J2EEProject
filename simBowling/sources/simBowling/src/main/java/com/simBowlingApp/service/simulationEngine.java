package com.simBowlingApp.service;

import com.simBowlingApp.entities.Game;
import com.simBowlingApp.entities.Player;
import com.simBowlingApp.entities.RoundData;
import com.simBowlingApp.entities.RoundScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class simulationEngine
{
    private int roundCounter;

    public List<RoundScore> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<RoundScore> responseList) {
        this.responseList = responseList;
    }

    private List<RoundScore> responseList;

    @Autowired
    private BowlingAlley bowlingAlley;

    @Autowired
    private Player player;


    public simulationEngine() {
        this.roundCounter = 1;
        responseList=new ArrayList<RoundScore>();
    }


    public void simRound()
    {
        //Simulate Final or Normal Based on the Number


        if(roundCounter<Game.rounds){
            System.out.println("RoundCounter "+ roundCounter);
            normalSim(roundCounter);
            roundCounter++;
        }
        else if(roundCounter==Game.rounds)
        {
            finalRoundSim();
            roundCounter++;
        }
        else
        {
            if(roundCounter>Game.rounds)
            {
                System.out.println("Invalid. Start a new Game");
            }

        }

    }

    public void reset()
    {
        //Clear All data
        this.roundCounter=1;
        this.responseList=new ArrayList<RoundScore>();
        bowlingAlley.getScoreBoard().clear();
        bowlingAlley.getTossRecords().clear();
    }


    public void normalSim(int round)
    {
        RoundScore roundScore=new RoundScore();
        bowlingAlley.simulationCore(bowlingAlley, player, round-1);
        int toss1Score=0;
        int toss2Score=0;
        boolean isStrike=false;
        boolean isSpare=false;
        int totalScore=0;


        if(bowlingAlley.getScoreBoard().containsKey(round))
        {
            RoundData rdata = bowlingAlley.getScoreBoard().get(round);
            toss1Score = rdata.getToss1Score() ;
            toss2Score = rdata.getToss2Score() ;

            if(toss1Score==Game.totalPins)
            {
                isStrike=true;

            }
            else {
                if ((toss1Score + toss2Score) == Game.totalPins) {
                    isSpare = true;
                }
            }

            totalScore = rdata.getRoundScore();

        }

        roundScore.setToss1placeholder(toss1Score);
        roundScore.setToss2placeholder(toss2Score);

        roundScore.setTotalScore(totalScore);
        roundScore.setStrike(isStrike);
        roundScore.setSpare(isSpare);
        responseList.add(roundScore);

        //Updating total scores before sending
        int counter=0;
        for (RoundData totalScoreUpdate: bowlingAlley.getScoreBoard().values())
        {
            RoundScore rcore = responseList.get(counter++);
            rcore.setTotalScore(totalScoreUpdate.getRoundScore());
        }


    }

    public void finalRoundSim()
    {
        RoundScore roundScore = new RoundScore();

        bowlingAlley.simulateFinalRound(bowlingAlley, player);


        if(bowlingAlley.getScoreBoard().containsKey(Game.rounds))
        {
            RoundData rdata = bowlingAlley.getScoreBoard().get(Game.rounds);

            int toss1Score = rdata.getToss1Score();
            int toss2Score = rdata.getToss2Score();
            int toss3Score = rdata.getToss3Score();

            if(toss1Score==Game.totalPins)
            {
                roundScore.setIsfinalRoundToss1Strike(true);

            }
            else {
                if ((toss1Score + toss2Score) == Game.totalPins) {
                    roundScore.setIsfinalRoundToss2Spare(true);
                }
            }
            if(toss2Score==Game.totalPins)
            {
                roundScore.setIsfinalRoundToss2Strike(true);
            }
            else {
                if ((toss2Score + toss3Score) == Game.totalPins) {
                    roundScore.setIsfinalRoundToss3Spare(true);
                }
            }
            if(toss3Score==Game.totalPins)
            {
                roundScore.setIsfinalRoundToss3Strike(true);
            }


            roundScore.setTotalScore(rdata.getRoundScore());
            roundScore.setToss1placeholder(toss1Score);
            roundScore.setToss2placeholder(toss2Score);
            roundScore.setToss3placeholder(toss3Score);


            responseList.add(roundScore);

            //Updating total scores before sending
            int counter=0;
            for (RoundData totalScoreUpdate: bowlingAlley.getScoreBoard().values())
            {
                RoundScore rcore = responseList.get(counter++);
                rcore.setTotalScore(totalScoreUpdate.getRoundScore());
            }

        }
    }
}
