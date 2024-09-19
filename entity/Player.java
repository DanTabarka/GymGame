package entity;

import main.GamePanel;
import main.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity{

    GamePanel gp;
    KeyHandler keyHandler;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        directioin = "down";
    }

    public void getPlayerImage() {

        try {

            up1 = ImageIO.read(getClass().getResourceAsStream("/res/player/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/res/player/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/res/player/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/res/player/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/res/player/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/res/player/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/res/player/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/res/player/right2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyHandler.up || keyHandler.down || keyHandler.left || keyHandler.right) {
            if (keyHandler.up) {
                directioin = "up";
                y -= speed;
            } else if (keyHandler.down) {
                directioin = "down";
                y += speed;
            } else if (keyHandler.left) {
                directioin = "left";
                x -= speed;
            } else if (keyHandler.right) {
                directioin = "right";
                x += speed;
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                sprite = !sprite;
            }
        }
    }

    public void draw(Graphics g2) {
        // g2.setColor(Color.white);
        // g2.fillRect(x, y, gp.tileSize, gp.tileSize);

        BufferedImage image = null;

        switch(directioin) {
            case "up" :
                if (sprite) {
                    image = up1;
                } else {
                    image = up2;
                }
                break;
            case "down":
                if (sprite) {
                    image = down1;
                } else {
                    image = down2;
                }
                break;
            case "left":
                if (sprite) {
                    image = left1;
                } else {
                    image = left2;
                }
                break;
            case "right":
                if (sprite) {
                    image = right1;
                } else {
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }


}
