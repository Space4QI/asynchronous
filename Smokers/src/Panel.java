import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class Panel extends JPanel {
    private Image cigaretteImageSmoking;
    private Image cigaretteImageNotSmoking;
    private Image currentCigaretteImage;

    public Panel() {
        setPreferredSize(new Dimension(100, 100));
        cigaretteImageSmoking = loadImage("src/image/smoker.png");
        cigaretteImageNotSmoking = loadImage("src/image/broken2.png");
        currentCigaretteImage = cigaretteImageNotSmoking;
    }

    private Image loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void changeColor(boolean isSmoking) {
        if (isSmoking) {
            currentCigaretteImage = cigaretteImageSmoking;
        } else {
            currentCigaretteImage = cigaretteImageNotSmoking;
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (currentCigaretteImage != null) {
            g.drawImage(currentCigaretteImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
