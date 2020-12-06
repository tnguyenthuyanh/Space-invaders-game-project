package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import model.images.ImageStore;
import model.statePattern.BulletState;
import model.statePattern.FireBulletState;
import view.GameBoard;

public class Bullet extends GameElement {

    public static final int WIDTH = 5;
    public static final int HEIGHT = 15;
    public static final int UNIT_MOVE = 10;
    private BulletState state;
    private BufferedImage image = ImageStore.bullet;

    public Bullet(int x, int y, GameBoard gameBoard) {
        super(x, y-30, Color.RED, true, WIDTH, HEIGHT);
        state = new FireBulletState(gameBoard, this);
    }

    @Override
    public void render(Graphics2D g2) {
        g2.drawImage(image, null, x, y);
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

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
