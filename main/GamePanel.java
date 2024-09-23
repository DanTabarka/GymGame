package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48
    public final int maxScreenCols = 16;
    public final int maxScreenRows = 12;
    public final int screenWidth = tileSize * maxScreenCols; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRows; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    int FPS = 60;
    public boolean gameFinished = false;
    public double playTime = 0.0;

    // SYSTEM
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Sound music = new Sound();
    Sound soundEffect = new Sound();
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    Thread gameThread;

    // ENTITY AND OBJECTS
    public Player player = new Player(this, keyHandler);
    public SuperObject objects[] = new SuperObject[10];

    public GamePanel () {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);

    }

    public void setupGame() {
        assetSetter.setObject();
        playMusic(0);
    }

    public void startGameThread() {

        gameThread = new Thread(this);
        gameThread.start(); // will automatically call the run method

    }


    @Override
    public void run() {

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(!gameFinished) {

            update();

            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; // miliseconds
                if (remainingTime > 0) {
                    Thread.sleep((long) remainingTime);
                }
                nextDrawTime += drawInterval;
                playTime += 1.0/FPS;

            } catch (InterruptedException e) {
                System.out.println(e);
            }
        }
        gameThread = null;      // END OF THE GAME
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // TILES
        tileManager.draw(g2);
        // OBJECTS
        for (int i = 0; i < objects.length; i++) {
            if (objects[i] != null) {
                objects[i].draw(g2, this);
            }
        }
        // PLAYER
        player.draw(g2);

        // UI
        ui.draw(g2);

        g2.dispose();

    }

    public void playMusic(int index) {
        music.setFile(index);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }
    public void playSoundEffect(int index) {
        soundEffect.setFile(index);
        soundEffect.play();
    }
}
