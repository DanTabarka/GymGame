package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class UI {

    GamePanel gp;
    Font arial_40 = new Font("Arial", Font.PLAIN, 40);
    Font arial_30 = new Font("Arial", Font.PLAIN, 30);
    Font arial_80B = new Font("Arial", Font.BOLD, 80);
    BufferedImage keyImage;
    public boolean messageOn = false;
    public String message = "";
    int messageTimer = 0;
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.ENGLISH); // force it to use . instead of ,
    DecimalFormat decimalFormat = new DecimalFormat("#0.00", symbols);

    public UI(GamePanel gp) {
        this.gp = gp;
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {
        if (gp.gameFinished) {
            g2.setFont(arial_40);
            g2.setColor(Color.white);

            String text = "You found the treasure!";
            int textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            int x = gp.screenWidth/2 - textLength/2;
            int y = gp.screenHeight/2 - gp.tileSize * 3;
            g2.drawString(text, x, y);

            text = "Your time is: " + decimalFormat.format(gp.playTime) + "!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize * 4;
            g2.drawString(text, x, y);

            g2.setFont(arial_80B);
            g2.setColor(Color.yellow);
            text = "Congratulatioins!";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth/2 - textLength/2;
            y = gp.screenHeight/2 + gp.tileSize * 2;
            g2.drawString(text, x, y);

            return;     // END OF THE GAME
        }

        g2.setFont(arial_40);
        g2.setColor(Color.white);
        g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
        g2.drawString("x " + gp.player.hasKey, 74, 65);

        // TIME
        g2.drawString("Time: " + decimalFormat.format(gp.playTime), gp.tileSize * 11, 65);

        // MESSAGE
        if (messageOn) {
            g2.setFont(arial_30);
            g2.drawString(message, gp.tileSize/2, gp.tileSize * 5);

            messageTimer++;
            if (messageTimer > 120) {
                messageTimer = 0;
                messageOn = false;
            }
        }
    }
}
