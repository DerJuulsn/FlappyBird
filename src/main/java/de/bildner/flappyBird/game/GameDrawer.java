package de.bildner.flappyBird.game;

import de.bildner.flappyBird.entities.Player;
import processing.core.PConstants;

import java.util.ArrayList;

class GameDrawer {

    private final GameController controller;
    private final Player player;

    GameDrawer() {
        controller = GameController.getInstance();
        player = controller.getPlayer();
    }

    void drawEvent() {

        if (controller.getState() != GameState.LOADING
                && controller.getState() != GameState.MENU
                && controller.getState() != GameState.STOP) {

            controller.getGameEnvironment().drawMap();

            controller.fill(150, 20, 40);
            controller.stroke(5);

            ArrayList<GameEnvironment.Obstacle> obstacles = controller.getGameEnvironment().getObstacles();
            for (int i = 0; i < obstacles.size(); i++) {

                GameEnvironment.Obstacle obstacle = obstacles.get(i);

                if (obstacle.gotPassed()) {
                    controller.getGAME_COIN_MANAGER().incrementScore();
                }

                if (!obstacle.draw()) {
                    obstacles.remove(i--);
                    continue;
                }

                obstacle.checkForCollision();

            }

            if (controller.getState() != GameState.WAIT_FOR_START)
                player.addGravity();

            player.draw();

            controller.fill(255);
            controller.textSize(30);
            controller.textAlign(PConstants.UP);
            controller.text("Score:         " + controller.getGAME_COIN_MANAGER().getScore(), 15, 45);
            controller.text("High Score: " + controller.getGAME_COIN_MANAGER().getHighScore(), 15, 90);

        }
    }


}
