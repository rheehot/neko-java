package net.honux.neko;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The Class which have responsibility to draw all GameObjects
 */
public class Renderer extends JPanel {

    private MainWindow window;
    private BufferedImage background;
    private long frame = 0;

    public Renderer(MainWindow window) {
        this.window = window;
        setupBackGround();
    }

    //Load bg image and setup panel
    private void setupBackGround() {
        try {
            background = ImageIO.read(new File("./resources/bg.png"));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(new JFrame(), "BG loading error: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        setDoubleBuffered(true);
        setSize(background.getWidth(), background.getHeight());
    }

    @Override
    public void paintComponents(Graphics g) {
        if (g == null) return;

        Neko neko = window.getNeko();
        g.drawImage(background, 0, 0, window);
        drawCoins(g);
        drawImageByScale(g, neko);
    }

    private void drawImageByScale(Graphics g, GameObject go) {
        int x = go.getX() - go.getW() * MainWindow.SCALE / 2;
        int y = go.getY() - go.getH() * MainWindow.SCALE;
        int w = go.getW();
        int h = go.getH();
        g.drawImage(go.getImage(window.getFrame()), x, y, x + w * MainWindow.SCALE, y + h * MainWindow.SCALE,
                0, 0, w, h, window);
    }

    private void drawCoins(Graphics g) {
        for(var coin: window.getCoins()) drawImageByScale(g, coin);
    }

    public void update() {
        int frame = window.getFrame();
        System.out.println(frame);

        //Update Neko
        Neko neko = window.getNeko();
        neko.update(frame);

        //Uodate coin after Neko
        var coins = window.getCoins();
        for (var coin: coins) coin.update(frame);
    }

    public void render() {
        paintComponents(this.getGraphics());
    }
}
