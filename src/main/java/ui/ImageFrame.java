package ui;

import logic.DimensionResizer;
import logic.ImageManager;
import utils.Consts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageFrame extends JFrame
{
    private String imageName;

    public ImageFrame(final String imageName)
    {
        this.imageName = imageName;
        setTitle("Obraz smyczy");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 450);

        add(initImage());
        setVisible(true);
    }

    private JComponent initImage()
    {
        try
        {
            File file = new File(new ImageManager().createPath(imageName));
            BufferedImage image = ImageIO.read(file);

            DimensionResizer resizer = new DimensionResizer(new Dimension(image.getWidth(), image.getHeight()));
            Dimension scaled = resizer.resizeBiggerToFit(this.getSize());

            return new JLabel(new ImageIcon(image.getScaledInstance(scaled.width, scaled.height, Image.SCALE_DEFAULT)));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            FrameHelper.showWarning(null, Consts.noPhotoAvailable);
        }
        return null;
    }

}
