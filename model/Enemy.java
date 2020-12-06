package model;

import java.awt.Graphics2D;
import model.images.ImageStore;
import java.awt.Color;

public class Enemy extends GameElement {

    public boolean redEnemy;

    public Enemy(int x, int y, int size, Color color, boolean filled, boolean redEnemy) {
        super(x, y, color, filled, size, size);
        this.redEnemy = redEnemy;
    }

    @Override
    public void render(Graphics2D g2) {
        if (redEnemy)
            g2.drawImage(ImageStore.alien3, null, x, y);
        else if (filled) 
            g2.drawImage(ImageStore.alien1, null, x, y);
        else 
            g2.drawImage(ImageStore.alien2, null, x, y);
    }

    @Override
    public void animate() {
        // compoiste group of enemies

    }
    
}
