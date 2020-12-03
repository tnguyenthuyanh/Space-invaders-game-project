package model.strategyPattern;

import java.awt.Graphics2D;
import model.EnemyComposite;

public class EnemyRenderAliveStrategy implements EnemyRenderStrategy {
    
    private EnemyComposite enemyComposite;

    public EnemyRenderAliveStrategy(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        var rows = enemyComposite.getRows();
        var bombs = enemyComposite.getBombs();
        for (var r: rows) {
            for (var e: r) {
                e.render(g2);
            }
        }

        // render bombs
        for (var b: bombs) {
            b.render(g2);
        }
    }
    
}
