package game.offline.ponggame;

public class RoundScoreManager {
    private int score;

    public RoundScoreManager() {
        score = 0;
    }

    public void increaseScore(int amount) {
        score += amount;
    }

    public int getScore() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }
}
