package dm_hangman;

import dm_hangman.state.HangmanState;

import java.util.Scanner;

public class Game {
    private final String line;
    private static final int MAX_POSSIBLE_TRIES = 10;
    private static final int SECONDS_PER_CHARACTER_PENALTY_THRESHOLD = 5;

    public Game(String line){
        this.line = line;
    }

    public HangmanState runGame(boolean isHeadToHead) throws InterruptedException {
        int triesRemaining = Math.min(1 + (line.length() / 50), MAX_POSSIBLE_TRIES);
        HangmanState state = new HangmanState(line, triesRemaining);
        Scanner scanner = new Scanner(System.in);
        int lastPenalty = 0;
        while(state.getTriesRemaining() > 0 && state.getBlankLettersRemaining() > 0){
            state.print(!isHeadToHead);
            if(lastPenalty > 0){
                System.out.println("WARNING - you are allowed " + SECONDS_PER_CHARACTER_PENALTY_THRESHOLD + " seconds max per character guessed. You've had " + lastPenalty + " tries deducted from your remaining tries");
                Thread.sleep(1000);
                System.out.println("...");
                Thread.sleep(500);
            }
            long beginGuess = System.currentTimeMillis();
            System.out.print("Enter a character A-Z (more characters at once = more points!): ");
            String input = scanner.nextLine().toUpperCase();
            StringBuilder sb = new StringBuilder();
            for(char c : input.toCharArray()){
                if(c < 'A' || c > 'Z'){
                    continue;
                }
                sb.append(c);
            }

            int length = 1;
            if(sb.length() > 0){
                length = sb.length();
            }
            long secondsPerCharacterGuessed = ((System.currentTimeMillis() - beginGuess) / 1000) / length;
            lastPenalty = (int) secondsPerCharacterGuessed/SECONDS_PER_CHARACTER_PENALTY_THRESHOLD;

            state = state.guessLetter(sb.toString(), isHeadToHead, lastPenalty);
        }

        return state;
    }
}
