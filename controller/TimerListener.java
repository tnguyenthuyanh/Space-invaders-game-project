package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

import model.Bullet;
import model.Laser;
import model.Shooter;
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
                        Bullet bullet = new Bullet(shooter.x, shooter.y, gameBoard);
                        shooter.getWeapons().add(bullet);
                        bullets.add(bullet);
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
            if (b.y < 0) {
                b.goNextState();
                remove.add(b);
            }
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
