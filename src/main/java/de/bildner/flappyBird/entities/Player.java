package de.bildner.flappyBird.entities;

import de.bildner.flappyBird.game.GameController;
import de.bildner.flappyBird.game.GameState;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class Player {

    private static final double gravity = 2;   // constant downward acceleration
    private static final int flapping = -22;   // upward acceleration whenever isFlapping is true

    private final GameController controller = GameController.getInstance();

    private double dY = 0; // current vertical speed
    private int y;    // current vertical position
    private int tick = 0;

    private int rotateAfterDeath;

    private int flappyRadius;

    private final ArrayList<PImage> birds = new ArrayList<PImage>();

    public void init() {
        y = controller.height / 2;
        flappyRadius = (int) (35 * controller.getFactor());
        for (int i = 0; i < 4; i++) {
            birds.add(controller.loadImage(getClass().getClassLoader()
                    .getResource("data/birdy/frame" + i + ".png").getPath()));
        }
    }

    public void flap() {
        dY = flapping * controller.getFactor();
    }

    public void addGravity() {

        if (dY < 0 && controller.getState() == GameState.DEAD)
            dY = 0;

        if (dY <= 30)
            dY += gravity * controller.getFactor();
        y += dY;
    }

    public void reset() {
        y = controller.height / 2;
        rotateAfterDeath = 0;
    }

    public int getY() {
        return y;
    }

    public void draw() {

        if (controller.getState() != GameState.DEAD)
            tick++;

        if (tick == 4 || controller.getState() == GameState.DEAD)
            tick = 0;

        if (y + getFlappyRadius() > controller.height / 100 * 92) {
            controller.restart();
            return;
        }

        PImage currentBird = birds.get(tick);
        if (controller.getState() == GameState.DEAD) {
            rotateAfterDeath += 10;
        }

        controller.pushMatrix();

        controller.translate((int) ((320 - flappyRadius) * controller.getFactor()), y - flappyRadius);
        controller.rotate(PApplet.radians(Math.min(90, rotateAfterDeath)));
        controller.copy(currentBird, 0, 0, currentBird.width, currentBird.height, 0,
                0, flappyRadius * 2, flappyRadius * 2);

        controller.popMatrix();
    }

    public int getFlappyRadius() {
        return flappyRadius;
    }
}
