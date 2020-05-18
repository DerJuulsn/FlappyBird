package de.bildner.FlappyBird.Game;

import java.io.*;

class GameScoreManager {

    private int score;
    private int highScore = 0;

    void init() {
        readHighScore();
    }

    private void readHighScore() {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("data/highscore.txt").getPath()));
            String line = reader.readLine();
            if (line != null) {
                try {
                    highScore = Integer.parseInt(line.trim());
                } catch (NumberFormatException e1) {
                    //ignore
                }
            }
            reader.close();

        } catch (IOException ex) {
            System.err.println("ERROR reading scores from file");
        }

        System.out.println("The highscore is: " + highScore);

    }

    public void checkHighScore() {
        if (score > this.highScore) {
            this.highScore = score;
            saveHighScore();
        }

        score = 0;
    }

    public void saveHighScore() {
        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(getClass().getClassLoader().getResource("data/highscore.txt").getPath(), true));
            output.write(highScore);
            output.flush();
            output.close();
        } catch (IOException ex1) {
            System.out.printf("ERROR writing score to file: %s\n", ex1);
        }
    }

    public void incrementScore() {
        score++;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }
}
