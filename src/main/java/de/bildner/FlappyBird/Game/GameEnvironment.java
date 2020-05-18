package de.bildner.FlappyBird.Game;

import de.bildner.FlappyBird.Entities.Player;
import processing.core.PImage;

import java.util.ArrayList;
import java.util.Random;


class GameEnvironment {

    private PImage background = null;
    private final int tileWidth = 104;
    //(20%)
    private int holeSize;


    private GameController controller;

    public GameEnvironment() {
        controller = GameController.getInstance();
    }

    public void init() {
        holeSize = controller.height / 4;
        loadBackground();
        createObstacles();

    }

    public void createObstacles() {
        obstacles.clear();
        for (int i = 0; i < 10; i++) {
            obstacles.add(new Obstacle(800 + i * 350));
        }
    }

    private void loadBackground() {
        background = controller.loadImage(getClass().getClassLoader().getResource("data/dungeon.jpg").getPath());
    }

    public void drawMap() {
        controller.copy(background, 0, 0, background.width, background.height, 0, 0, controller.width, controller.height);
    }

    private ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();

    public ArrayList<Obstacle> getObstacles() {
        return obstacles;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public class Obstacle {

        private int upperTubeBorder;
        private int x;
        private boolean passed = false;
        private int lowerTubeBorder;

        Obstacle(int xPosition) {
            x = xPosition;
            //possibleHoleHeight = random(10% unten + 10% oben + loch) + 10% unten ->
            upperTubeBorder = new Random().nextInt(controller.height - controller.height / 5 - holeSize)
                    + controller.height / 10;
            lowerTubeBorder = holeSize + upperTubeBorder;
        }

        public boolean draw() {

            if (x <= -tileWidth) {
                obstacles.add(new Obstacle((obstacles.get(obstacles.size() - 1).getX()) + 350));
                return false;
            }

            controller.copy(controller.getFlappyTileSet(), 0, 646, 52, 320, x, upperTubeBorder, tileWidth, -640);
            controller.copy(controller.getFlappyTileSet(), 0, 646, 52, 320, x, lowerTubeBorder, tileWidth, 640);

            if (controller.getState() != GameState.WAIT_FOR_START && controller.getState() != GameState.DEAD)
                x -= 4;

            return true;
        }

        int getUpperTubeBorder() {
            return upperTubeBorder;
        }

        int getLowerTubeBorder() {
            return lowerTubeBorder;
        }

        int getX() {
            return x;
        }

        boolean checkForCollision() {

            if (controller.getState() == GameState.DEAD)
                return false;

            Player player = controller.getPlayer();

            if (getX() - 320 <= player.getFlappyRadius() && (getX() - 320 >= -player.getFlappyRadius() - controller.getGameEnvironment().getTileWidth())) {
                if (player.getY() + player.getFlappyRadius() / 10 * 8 >= getLowerTubeBorder()) {
                    controller.setDead();
                    return true;
                } else if (player.getY() - player.getFlappyRadius() / 10 * 9 <= getUpperTubeBorder()) {
                    controller.setDead();
                    return true;
                }

            }
            return false;
        }

        public boolean gotPassed() {
            if (!passed && x <= 320 - controller.getPlayer().getFlappyRadius()) {
                passed = true;
                return true;
            }
            return false;
        }
    }
}
