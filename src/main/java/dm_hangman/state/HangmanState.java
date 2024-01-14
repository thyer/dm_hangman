package dm_hangman.state;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class HangmanState {
    private static final String BLANK_LETTER = "_";
    private static final Pattern alphabetPattern = Pattern.compile("[A-Z]", Pattern.CASE_INSENSITIVE);

    private final String unencodedString;
    private final String userVisibleString;
    private final int triesRemaining;
    private final int blankLettersRemaining;

    private final int points;

    public HangmanState(String unencodedString, int triesRemaining){
        this(unencodedString.toUpperCase(), prepareUserVisibleString(unencodedString), triesRemaining, 0);
    }

    public HangmanState(String unencodedString, String userVisibleString, int triesRemaining, int points){
        this.unencodedString = unencodedString;
        this.triesRemaining = triesRemaining;

        this.userVisibleString = userVisibleString;
        this.blankLettersRemaining = userVisibleString.length() - userVisibleString.replace(BLANK_LETTER, "").length();
        this.points = points;
    }

    private static String prepareUserVisibleString(String unencodedString) {
        // to uppercase
        String userVisibleString = unencodedString.toUpperCase();
        // ignore accents and diacritics
        userVisibleString = Normalizer.normalize(userVisibleString, Normalizer.Form.NFD);
        userVisibleString = userVisibleString.replaceAll("\\p{M}", "");

        // blank out letters
        userVisibleString = alphabetPattern.matcher(userVisibleString).replaceAll(BLANK_LETTER);
        return userVisibleString;
    }

    public HangmanState guessLetter(String guessedCharacters){
        int len = userVisibleString.length();
        int pointsPerCharacter = guessedCharacters.length();
        HangmanState output = this;
        for(int iGuessedChar = 0; iGuessedChar < guessedCharacters.length(); ++iGuessedChar) {
            char guessedChar = guessedCharacters.charAt(iGuessedChar);
            boolean earnedPoint = false;
            for (int iUserVisibleStringChar = 0; iUserVisibleStringChar < len; ++iUserVisibleStringChar) {
                char curr = output.userVisibleString.charAt(iUserVisibleStringChar);
                if (curr != BLANK_LETTER.charAt(0)) { // character already revealed
                    continue;
                }

                String secretChar = output.unencodedString.substring(iUserVisibleStringChar, iUserVisibleStringChar + 1);
                String unaccentedChar = Normalizer.normalize(secretChar, Normalizer.Form.NFD); // separates é into e+diacritic
                if (guessedChar == unaccentedChar.charAt(0)) {
                    StringBuilder sb = new StringBuilder(output.userVisibleString);
                    sb.setCharAt(iUserVisibleStringChar, secretChar.charAt(0));
                    earnedPoint = true;
                    output = new HangmanState(output.unencodedString, sb.toString(), output.triesRemaining, output.points + pointsPerCharacter);
                    break;
                }
            }
            if(!earnedPoint){
                output = new HangmanState(output.unencodedString, output.userVisibleString, output.triesRemaining - 1, output.points - pointsPerCharacter);
            }
        }

        return output;
    }

    public String getUserVisibleString() {
        return userVisibleString;
    }

    public int getTriesRemaining() {
        return triesRemaining;
    }

    public int getBlankLettersRemaining(){
        return blankLettersRemaining;
    }

    public int getPoints(){
        return points;
    }

    public void print() {
        System.out.printf("%nPoints=%d%nTries Remaining=%d%nBlank Letters Remaining=%d%nDM:%n%s%n%n",
                this.getPoints(),
                this.getTriesRemaining(),
                this.getBlankLettersRemaining(),
                this.getUserVisibleString());
    }
}
