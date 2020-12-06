package model.strategyPattern;

import java.awt.Graphics2D;
import model.Enemy;
import model.EnemyComposite;
import model.images.ImageStore;

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
                Enemy enemy = (Enemy) e;
                enemy.setImage(ImageStore.alien1);
                e.render(g2);
            }
        }
    }
    
}
