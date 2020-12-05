package model.images;

import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import model.Bomb;
import model.Bullet;
import model.EnemyComposite;
import model.ShooterElement;

public class ImageStore {

    public static BufferedImage alien1;
    public static BufferedImage alien2;
    public static BufferedImage shooter1;
    public static BufferedImage shooter2;
    public static BufferedImage shooter3;
    public static BufferedImage shooter4;
    public static BufferedImage bullet;
    public static BufferedImage bullet2;
    public static BufferedImage bomb;
    public static BufferedImage explosion;

    static {
        // although images png in same folder with this file but the Main java runs from a different package 
        // -> put in path
        alien1 = readImage("model/images/alien1.png", EnemyComposite.ENEMY_SIZE, EnemyComposite.ENEMY_SIZE);
        alien2 = readImage("model/images/alien2.png", EnemyComposite.ENEMY_SIZE, EnemyComposite.ENEMY_SIZE);
        shooter1 = readImage("model/images/shooter1.png", ShooterElement.SIZE, ShooterElement.SIZE);
        shooter2 = readImage("model/images/shooter2.png", ShooterElement.SIZE, ShooterElement.SIZE);
        shooter3 = readImage("model/images/shooter3.png", ShooterElement.SIZE, ShooterElement.SIZE);
        shooter4 = readImage("model/images/shooter4.png", ShooterElement.SIZE, ShooterElement.SIZE);
        bullet = readImage("model/images/bullet.png", Bullet.WIDTH, Bullet.HEIGHT);
        bullet2 = readImage("model/images/bullet2.png", Bullet.WIDTH, Bullet.HEIGHT);
        bomb = readImage("model/images/bomb.png", Bomb.SIZE, Bomb.SIZE*2);
        explosion = readImage("model/images/explosion.png", 3, 3);
    }
    
    public static BufferedImage readImage(String path, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(path));
            Image tmp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImage.createGraphics();
            g2.drawImage(tmp, 0, 0, null);
            g2.dispose();
            return resizedImage;
        } catch (Exception e) {
            System.out.println("Image file load error");
        }
        return null;
    }
}
