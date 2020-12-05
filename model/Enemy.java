package model;

import java.awt.Graphics2D;
import model.images.ImageStore;
import java.awt.Color;

public class Enemy extends GameElement {

    public Enemy(int x, int y, int size, Color color, boolean filled) {
        super(x, y, color, filled, size, size);
    }

    @Override
    public void render(Graphics2D g2) {
        if (filled) 
            g2.drawImage(ImageStore.alien1, null, x, y);
        else 
            g2.drawImage(ImageStore.alien2, null, x, y);
    }

    @Override
    public void animate() {
        // compoiste group of enemies

    }
    
}
