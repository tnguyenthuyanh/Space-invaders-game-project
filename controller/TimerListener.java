package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Bullet;
import model.Laser;
import model.Shooter;
import model.statePattern.FireBulletState;
import view.GameBoard;

public class TimerListener implements ActionListener {

    public enum EventType {
        KEY_RIGHT, KEY_LEFT, KEY_SPACE, KEY_CTRL;
    }

    private GameBoard gameBoard;
    private LinkedList<EventType> eventQueue;
    private final int BOMB_DROP_FREQ = 20;
    private int frameCounter = 0;
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public TimerListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        eventQueue = new LinkedList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            update();
        if (gameBoard.getGameStatus() == GameBoard.GameStatus.PLAYING) {    
            ++frameCounter;
            processEventQueue();
            processCollision();
        }
            gameBoard.getCanvas().repaint();

    }

    private void processEventQueue() {
        while (!eventQueue.isEmpty()) {
            var e = eventQueue.getFirst();
            eventQueue.removeFirst();
            Shooter shooter = gameBoard.getShooter();
            if (shooter == null) return;

            switch (e) {
                case KEY_LEFT:
                    shooter.moveLeft();
                    break;
                case KEY_RIGHT:
                    shooter.moveRight();
                    break;
                case KEY_SPACE:
                    if (shooter.canFireMoreBullet()) {
                        Bullet bullet = new Bullet(shooter.x - 2, shooter.y, gameBoard);
                        bullets.add(bullet);
                        shooter.getWeapons().add(bullet);
                        if (gameBoard.getEnemyComposite().isRedEnemyRemoved()) {
                            bullet.x = shooter.x - 11;
                            Bullet bullet2 = new Bullet(shooter.x + 8, shooter.y, gameBoard);
                            bullets.add(bullet2);
                            shooter.getWeapons().add(bullet2);
                            gameBoard.getShooter().MAX_BULLETS = 18;
                        }
                    }
                    break;
                case KEY_CTRL:
                    if (shooter.canFireMoreLaser())
                        shooter.getWeapons().add(new Laser(shooter.x, shooter.y));
                    break;
            }
        }
        if (frameCounter == BOMB_DROP_FREQ) {
            gameBoard.getEnemyComposite().dropBombs();
            frameCounter = 0;
        }
    }

    private void processCollision() {
        var shooter = gameBoard.getShooter();
        var enemyComposite = gameBoard.getEnemyComposite();

        var remove = new ArrayList<Bullet>();
        for (var b: bullets) {
            if (b.y < enemyComposite.topEnd())
                b.goNextState();
            if (b.y + 15 < 0) 
                remove.add(b);
        }
        bullets.removeAll(remove);

        shooter.removeWeaponsOutOfBound();
        enemyComposite.removeBombsOutOfBound();
        enemyComposite.processCollision(shooter);
    }

    private void update() {
        for (var e: gameBoard.getCanvas().getGameElements())
            e.animate();
    }

    public LinkedList<EventType> getEventQueue() {
        return eventQueue;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }
}
