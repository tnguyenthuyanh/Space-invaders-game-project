package model.strategyPattern;

import java.awt.Graphics2D;
import model.Enemy;
import model.EnemyComposite;
import model.images.ImageStore;

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
                Enemy enemy = (Enemy) e;
                if (enemy.redEnemy)
                    enemy.setImage(ImageStore.alien3);
                else if (e.filled) 
                    enemy.setImage(ImageStore.alien1);
                else 
                    enemy.setImage(ImageStore.alien2);
                e.render(g2);
            }
        }

        // render bombs
        for (var b: bombs) {
            b.render(g2);
        }
    }
    
}
