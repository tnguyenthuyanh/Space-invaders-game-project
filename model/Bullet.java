package model;

import java.awt.Color;
import java.awt.Graphics2D;

import model.images.ImageStore;
import model.statePattern.BulletState;
import model.statePattern.FireBulletState;
import view.GameBoard;

public class Bullet extends GameElement {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 15;
    public static final int UNIT_MOVE = 10;
    private BulletState state;

    public Bullet(int x, int y, GameBoard gameBoard) {
        super(x, y-30, Color.RED, true, WIDTH, HEIGHT);
        state = new FireBulletState(gameBoard);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.setColor(color);
        g2.drawImage(ImageStore.bullet, null, x, y);
        // if (filled)
        //     g2.fillRect(x, y, width, height);
        // else 
        //     g2.drawRect(x, y, width, height);
    }

    @Override
    public void animate() {
        super.y -= UNIT_MOVE;
    }
    
    public void setState(BulletState state) {
        this.state = state;
    }

    public void goNextState() {
        state.goNext(this);
    }

}
