package model.statePattern;

import model.Bullet;
import model.images.ImageStore;
import view.GameBoard;

public class BulletPassEnemy implements BulletState {

    public BulletPassEnemy(GameBoard gameBoard, Bullet bullet) {
        bullet.setImage(ImageStore.bullet2);
    }

    @Override
    public void goNext(Bullet context) {
        context.setState(this);
    }

}