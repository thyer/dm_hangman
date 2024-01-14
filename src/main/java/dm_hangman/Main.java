package dm_hangman;

import dm_hangman.state.HangmanState;
import dm_hangman.state.InputHelper;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final int MAX_POSSIBLE_TRIES = 10;

    public static void main(final String[] args) throws IOException {
        String lang = InputHelper.ENGLISH;
        if(args.length == 1){
            lang = args[0];
        }
        InputHelper ih = new InputHelper(lang);
        String reference = ih.getReferenceInput();
        String line = ih.getDM(reference);
        int triesRemaining = Math.min(1 + (line.length() / 50), MAX_POSSIBLE_TRIES);
        HangmanState state = new HangmanState(line, triesRemaining);
        long begin = System.currentTimeMillis();
        Scanner scanner = new Scanner(System.in);
        while(state.getTriesRemaining() > 0 && state.getBlankLettersRemaining() > 0){
            state.print();
            System.out.print("Enter a character A-Z (more characters at once = more points!): ");
            String input = scanner.nextLine().toUpperCase();
            StringBuilder sb = new StringBuilder();
            for(char c : input.toCharArray()){
                if(c < 'A' || c > 'Z'){
                    continue;
                }
                sb.append(c);
            }
            state = state.guessLetter(sb.toString());
        }

        state.print();
        if(state.getBlankLettersRemaining() == 0 && state.getTriesRemaining() > 0) {
            long end = System.currentTimeMillis();
            System.out.println("************YOU WIN!***************");
            long totalTime = end-begin;
            String hms = String.format("%02d:%02d.%03d",
                    TimeUnit.MILLISECONDS.toMinutes(totalTime) % TimeUnit.HOURS.toMinutes(1),
                    TimeUnit.MILLISECONDS.toSeconds(totalTime) % TimeUnit.MINUTES.toSeconds(1),
                    TimeUnit.MILLISECONDS.toMillis(totalTime) % TimeUnit.SECONDS.toMillis(1));
            System.out.printf("*******TOTAL TIME:%s*************%n", hms);

            ih.recordName(reference, hms, state.getPoints());
        }
        else{
            System.out.println("************TRY AGAIN**************");
        }
        scanner.close();
    }
}