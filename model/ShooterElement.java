package model;

import java.awt.Graphics2D;
import model.images.ImageStore;
import java.awt.Color;

public class ShooterElement extends GameElement {

    public static final int SIZE = 20;
    private int position;

    public ShooterElement(int x, int y, Color color, boolean filled, int position) {
        super(x, y, color, filled, SIZE, SIZE);
        this.position = position;
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        if (position == 1) 
            g2.drawImage(ImageStore.shooter1, null, x, y);
        else if (position == 2) 
            g2.drawImage(ImageStore.shooter2, null, x, y);
        else if (position == 3) 
            g2.drawImage(ImageStore.shooter3, null, x, y);
        else 
            g2.drawImage(ImageStore.shooter4, null, x, y);
    }

    @Override
    public void animate() {}
    
}
