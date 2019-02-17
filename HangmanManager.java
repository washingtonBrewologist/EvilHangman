import java.util.*;

public class HangmanManager {

    private SortedSet<String> wordSet;
    private SortedSet<Character> lettersGuessed;
    private int remainingGuesses;
    private String pattern;

    /**
     * Instantiates a new Hangman manager.
     * @param dictionary the dictionary of words to be used
     * @param length     the length of the word set
     * @param max        the max number of guesses
     */
    public HangmanManager(List<String> dictionary, int length, int max) {
        if (length < 1 || max < 0){
            throw new IllegalArgumentException
                    ("Check : length: " + length + ", max: " + max);
        }
        remainingGuesses = max;
        wordSet = new TreeSet<>();
        lettersGuessed = new TreeSet<>();
        pattern = "";
        for (int i = 0; i < length; i++){
            pattern += "-";
        }
        for (String word : dictionary){
            if (word.length() == length){
                wordSet.add(word);
            }
        }
    }

    /**
     * The current set of words for game play
     * @return the current set of words being considered in game
     */
    Set<String> words(){
        return wordSet;
    }

    /**
     * Guesses left in round of hangman.
     * @return an int representing remaining guesses for game
     */
    int guessesLeft(){
        return remainingGuesses;
    }

    /**
     * Guesses previously made in round of game play.
     * @return A sorted set representing the letters already guessed
     */
    SortedSet<Character> guesses(){
        return lettersGuessed;
    }

    /**
     * Displays current pattern to be displayed for round of game play.
     * @return the string representation of current pattern.
     */
    String pattern(){
        if (wordSet.isEmpty()){
            throw new IllegalStateException("Word set is empty!");
        }
        String gamePattern = String.valueOf(pattern.charAt(0));
        for (int i = 1; i < pattern.length(); i++){
            gamePattern += (" " + pattern.charAt(i));
        }
        return gamePattern;
    }

    /**
     * We go through each word in the set, letter by letter checking
     * to see if the letter matches the players guess. We append the
     * pattern where guess != to the letter and sort the patterns into
     * groups.
     * @param guess represents the current guess during game play
     * @return PatternList represents the next grouping of patterns
     * selected by our program.
     */
    public TreeMap<String, SortedSet<String>> patternsList(char guess){
        TreeMap<String, SortedSet<String>> patternSet = new TreeMap<>();
        for (String word : wordSet){
            String gPattern = "";
            for (int i = 0; i < word.length(); i++){
                if (word.charAt(i) == guess){
                    gPattern += guess;
                } else {
                    gPattern += pattern.charAt(i);
                }
            }
            // If the pattern is unique we want to group this as a new set of patterns
            if (!patternSet.containsKey(gPattern)) {
                patternSet.put(gPattern, new TreeSet<>());
            }
            // If not we want to find the pattern set it belongs with
            patternSet.get(gPattern).add(word);
        }
        return patternSet;
    }

    /**
     * Checks the current pattern list and refreshes
     * the game pattern as letters are guessed, or not guessed.
     * @param patternList represents the current patterns being considered
     *        by computer.
     */
    public void refreshPattern(TreeMap<String, SortedSet<String>> patternList){
        pattern = "";
        int count = 0;
        for (String patternK : patternList.keySet()){
            if (patternList.get(patternK).size() > count){
                pattern = patternK;
                count = patternList.get(patternK).size();
            }
        }
    }

    /**
     * Will update the wordSet array to reflect the current size of
     * the patternList key set.
     * @param patternList represents the current patterns being considered
     *        by computer.
     */
    public void updWordSet(TreeMap<String, SortedSet<String>> patternList){
        int pSize = 0;
        for (String patternK : patternList.keySet()){
            if (patternList.get(patternK).size() > pSize){
                pSize = patternList.get(patternK).size();
                wordSet = patternList.get(patternK);
            }
        }
    }

    /**
     * Handles the recording of the next guess made by the user.
     * Takes the guess and determines the new set of words
     * and adjusts the guesses made and remaining guesses.
     * @param guess This represents the current guess made by the user.
     * @return guessCalc is an integer representing the number of correct guesses made
     * by the user during game play
     */
    int record(char guess){
        if (remainingGuesses < 1 || wordSet.isEmpty()){
            throw new IllegalStateException("");
        }else if (!wordSet.isEmpty() && lettersGuessed.contains(guess)){
            throw new IllegalArgumentException("");
        }
        // This will store all of our changing patterns
        TreeMap<String, SortedSet<String>> patternAnalysis = patternsList(guess);

        // Will update our patterns
        refreshPattern(patternAnalysis);

        // will keep our current word set updating
        updWordSet(patternAnalysis);

        // Add guesses made into our field list
        lettersGuessed.add(guess);

        return guessCalc(guess);
    }

    /**
     * Guess Calc represents the number of correct guesses
     * made during game play. Updates remainingGuesses field.
     * @param guess char Guess represents the current letter
     *  guessed.
     * @return An integer representing the number of correctGuesses
     */
    public int guessCalc(char guess){
        int correctGuesses = 0;
        for (int i = 0; i < pattern.length(); i++){
            if (pattern.charAt(i) == guess){
                correctGuesses++;
            }
        }
        if (correctGuesses == 0){
            remainingGuesses--;
        }
        return correctGuesses;
    }

}