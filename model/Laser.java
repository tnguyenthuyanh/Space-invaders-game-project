package model;

import java.awt.Color;
import java.awt.Graphics2D;

public class Laser extends GameElement {

    public static final int SIZE = 3;
    public static final int UNIT_MOVE = 15;

    public Laser(int x, int y) {
        super(x, y-50, Color.GRAY, true, SIZE, SIZE*15);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        if (filled) 
            g2.fillOval(x, y, width, height);
        else 
            g2.drawOval(x, y, width, height);
    }

    @Override
    public void animate() {
        super.y -= UNIT_MOVE;
    }
    
}
