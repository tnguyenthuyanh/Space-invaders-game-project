package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import controller.KeyController;
import controller.TimerListener;
import model.EnemyComposite;
import model.Shooter;
import model.ShooterElement;
import model.observerPattern.EnemyObserver;

public class GameBoard {

    public static final int WIDTH = 700;
    public static final int HEIGHT = 400;

    public static final int FPS = 20;
    public static final int DELAY = 1000 / FPS;

    public enum GameStatus {
        PLAYING, WIN, LOST
    }
    
    private JFrame window;
    private MyCanvas canvas;
    private Shooter shooter;
    private EnemyComposite enemyComposite;
    private Timer timer;
    private TimerListener timerListener;
    private GameStatus gameStatus = GameStatus.PLAYING;

    private JButton startButton = new JButton("Start");
    private JButton quitButton = new JButton("Quit");
    private JButton stopButton = new JButton("Stop");
    private JLabel scoreDisplay = new JLabel();
    private JLabel highestScoreDisplay = new JLabel();
    private int score = 0;
    private int highestScore = 0;

    public GameBoard(JFrame window) {
        this.window = window;
    }

    public void init() {
        Container cp = window.getContentPane();

        canvas = new MyCanvas(this, WIDTH, HEIGHT);
        cp.add(BorderLayout.CENTER, canvas);
        canvas.addKeyListener(new KeyController(this));
        canvas.requestFocusInWindow();
        canvas.setFocusable(true);

        JPanel northPanel = new JPanel();
        JLabel label = new JLabel("Score: ");
        JLabel highestSclabel = new JLabel("| Highest Score: ");
        northPanel.add(label);
        scoreDisplay.setText("" + score);
        northPanel.add(scoreDisplay);
        northPanel.add(highestSclabel);
        highestScoreDisplay.setText("" + score);
        northPanel.add(highestScoreDisplay);
        cp.add(BorderLayout.NORTH, northPanel);
        label.setFocusable(false);
        scoreDisplay.setFocusable(false);
        highestSclabel.setFocusable(false);
        highestScoreDisplay.setFocusable(false);
        startButton.setFocusable(false);
        quitButton.setFocusable(false);
        stopButton.setFocusable(false);
        stopButton.setEnabled(false);

        JPanel southPanel = new JPanel();
        southPanel.add(startButton);
        southPanel.add(stopButton);
        southPanel.add(quitButton);
        cp.add(BorderLayout.SOUTH, southPanel);

        canvas.getGameElements().add(new TextDraw("Click <Start> to Play", 150, 180, Color.YELLOW, 30));

        timerListener = new TimerListener(this);
        timer = new Timer(DELAY, timerListener);

        startButton.addActionListener(event -> {
            stopButton.setEnabled(true);
            stopButton.setText("Stop");
            timerListener.getBullets().clear();
            gameStatus = GameStatus.PLAYING;
            score = 0;
            scoreDisplay.setText("" + score);
            canvas.getGameElements().clear();
            shooter = new Shooter(GameBoard.WIDTH/2,GameBoard.HEIGHT - ShooterElement.SIZE);
            enemyComposite = new EnemyComposite(this);
            EnemyObserver observer = new EnemyObserver(this);
            enemyComposite.addEnemyListener(observer);
            canvas.getGameElements().add(shooter);
            canvas.getGameElements().add(enemyComposite);
            timer.start();
        });

        stopButton.addActionListener(event -> {
            String labelButton = stopButton.getText();
            if (labelButton.equals("Stop")) {
                stopButton.setText("Resume");
                timer.stop();
            } else {
                stopButton.setText("Stop");
                timer.start();
            }
        });

        quitButton.addActionListener(event -> System.exit(0));
    }


    public MyCanvas getCanvas() {
        return canvas;
    }

    public TimerListener getTimerListener() {
        return timerListener;
    }

    public Timer getTimer() {
        return timer;
    }

    public Shooter getShooter() {
        return shooter;
    }
    
    public EnemyComposite getEnemyComposite() {
        return enemyComposite;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public JLabel getScoreDisplay() {
        return scoreDisplay;
    }

    public JLabel getHighestScoreDisplay() {
        return highestScoreDisplay;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public JButton getStopButton() {
        return stopButton;
    }

}
