package model;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class Enemy extends GameElement {

    public boolean redEnemy;
    private BufferedImage image;

    public Enemy(int x, int y, int size, Color color, boolean filled, boolean redEnemy) {
        super(x, y, color, filled, size, size);
        this.redEnemy = redEnemy;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(image, null, x, y);
    }

    @Override
    public void animate() {
        // compoiste group of enemies
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    
}
