package model.statePattern;

import model.Bullet;
import model.images.ImageStore;
import view.GameBoard;

public class FireBulletState implements BulletState {
    
    private GameBoard gameBoard;
    private Bullet bullet;

    public FireBulletState(GameBoard gameBoard, Bullet bullet) {
        this.gameBoard = gameBoard;
        this.bullet = bullet;
        bullet.setImage(ImageStore.bullet);
    }

    @Override
    public void goNext(Bullet context) {
        context.setState(new BulletPassEnemy(gameBoard, bullet));
    }
    
}
