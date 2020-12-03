package model.strategyPattern;

import java.awt.Color;
import java.awt.Graphics2D;
import model.EnemyComposite;

public class EnemyRenderDeadStrategy implements EnemyRenderStrategy {
    
    private EnemyComposite enemyComposite;

    public EnemyRenderDeadStrategy(EnemyComposite enemyComposite) {
        this.enemyComposite = enemyComposite;
    }

    @Override
    public void renderAlgorithm(Graphics2D g2) {
        var rows = enemyComposite.getRows();
        for (var r: rows) {
            for (var e: r) {
                e.color = Color.GRAY;
                e.render(g2);
            }
        }
    }
    
}
