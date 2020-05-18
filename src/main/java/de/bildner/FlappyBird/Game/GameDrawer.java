package de.bildner.FlappyBird.Game;

import de.bildner.FlappyBird.Entities.Player;

import java.util.ArrayList;

class GameDrawer {

    private GameController controller;
    private Player player;

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

            controller.textSize(50);
            controller.text(controller.getGAME_COIN_MANAGER().getScore(), 20, 70);
            controller.text(controller.getGAME_COIN_MANAGER().getHighScore(), 20, 140);

        }
    }


}