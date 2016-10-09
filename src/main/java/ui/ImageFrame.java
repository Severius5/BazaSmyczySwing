package ui;

import logic.FileManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame {

    private String imageName;

    public ImageFrame(final String imageName) {
        this.imageName = imageName;
        setTitle("Obraz smyczy");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(this.DISPOSE_ON_CLOSE);
        setSize(600, 450);

        add(initImage());
        setVisible(true);
    }

    private JComponent initImage() {
        try {
            File file = new File(new FileManager().setPath(imageName));
            BufferedImage image = ImageIO.read(file);
            return new JLabel(new ImageIcon(image.getScaledInstance(600, 450, image.SCALE_DEFAULT)));
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Brak podanego zdjecia",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

}
