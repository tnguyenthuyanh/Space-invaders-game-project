package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import view.GameBoard;

public class Shooter extends GameElement {

    public static final int UNIT_MOVE = 10;
    public static final int MAX_BULLETS = 3;
    public static final int MAX_LASER = 1;

    private ArrayList<GameElement> components = new ArrayList<>();
    private ArrayList<GameElement> weapons = new ArrayList<>();

    public Shooter(int x, int y) {
        super(x, y, 0, 0);

        var size = ShooterElement.SIZE;
        var s1 = new ShooterElement(x - size, y - size, Color.WHITE, false, 1);
        var s2 = new ShooterElement(x, y - size, Color.WHITE, false, 2);
        var s3 = new ShooterElement(x - size, y, Color.WHITE, false, 3);
        var s4 = new ShooterElement(x, y, Color.WHITE, false, 4);
        components.add(s1);
        components.add(s2);
        components.add(s3);
        components.add(s4);
    }

    public void moveRight() {
        if (super.x + ShooterElement.SIZE < GameBoard.WIDTH) {
            super.x += UNIT_MOVE;
            for (var c: components) 
                c.x += UNIT_MOVE;     
        }
    }

    public void moveLeft() {
        if (super.x - ShooterElement.SIZE > 0) {
            super.x -= UNIT_MOVE;
            for (var c: components) 
                c.x -= UNIT_MOVE;
        }
    }

    public boolean canFireMoreBullet() {
        int count = 0;
        for (var w : weapons) {
            if (w instanceof Bullet) 
                count++;
        }
        return count < MAX_BULLETS;
    }

    public boolean canFireMoreLaser() {
        int count = 0;
        for (var w : weapons) {
            if (w instanceof Laser) 
                count++;
        }
        return count < MAX_LASER;
    }

    public void removeWeaponsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var w: weapons) 
            if (w.y + 15 < 0) remove.add(w);

        weapons.removeAll(remove);

    }

    public int numOfBullets() {
        int count = 0;
        for (var w: weapons) 
            if (w instanceof Bullet) 
                count++;
        return count;
    }
    
    public ArrayList<GameElement> getWeapons() {
        return weapons;
    }

    public ArrayList<GameElement> getComponents() {
        return components;
    }

    @Override
    public void render(Graphics2D g2) {
        for (var c: components) 
            c.render(g2);
        
        for (var w: weapons) 
            w.render(g2);

    }

    @Override
    public void animate() {
        for (var w: weapons) 
            w.animate();
    }

}
