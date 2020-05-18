package de.bildner.FlappyBird.Game;

import de.bildner.FlappyBird.Entities.Player;
import processing.core.PApplet;
import processing.core.PImage;

public class GameController extends PApplet {

    private static GameController instance;

    private final GameDrawer GAME_DRAWER;
    private final GameEnvironment GAME_ENVIRONMENT;
    private final GameScoreManager GAME_COIN_MANAGER;

    private GameState gameState = GameState.STOP;
    private Player player;

    private PImage flappyTileSet;
    private double factor;

    public static void main(String[] args) {
        PApplet.main(GameController.class.getName());
    }

    public GameController() {
        instance = this;
        player = new Player();
        GAME_DRAWER = new GameDrawer();
        GAME_ENVIRONMENT = new GameEnvironment();
        GAME_COIN_MANAGER = new GameScoreManager();
    }

    @Override
    public void settings() {
        //size(displayWidth / 2, displayHeight / 2);
        fullScreen();
        noSmooth();
    }

    @Override
    public void setup() {
        frameRate(60);
        factor = (double) height / 900d;
        flappyTileSet = loadImage(getClass().getClassLoader().getResource("data/flappy.png").getPath());
        GAME_ENVIRONMENT.init();
        GAME_COIN_MANAGER.init();
        player.init();
        gameState = GameState.WAIT_FOR_START;
        System.out.println(factor);
    }

    @Override
    public void draw() {
        GAME_DRAWER.drawEvent();
    }

    private boolean pressing = false;

    @Override
    public void keyPressed() {
        if (getState() != GameState.DEAD)
            if (keyCode == 32 && !pressing) {
                if (getState() == GameState.WAIT_FOR_START)
                    setState(GameState.RUNNING);
                player.flapp();
                pressing = true;
            }
    }

    @Override
    public void keyReleased() {
        if (keyCode == 32) {
            pressing = false;
        }
    }

    public void restart() {
        setState(GameState.STOP);
        GAME_COIN_MANAGER.checkHighScore();
        getGameEnvironment().createObstacles();
        player.reset();

        //TODO add splash screen
        setState(GameState.WAIT_FOR_START);
    }

    private void setState(GameState gameState) {
        this.gameState = gameState;
    }

    public GameState getState() {
        return gameState;
    }

    Player getPlayer() {
        return player;
    }

    GameEnvironment getGameEnvironment() {
        return GAME_ENVIRONMENT;
    }

    public GameScoreManager getGAME_COIN_MANAGER() {
        return GAME_COIN_MANAGER;
    }

    PImage getFlappyTileSet() {
        return flappyTileSet;
    }

    public double getFactor() {
        return factor;
    }

    public static GameController getInstance() {
        return instance;
    }

    public void setDead() {
        setState(GameState.DEAD);
    }
}
