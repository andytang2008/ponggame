package game.offline.ponggame;

import android.content.Context;
import android.content.SharedPreferences;
/*
public class GameScoreManager {
    private int totalScore;
    private static final String PREFS_NAME = "game_prefs";
    private static final String TOTAL_SCORE_KEY = "total_score";

    private SharedPreferences preferences;

    public GameScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadTotalScore();
    }

    private void loadTotalScore() {
        totalScore = preferences.getInt(TOTAL_SCORE_KEY, 0);
    }

    public void addRoundScore(int roundScore) {
        totalScore += roundScore;
        saveTotalScore();
    }

    private void saveTotalScore() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TOTAL_SCORE_KEY, totalScore);
        editor.apply();
    }

    public int getScore() {
        return totalScore;
    }

    public void increaseScore(int amount) {
        totalScore += amount;
    }


}*/
/*
public class GameScoreManager {
    private int totalScore;
    private static final String PREFS_NAME = "game_prefs";
    private static final String TOTAL_SCORE_KEY = "total_score";
    private static final String INITIALIZED_KEY = "initialized";

    private SharedPreferences preferences;

    public GameScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if (!isInitialized()) {
            // If it's the first time the game is run, set totalScore to 0
            resetTotalScore();
            markInitialized(); // Mark the game as initialized
        } else {
            loadTotalScore(); // Load the existing totalScore
        }
    }

    private boolean isInitialized() {
        return preferences.getBoolean(INITIALIZED_KEY, false);
    }

    private void markInitialized() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(INITIALIZED_KEY, true);
        editor.apply();
    }

    private void loadTotalScore() {
        totalScore = preferences.getInt(TOTAL_SCORE_KEY, 0);
    }

    private void saveTotalScore() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TOTAL_SCORE_KEY, totalScore);
        editor.apply();
    }

    public void addScore(int score) {
        totalScore += score;
        saveTotalScore();
    }

    public int getScore() {
        return totalScore;
    }

    public void resetTotalScore() {
        totalScore = 0;
        saveTotalScore();
    }

    public void increaseScore(int amount) {
        totalScore += amount;
    }
}
*/


/*
public class GameScoreManager {
    private int totalScore;
    private static final String PREFS_NAME = "game_prefs";
    private static final String TOTAL_SCORE_KEY = "total_score";

    private SharedPreferences preferences;

    public GameScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadTotalScore();
    }

    private void loadTotalScore() {
        totalScore = preferences.getInt(TOTAL_SCORE_KEY, 0);
    }

    private void saveTotalScore() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TOTAL_SCORE_KEY, totalScore);
        editor.apply();
    }

    public void addScore(int score) {
        totalScore += score;
        saveTotalScore();
    }

    public int getScore() {
        return totalScore;
    }

    public void resetTotalScore() {
        totalScore = 0;
        saveTotalScore();
    }

    public void increaseScore(int amount) {
        totalScore += amount;
    }
}*/

public class GameScoreManager {
    private int totalScore;
    private static final String PREFS_NAME = "game_prefs";
    private static final String TOTAL_SCORE_KEY = "total_score";

    private SharedPreferences preferences;

    public GameScoreManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadTotalScore(); // Load the score from SharedPreferences when the object is created
    }

    private void loadTotalScore() {
        // Retrieve the saved total score, default to 0 if it doesn't exist
        totalScore = preferences.getInt(TOTAL_SCORE_KEY, 0);
    }

    private void saveTotalScore() {
        // Save the current total score in SharedPreferences
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(TOTAL_SCORE_KEY, totalScore);
        editor.apply();
    }

    public void addScore(int score) {
        totalScore += score; // Add the score to the total
        saveTotalScore(); // Save the updated score to SharedPreferences
    }

    public int getScore() {
        return totalScore; // Return the current total score
    }

    public void resetTotalScore() {
        totalScore = 0; // Reset the total score to 0
        saveTotalScore(); // Save the reset score to SharedPreferences
    }

    public void increaseScore(int amount) {
        totalScore += amount; // Increase the total score by the specified amount
        saveTotalScore(); // Save the updated score to SharedPreferences
    }
}