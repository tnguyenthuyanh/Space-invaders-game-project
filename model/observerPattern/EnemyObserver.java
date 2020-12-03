package model.observerPattern;

import model.EnemyComposite;
import model.strategyPattern.EnemyMoveDeadStrategy;
import model.strategyPattern.EnemyRenderDeadStrategy;
import view.GameBoard;

public class EnemyObserver implements Observer {

    private GameBoard gameBoard;

    public EnemyObserver(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    @Override
    public void lost() {
        int score = gameBoard.getEnemyComposite().getScore();
        if (score > gameBoard.getHighestScore()) {
            gameBoard.setHighestScore(score);
            gameBoard.getHighestScoreDisplay().setText("" + score);
        }
        gameBoard.getStopButton().setEnabled(false);
        EnemyComposite enemyComposite = gameBoard.getEnemyComposite();
        enemyComposite.setMoveStrategy(new EnemyMoveDeadStrategy(enemyComposite));
        enemyComposite.setRenderStrategy(new EnemyRenderDeadStrategy(enemyComposite));
        gameBoard.setGameStatus(GameBoard.GameStatus.LOST);
    }

    @Override
    public void allEnemyDestroyed() {
        int score = gameBoard.getEnemyComposite().getScore();
        if (score > gameBoard.getHighestScore()) {
            gameBoard.setHighestScore(score);
            gameBoard.getHighestScoreDisplay().setText("" + score);
        }
        gameBoard.getStopButton().setEnabled(false);
        gameBoard.setGameStatus(GameBoard.GameStatus.WIN);
    }

    @Override
    public void setScore() {
        int score = gameBoard.getEnemyComposite().getScore();
        gameBoard.setScore(score);
        gameBoard.getScoreDisplay().setText("" + score);

    }
    
}
