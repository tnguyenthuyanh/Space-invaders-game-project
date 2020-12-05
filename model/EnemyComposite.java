package model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import model.observerPattern.Observer;
import model.observerPattern.Subject;
import model.strategyPattern.EnemyMoveAliveStrategy;
import model.strategyPattern.EnemyMoveStrategy;
import model.strategyPattern.EnemyRenderAliveStrategy;
import model.strategyPattern.EnemyRenderStrategy;
import view.GameBoard;

public class EnemyComposite extends GameElement implements Subject {

    public static final int NROWS = 2;
    public static final int NCOLS = 11;
    public static final int ENEMY_SIZE = 20;
    public static final int UNIT_MOVE = 5;
    public static final int UNIT_DOWN = 20;

    private GameBoard gameBoard;
    private ArrayList<ArrayList<GameElement>> rows;
    private ArrayList<GameElement> bombs;
    private ArrayList<Observer> observers = new ArrayList<>();
    private Random random = new Random();
    private int score = 0;

    private EnemyMoveStrategy moveStrategy;
    private EnemyRenderStrategy renderStrategy;

    public enum Event {
        Lost, AllEnemyDestroyed, SetScore
    }

    public EnemyComposite(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        rows = new ArrayList<>();
        bombs = new ArrayList<>();

        boolean filled = true;
        for (int r = 0; r < NROWS; r++) {
            var oneRow = new ArrayList<GameElement>();
            rows.add(oneRow);
            for (int c = 0; c < NCOLS; c++) {
                filled = !filled;
                oneRow.add(new Enemy(c * ENEMY_SIZE * 2, r * ENEMY_SIZE * 2, ENEMY_SIZE, Color.YELLOW, filled));
            }
        }
        moveStrategy = new EnemyMoveAliveStrategy(this);
        renderStrategy = new EnemyRenderAliveStrategy(this);
    }

    @Override
    public void render(Graphics2D g2) {
        this.renderStrategy.renderAlgorithm(g2);
    }

    @Override
    public void animate() {
        this.moveStrategy.moveAlgorithm();
    }

    public void dropBombs() {
        for (var row : rows) {
            for (var e : row) {
                if (random.nextFloat() < 0.1F) { // 10 percent to drop bomb
                    bombs.add(new Bomb(e.x, e.y));
                }
            }
        }
    }

    public void removeBombsOutOfBound() {
        var remove = new ArrayList<GameElement>();
        for (var b : bombs) {
            if (b.y >= GameBoard.HEIGHT) {
                remove.add(b);
            }
        }
        bombs.removeAll(remove);
    }

    public void processCollision(Shooter shooter) {
        var removeBullets = new ArrayList<GameElement>();
        int empty = 0;
        // enemies vs shooter
        for (var row : rows) 
            for (var enemy : row) 
                for (var s : shooter.getComponents()) 
                    if (enemy.collideWith(s)) 
                        notifyObservers(Event.Lost);
            
        // bullets, laser vs enemies
        for (var row : rows) {
            var removeEnemies = new ArrayList<GameElement>();
            for (var enemy : row) {
                for (var w : shooter.getWeapons()) {
                    if (enemy.collideWith(w)) {
                        if (w instanceof Bullet) 
                            removeBullets.add(w);
                        removeEnemies.add(enemy);  
                    }
                }
            }
            row.removeAll(removeEnemies);
            if (row.size() == 0) empty++;

            score = gameBoard.getScore() + removeEnemies.size() * 20;
            notifyObservers(Event.SetScore);

            if (empty == 2)
                notifyObservers(Event.AllEnemyDestroyed);
        }
        shooter.getWeapons().removeAll(removeBullets);

        // bullets, laser vs bombs
        var removeBombs = new ArrayList<GameElement>();
        removeBullets.clear();

        for (var b : bombs) {
            for (var w : shooter.getWeapons()) {
                if (b.collideWith(w)) {
                    if (w instanceof Bullet) 
                        removeBullets.add(w);
                    removeBombs.add(b);  
                }
            }
        }
        score = gameBoard.getScore() + removeBombs.size() * 5;
        notifyObservers(Event.SetScore);
        shooter.getWeapons().removeAll(removeBullets);
        bombs.removeAll(removeBombs);

        // bombs vs shooter
        var removeShooter = new ArrayList<GameElement>();
        removeBombs.clear();
        for (var b : bombs) {
            for (var s : shooter.getComponents()) {
                if (b.collideWith(s)) {
                    removeShooter.add(s);
                    removeBombs.add(b);
                }
            }
        }
        shooter.getComponents().removeAll(removeShooter);
        bombs.removeAll(removeBombs);
        if (shooter.getComponents().size() == 0) 
            notifyObservers(Event.Lost);

    }

    @Override
    public void addEnemyListener(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeEnemnyListener(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(Event event) {
        switch (event) {
            case Lost:
                for (var o : observers) {
                    o.lost();
                }
                break;
            case AllEnemyDestroyed:
                for (var o : observers) {
                    o.allEnemyDestroyed();
                }
                break;
            case SetScore:
                for (var o : observers) {
                    o.setScore();
                }
            break;
        }
    }

    public ArrayList<ArrayList<GameElement>> getRows() {
        return rows;
    }

    public ArrayList<GameElement> getBombs() {
        return bombs;
    }

    public int getScore() {
        return score;
    }

    public void setMoveStrategy(EnemyMoveStrategy moveStrategy) {
        this.moveStrategy = moveStrategy;
    }

    public void setRenderStrategy(EnemyRenderStrategy renderStrategy) {
        this.renderStrategy = renderStrategy;
    }

    public int topEnd() {
        int yEnd = 0;
        for (var row: rows) {
            if (row.size() == 0) continue;
            yEnd = row.get(row.size() -1).y;
            break;
        }
        return yEnd;
    }

}
