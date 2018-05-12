package RestClient.restClient;

import java.util.InputMismatchException;
import java.util.Scanner;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

public class simBowlingClient
{

    public static int numRounds=10;

    public static void main(String args[]) {

        RestTemplate restTemplate = new RestTemplate();

        //Total number of rounds can be 10
        RoundScore[] roundScore= new RoundScore[numRounds];

        Scanner reader = new Scanner(System.in);

        try {
            restTemplate.getForObject("http://localhost:8080/Reset", RoundScore[].class);
        }
        catch(ResourceAccessException rae)
        {
            System.out.println("Service not available. Exiting..");
            reader.close();
            System.exit(0);
        }
        //Clearing Screen Before Displaying data
        //System.out.print("\033[H\033[2J");
        //System.out.flush();

        System.out.println("Welcome to SimBowling ----- Press (1) to Bowl and (0) to Start a New Game). Press any other key to Exit");

        for (int i = 0; ; i++) {


            System.out.println("                                                                                              ");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            System.out.println("                                                                                              ");
            try
            {
                int n = reader.nextInt();
                if (n == 1) {
                    roundScore = restTemplate.getForObject("http://localhost:8080/Score", RoundScore[].class);
                } else if (n == 0) {
                    restTemplate.getForObject("http://localhost:8080/Reset", RoundScore[].class);
                } else {
                    System.out.println("Wrong Key Exiting...");
                    reader.close();
                    System.exit(0);
                }
            }
            catch (InputMismatchException ie)
            {
                System.out.println("Wrong Key Exiting...");
                reader.close();
                System.exit(0);
            }

            int roundCount = 1;
            for (RoundScore rscore : roundScore) {


                System.out.println("============== Round - " + roundCount + " ==============");

                if (roundCount < 10) {
                    if (rscore.isStrike()) {
                        System.out.println("Strike(X)");
                        System.out.println("Score ->" + rscore.getTotalScore());
                    } else if (rscore.isSpare()) {
                        System.out.print("Toss-1 ->" + rscore.getToss1placeholder());
                        System.out.println("  ||  Spare(/)");
                        System.out.println("Score ->" + rscore.getTotalScore());
                    } else {
                        System.out.print("Toss-1 ->" + rscore.getToss1placeholder());
                        System.out.println("  ||  Toss-2 ->" + rscore.getToss2placeholder());
                        System.out.println("Score ->" + rscore.getTotalScore());
                    }
                }
                //Display for the final round
                else {
                    if (rscore.isIsfinalRoundToss1Strike()) {
                        if (rscore.isIsfinalRoundToss2Strike()) {
                            if (rscore.isIsfinalRoundToss3Strike()) {

                                System.out.println("Strike(X)  ||  Strike(X)  ||  Strike(X)");
                                System.out.println("Score ->" + rscore.getTotalScore());
                            } else {
                                System.out.println("Strike(X)  ||  Strike(X)  ||  " + rscore.getToss3placeholder());
                                System.out.println("Score ->" + rscore.getTotalScore());

                            }
                        } else {
                            if (rscore.isIsfinalRoundToss3Spare()) {

                                System.out.println("Strike(X)  ||  Toss-2 ->" + rscore.getToss2placeholder() + "  ||  Spare(/)");
                                System.out.println("Score ->" + rscore.getTotalScore());
                            } else {
                                System.out.println("Strike(X)  ||  Toss-2 ->" + rscore.getToss2placeholder() + "  ||  Toss-2 ->" + rscore.getToss3placeholder());
                                System.out.println("Score ->" + rscore.getTotalScore());
                            }
                        }

                    } else {
                        if (rscore.isIsfinalRoundToss2Spare()) {

                            if (rscore.isIsfinalRoundToss3Strike()) {
                                System.out.print("Toss-1 ->" + rscore.getToss1placeholder());
                                System.out.println("  ||  Spare(/)  ||  Strike(X)");
                                System.out.println("Score ->" + rscore.getTotalScore());
                            } else {
                                System.out.print("Toss-1 ->" + rscore.getToss1placeholder());
                                System.out.println("  ||  Spare(/)  ||  Toss-3 ->" + rscore.getToss3placeholder());
                                System.out.println("Score ->" + rscore.getTotalScore());
                            }


                        } else {
                            System.out.print("Toss-1 ->" + rscore.getToss1placeholder());
                            System.out.println("  ||  Toss-2 ->" + rscore.getToss2placeholder());
                            System.out.println("Score ->" + rscore.getTotalScore());
                        }
                    }

                    System.out.println("Thanks for Playing !");
                    restTemplate.getForObject("http://localhost:8080/Reset", RoundScore[].class);
                    System.out.println("Starting a New Game");
                    System.out.println("--------Instructions---------");
                    System.out.println("->Press (1) to Bowl and (0) to Restart.");
                    System.out.println("->Press any other key to Exit");


                }

                //Increment round counter
                roundCount++;

            }


        }

    }
}

