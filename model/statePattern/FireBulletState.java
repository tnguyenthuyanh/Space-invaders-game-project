package model.statePattern;

import java.awt.Color;

import model.Bullet;
import view.GameBoard;

public class FireBulletState implements BulletState {
    
    private GameBoard gameBoard;

    public FireBulletState(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        if (gameBoard.getShooter().numOfBullets() == 2)
            for (var c: gameBoard.getShooter().getComponents())
                c.color = Color.GREEN;
        else 
            for (var c: gameBoard.getShooter().getComponents())
                c.color = Color.WHITE;
    }

    @Override
    public void goNext(Bullet context) {
        context.setState(new BulletOutOfBoundState(gameBoard));
    }
    
}
