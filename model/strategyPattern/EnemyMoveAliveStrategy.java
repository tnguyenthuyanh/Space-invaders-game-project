package model.strategyPattern;

import model.EnemyComposite;
import model.EnemyComposite.Event;
import view.GameBoard;

public class EnemyMoveAliveStrategy implements EnemyMoveStrategy {

    private EnemyComposite enemyComposite;
    private boolean movingToRight = true;

    public EnemyMoveAliveStrategy(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
    }

    public void moveAlgorithm() {
        int dx = EnemyComposite.UNIT_MOVE;
        int dy = EnemyComposite.UNIT_DOWN;
        if (movingToRight) {
            if (rightEnd() >= GameBoard.WIDTH) {
                dx = -dx;
                for (var row: enemyComposite.getRows()) {
                    for (var e: row) {
                        e.y += dy;
                    }
                }
                movingToRight = false;
            }
        } else {
            dx = -dx;
            if (leftEnd() <= 0) {
                dx = -dx;
                for (var row: enemyComposite.getRows()) {
                    for (var e: row) {
                        e.y += dy;
                    }
                }
                movingToRight = true;
            }
        }

        if (bottomEnd() >= GameBoard.HEIGHT) {
            enemyComposite.notifyObservers(Event.Lost);
            
        }

        // update x loc
        for (var row: enemyComposite.getRows()) {
            for (var e: row) {
                e.x += dx;
            }
        }

        // animate bombs
        for (var b: enemyComposite.getBombs()) 
            b.animate();
    }

    private int rightEnd() {
        int xEnd = -100;
        for (var row: enemyComposite.getRows()) {
            if (row.size() == 0) continue;
            int x = row.get(row.size() -1).x + EnemyComposite.ENEMY_SIZE;
            if (x > xEnd) xEnd = x;
        }
        return xEnd;
    }
    
    private int leftEnd() {
        int xEnd = 9000;
        for (var row: enemyComposite.getRows()) {
            if (row.size() == 0) continue;
            int x = row.get(0).x;
            if (x < xEnd) xEnd = x;
        }
        return xEnd;
    }

    private int bottomEnd() {
        int yEnd = -100;
        for (var row : enemyComposite.getRows()) {
            if (row.size() == 0)
                continue;
            int y = row.get(0).y + EnemyComposite.ENEMY_SIZE;
            if (y > yEnd)
                yEnd = y;
        }
        return yEnd;
    }
}
