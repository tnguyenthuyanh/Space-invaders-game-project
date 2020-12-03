package model.strategyPattern;

import model.EnemyComposite;
import view.GameBoard;

public class EnemyMoveDeadStrategy implements EnemyMoveStrategy {

    private EnemyComposite enemyComposite;

    public EnemyMoveDeadStrategy(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
    }

    @Override
    public void moveAlgorithm() {
        int dy = 8;
        for (var row: enemyComposite.getRows())
            for (var e: row) 
                e.y += dy;
        
        if (TopY() > GameBoard.HEIGHT) {
            int u = 0;
            for (var row: enemyComposite.getRows()) {     
                for (var e: row) 
                    e.y = 0 + u*EnemyComposite.ENEMY_SIZE*2;
                u++;
            }
        }
    }    

    private int TopY() {
        int topY = 0;
        outerloop:
        for (var row: enemyComposite.getRows()) {
            if (row.size() == 0) 
                continue;
            for (var e: row) {
                topY = e.y;
                break outerloop;
            }
        }
        return topY;
    }
}
