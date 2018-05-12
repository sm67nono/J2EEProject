package com.simBowlingApp.service;

import com.simBowlingApp.entities.Game;
import com.simBowlingApp.entities.Player;
import com.simBowlingApp.entities.RoundData;
import com.simBowlingApp.entities.TossData;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Component
class BowlingAlley
{
    //Store every round as key and RoundData as value
    private Map<Integer, RoundData> scoreBoard;

    //Store the toss as key and the score as value
    private Map<Integer, TossData> tossRecords;

    //Total number of tosses during a game
    private int tossCounter;

    public BowlingAlley() {
        this.scoreBoard = new HashMap<Integer, RoundData>();
        this.tossRecords =new HashMap<Integer, TossData>();
        this.tossCounter = 0;
    }

    public Map<Integer, TossData> getTossRecords() {
        return tossRecords;
    }

    public void setTossRecords(Map<Integer, TossData> tossRecords) {
        this.tossRecords = tossRecords;
    }



    public Map<Integer, RoundData> getScoreBoard() {
        return scoreBoard;
    }

    public void setScoreBoard(Map<Integer, RoundData> scoreBoard) {
        this.scoreBoard = scoreBoard;
    }


    public int randomBowlEngine(int max)
    {
        //Create random bowled pins
        Random r = new Random();
        int value = -1;

        //Toss 1
        if(max==Game.totalPins)
        {
            int value1 = r.nextInt(max + 1);
            int value2 = r.nextInt(max + 1);
            value = value1 + value2;

            value = (value > (max+2)) ? max : (value / 2);
        }
        //Toss 2
        else
        {
            int value1 = r.nextInt(max + 1);
            int value2 = r.nextInt(max + 1);
            value = ((value1+value2)>=(max))?max:value1;
        }
        return value;
    }

    public void updatePreviousScores(BowlingAlley bowlData, int currentToss)
    {
        TossData currentTD =  bowlData.getTossRecords().get(currentToss);

        //Adding Bonus for Strikes obtained previously

        //Case-1
        if(bowlData.getTossRecords().containsKey(currentToss-2))
        {
            TossData previousTD = bowlData.getTossRecords().get(currentToss-2);
            if(previousTD.getGame().isStrike())
            {

                RoundData roundData = bowlData.getScoreBoard().get(previousTD.getGame().getRoundNumber());
                roundData.setRoundScore(roundData.getRoundScore()+currentTD.getTossScore());

                //Changes to the score which follows (+1)
                if(bowlData.getScoreBoard().containsKey(previousTD.getGame().getRoundNumber()+1))
                {
                    RoundData roundData2 = bowlData.getScoreBoard().get(previousTD.getGame().getRoundNumber() + 1);
                    roundData2.setRoundScore(roundData2.getRoundScore() + currentTD.getTossScore());
                }
            }

        }

        //Case-2
        if(bowlData.getTossRecords().containsKey(currentToss-1))
        {
            TossData previousTD = bowlData.getTossRecords().get(currentToss-1);
            if(previousTD.getGame().isStrike())
            {

                if(bowlData.getScoreBoard().containsKey(previousTD.getGame().getRoundNumber())) {
                    RoundData roundData = bowlData.getScoreBoard().get(previousTD.getGame().getRoundNumber());
                    roundData.setRoundScore(roundData.getRoundScore() + currentTD.getTossScore());
                }
            }

        }

        //Adding Bonus for Spares obtained previously

        if(bowlData.getTossRecords().containsKey(currentToss-1))
        {


            TossData previousTD = bowlData.getTossRecords().get(currentToss-1);
            if(previousTD.getGame().isSpare())
            {

                RoundData roundData = bowlData.getScoreBoard().get(previousTD.getGame().getRoundNumber());
                roundData.setRoundScore(roundData.getRoundScore()+currentTD.getTossScore());
            }

        }




    }

    public void simulationCore(BowlingAlley bowlingAlley, Player player1, int round)
    {

        TossData tdata1 = new TossData(new Game(false,false,round+1),0, bowlingAlley.tossCounter);

        RoundData rdata = new RoundData();


        //Toss-1
        tdata1.setTossScore(bowlingAlley.randomBowlEngine(Game.totalPins));
        rdata.setToss1Score(tdata1.getTossScore());


        //Case-1 : Strike
        if (tdata1.getTossScore() == Game.totalPins) {
            tdata1.getGame().setStrike(true);
            rdata.setRoundScore(Game.totalPins);

            //Update toss records
            bowlingAlley.tossCounter++;
            tdata1.setTossID(bowlingAlley.tossCounter);

            bowlingAlley.tossRecords.put(bowlingAlley.tossCounter,tdata1);

            //Update bonus scores of strike or spares
            bowlingAlley.updatePreviousScores(bowlingAlley, bowlingAlley.tossCounter);


        }


        else {

            //Update toss records for toss 1
            bowlingAlley.tossCounter++;
            tdata1.setTossID(tossCounter);

            bowlingAlley.tossRecords.put(bowlingAlley.tossCounter,tdata1);
            //Update bonus scores of strike or spares
            bowlingAlley.updatePreviousScores(bowlingAlley, bowlingAlley.tossCounter);


            //Go for Toss-2
            TossData tdata2 = new TossData(new Game(false,false,round+1),0, bowlingAlley.tossCounter);
            tdata2.setTossScore(bowlingAlley.randomBowlEngine((Game.totalPins - tdata1.getTossScore())));
            rdata.setToss2Score(tdata2.getTossScore());




            //Case-2 : Spare

            if ((rdata.getToss1Score() + rdata.getToss2Score()) == Game.totalPins) {
                tdata2.getGame().setSpare(true);
                rdata.setRoundScore(Game.totalPins);

            }

            //Case-3: Neither Strike nor Spare
            else
            {
                rdata.setRoundScore(rdata.getToss1Score() + rdata.getToss2Score());

            }

            //Update toss records for toss 2
            bowlingAlley.tossCounter++;
            tdata2.setTossID(bowlingAlley.tossCounter);

            bowlingAlley.tossRecords.put(bowlingAlley.tossCounter,tdata2);

            ///Update bonus scores of strike or spares
            bowlingAlley.updatePreviousScores(bowlingAlley, bowlingAlley.tossCounter);


        }

        rdata.setPlayer(player1);
        rdata.setRoundID(round+1);

        if(bowlingAlley.getScoreBoard().containsKey(round))
        {
            int prevRoundScore = bowlingAlley.getScoreBoard().get(round).getRoundScore();

            //Set Current Round Score
            rdata.setRoundScore(prevRoundScore+rdata.getRoundScore());
        }

        bowlingAlley.scoreBoard.put(round+1,rdata);

    }

    public void simulateFinalRound(BowlingAlley bowlingAlley, Player player1)
    {
        TossData tdata1 = new TossData(new Game(false,false,Game.rounds),0, bowlingAlley.tossCounter);

        RoundData rdata = new RoundData();

        boolean thirdToss=false;

        //Toss-1
        tdata1.setTossScore(bowlingAlley.randomBowlEngine(Game.totalPins));
        rdata.setToss1Score(tdata1.getTossScore());


        //Case-1 : Strike
        if (tdata1.getTossScore() == Game.totalPins) {
            tdata1.getGame().setStrike(true);

            //Grant a third toss
            thirdToss=true;



        }

        //Second toss


        System.out.println("Inside second toss final round");
        //Update toss records for toss 1
        bowlingAlley.tossCounter++;
        tdata1.setTossID(tossCounter);

        bowlingAlley.tossRecords.put(bowlingAlley.tossCounter, tdata1);
        //Update bonus scores of strike or spares
        bowlingAlley.updatePreviousScores(bowlingAlley, bowlingAlley.tossCounter);


        //Go for Toss-2
        TossData tdata2 = new TossData(new Game(false, false, Game.rounds), 0, bowlingAlley.tossCounter);

        if(rdata.getToss1Score()==Game.totalPins) {
            //Incase the first toss was a strike
            tdata2.setTossScore(bowlingAlley.randomBowlEngine(Game.totalPins ));
            rdata.setToss2Score(tdata2.getTossScore());

            //Case-2 : Strike or Spare

            if (rdata.getToss2Score()== Game.totalPins)
            {
                tdata2.getGame().setStrike(true);
                //Grant a third toss
                thirdToss = true;

            }
        }
        else
        {
            //Incase the first toss was not a strike
            tdata2.setTossScore(bowlingAlley.randomBowlEngine((Game.totalPins - tdata1.getTossScore())));
            rdata.setToss2Score(tdata2.getTossScore());
            if ((rdata.getToss2Score()+rdata.getToss1Score())== Game.totalPins)
            {
                tdata2.getGame().setSpare(true);


                //Grant a third toss
                thirdToss = true;

            }
        }

        //Update toss records for toss 2
        bowlingAlley.tossCounter++;
        tdata1.setTossID(tossCounter);

        bowlingAlley.tossRecords.put(bowlingAlley.tossCounter,tdata2);
        //Update bonus scores of strike or spares
        bowlingAlley.updatePreviousScores(bowlingAlley, bowlingAlley.tossCounter);

        if(!thirdToss)
        {
            rdata.setRoundScore(rdata.getToss1Score() + rdata.getToss2Score());
        }


        //Case-3: Neither Strike nor Spare - Check for third-toss in case of Final Round
        if(thirdToss)
        {


            // Go for Toss-3
            TossData tdata3 = new TossData(new Game(false,false,Game.rounds),0, bowlingAlley.tossCounter);


            //Check if toss2 was a strike or a spare(10 pins available again)
            if((rdata.getToss2Score()==Game.totalPins) || (rdata.getToss1Score()+rdata.getToss2Score())==Game.totalPins) {


                tdata3.setTossScore(bowlingAlley.randomBowlEngine(Game.totalPins));
                rdata.setToss3Score(tdata3.getTossScore());


                //Check this toss its a strike
                if (rdata.getToss3Score()== Game.totalPins)
                {
                    //System.out.println("Final toss 3 is a strike");
                    tdata3.getGame().setStrike(true);


                }

                //Update toss records for toss 3
                bowlingAlley.tossCounter++;
                tdata3.setTossID(bowlingAlley.tossCounter);

                bowlingAlley.tossRecords.put(bowlingAlley.tossCounter,tdata3);
            }
            //Incase the second toss was not a strike or spare
            else
            {

                tdata3.setTossScore(bowlingAlley.randomBowlEngine((Game.totalPins - rdata.getToss2Score())));
                rdata.setToss3Score(tdata3.getTossScore());
                if ((rdata.getToss2Score()+rdata.getToss3Score())== Game.totalPins)
                {

                    tdata3.getGame().setSpare(true);

                }
                //Update toss records for toss 3
                bowlingAlley.tossCounter++;
                tdata3.setTossID(bowlingAlley.tossCounter);

                bowlingAlley.tossRecords.put(bowlingAlley.tossCounter,tdata3);
            }

            rdata.setRoundScore(rdata.getToss1Score()+rdata.getToss2Score()+rdata.getToss3Score());

        }

        rdata.setPlayer(player1);
        rdata.setRoundID(Game.rounds);

        if(bowlingAlley.getScoreBoard().containsKey(Game.rounds-1))
        {
            int prevRoundScore = bowlingAlley.getScoreBoard().get(Game.rounds-1).getRoundScore();

            rdata.setRoundScore(prevRoundScore+rdata.getRoundScore());
        }

        bowlingAlley.scoreBoard.put(Game.rounds,rdata);

    }

}