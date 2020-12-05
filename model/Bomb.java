package model;

import java.awt.Color;
import java.awt.Graphics2D;

import model.images.ImageStore;

public class Bomb extends GameElement {

    public static final int SIZE = 10;
    public static final int UNIT_MOVE = 5;

    public Bomb(int x, int y) {
        super(x, y, Color.GREEN, true, SIZE, SIZE*2);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(ImageStore.bomb, null, x, y);
    }

    @Override
    public void animate() {
        super.y += UNIT_MOVE;
    }
    
}
