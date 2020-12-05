package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.util.ArrayList;
import java.awt.Dimension;

import javax.swing.JPanel;

import model.GameElement;

public class MyCanvas extends JPanel {

    private GameBoard gameBoard;
    private ArrayList<GameElement> gameElements = new ArrayList<>();

    public MyCanvas(GameBoard gameBoard, int width, int height) {
        this.gameBoard = gameBoard;
        setBackground(Color.BLACK);
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        for (var e: gameElements) {
            e.render(g2);
        }
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.50f));
        if (gameBoard.getGameStatus() == GameBoard.GameStatus.WIN) {
            g2.setFont(new Font("Courier New", Font.BOLD, 45));
            g2.setColor(Color.CYAN);
            g2.drawString("You Won!", 230, 180);
            g2.setFont(new Font("Courier New", Font.BOLD, 25));
            g2.drawString("score: " + gameBoard.getScore(), 260, 220);
        }
        else if (gameBoard.getGameStatus() == GameBoard.GameStatus.LOST) {
            g2.setFont(new Font("Courier New", Font.BOLD, 45));
            g2.setColor(Color.RED);
            g2.drawString("You Lost!", 230, 180);
            g2.setFont(new Font("Courier New", Font.BOLD, 25));
            g2.drawString("score: " + gameBoard.getScore(), 260, 220);
        }
    }
    
    public ArrayList<GameElement> getGameElements() {
        return gameElements;
    }
}
