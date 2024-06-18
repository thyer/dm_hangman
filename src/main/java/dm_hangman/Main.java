package dm_hangman;

import dm_hangman.state.HangmanState;
import dm_hangman.state.InputHelper;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(final String[] args) throws IOException, InterruptedException {
        String lang = InputHelper.ENGLISH;
        boolean isHeadToHead = false;
        if(args.length >= 1){
            lang = args[0];
            isHeadToHead = Boolean.parseBoolean(args[1]);
        }
        InputHelper ih = new InputHelper(lang);
        String reference = ih.getReferenceInput();
        String line = ih.getDM(reference);

        Scanner scanner = new Scanner(System.in);
        Game game = new Game(line);
        long begin = System.currentTimeMillis();
        if(isHeadToHead){
            System.out.println("************READY PLAYER 1!***************");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("__________________________________________");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("__________________________________________");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("__________________________________________");
        }
        HangmanState state1 = game.runGame(isHeadToHead);
        if(!isHeadToHead){
            state1.print();
            if(state1.getBlankLettersRemaining() == 0 && state1.getTriesRemaining() > 0) {
                long end = System.currentTimeMillis();
                System.out.println("************YOU WIN!***************");
                long totalTime = end-begin;
                String hms = String.format("%02d:%02d.%03d",
                        TimeUnit.MILLISECONDS.toMinutes(totalTime) % TimeUnit.HOURS.toMinutes(1),
                        TimeUnit.MILLISECONDS.toSeconds(totalTime) % TimeUnit.MINUTES.toSeconds(1),
                        TimeUnit.MILLISECONDS.toMillis(totalTime) % TimeUnit.SECONDS.toMillis(1));
                System.out.printf("*******TOTAL TIME:%s*************%n", hms);

                ih.recordName(reference, hms, state1.getPoints());
            }
            else{
                System.out.println("************TRY AGAIN**************");
            }
        }
        else{
            System.out.println("************PLAYER 1 MATCH COMPLETED***************");
            System.out.println("************READY PLAYER 2!***************");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("__________________________________________");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("__________________________________________");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("__________________________________________");
            game = new Game(line);
            HangmanState state2 = game.runGame(isHeadToHead);
            System.out.println("************PLAYER 2 MATCH COMPLETED***************");

            System.out.println("************CALCULATING WINNER***************");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("************CALCULATING WINNER***************");
            TimeUnit.SECONDS.sleep(1);
            System.out.println("************CALCULATING WINNER***************");
            TimeUnit.SECONDS.sleep(1);
            if(state1.getPoints() > state2.getPoints()){
                System.out.println("************PLAYER 1 IS THE WINNER!***************");
            }
            else{
                System.out.println("************PLAYER 2 IS THE WINNER!***************");
            }
            TimeUnit.SECONDS.sleep(2);
            state1.print();
            state2.print();

        }

        scanner.close();
    }
}