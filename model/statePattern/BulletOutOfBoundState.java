package model.statePattern;

import java.awt.Color;
import model.Bullet;
import view.GameBoard;

public class BulletOutOfBoundState implements BulletState {

    public BulletOutOfBoundState(GameBoard gameBoard) {
        for (var c: gameBoard.getShooter().getComponents())
            c.color = Color.WHITE;
    }

    @Override
    public void goNext(Bullet context) {
        context.setState(null);
    }



}