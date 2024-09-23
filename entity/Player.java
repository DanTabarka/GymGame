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
    public final int screenX;
    public final int screenY;
    int hasKey = 0;

    public Player(GamePanel gp, KeyHandler keyHandler) {
        this.gp = gp;
        this.keyHandler = keyHandler;

        screenX = gp.screenWidth/2 - gp.tileSize/2;
        screenY = gp.screenHeight/2 - gp.tileSize/2;

        solidArea = new Rectangle(12, 16, gp.tileSize - 24, gp.tileSize - 16);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 6;                  // speed 4
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
            } else if (keyHandler.down) {
                directioin = "down";
            } else if (keyHandler.left) {
                directioin = "left";
            } else if (keyHandler.right) {
                directioin = "right";
            }

            // CHECK TILE COLLISION
            collisionOn = false;
            gp.collisionChecker.checkTile(this);
            // CHECK OBJECT COLLISION
            int objectIndex = gp.collisionChecker.checkObject(this, true);
            pickUpObject(objectIndex);

            if (!collisionOn) {
                switch (directioin) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 10) {
                spriteCounter = 0;
                sprite = !sprite;
            }
        }
    }

    public void pickUpObject(int index) {
        if (index >= 0) {
            String objectName = gp.objects[index].name;

            switch (objectName) {
                case "Key":
                    gp.playSoundEffect(1);
                    hasKey++;
                    gp.objects[index] = null;
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gp.playSoundEffect(3);
                        gp.objects[index] = null;
                        hasKey--;
                    }
                    break;
                case "Boots":
                    gp.playSoundEffect(2);
                    speed += 2;
                    gp.objects[index] = null;
                    break;
            }
        }

    }

    public void draw(Graphics g2) {
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

        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }


}
