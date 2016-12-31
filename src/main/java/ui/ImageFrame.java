package ui;

import logic.ImageManager;
import utils.Consts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {

    private String imageName;

    public ImageFrame(final String imageName) {
        this.imageName = imageName;
        setTitle("Obraz smyczy");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 450);

        add(initImage());
        setVisible(true);
    }

    private JComponent initImage() {
        try {
            ImageManager imageManager = new ImageManager();
            File file = new File(imageManager.createPath(imageName));
            BufferedImage image = ImageIO.read(file);

            double scaleFactor = Math.min(1d, imageManager.getScaleFactorToFit(new Dimension(image.getWidth(), image.getHeight()), this.getSize()));

            int scaleWidth = (int) Math.round(image.getWidth() * scaleFactor);
            int scaleHeight = (int) Math.round(image.getHeight() * scaleFactor);

            return new JLabel(new ImageIcon(image.getScaledInstance(scaleWidth, scaleHeight, Image.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
            FrameHelper.showWarning(null, Consts.noPhotoAvailable);
        }
        return null;
    }

}
