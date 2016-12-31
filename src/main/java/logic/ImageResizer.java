package logic;

import java.awt.*;

public class ImageResizer
{
    private Dimension image;

    /**
     * Creates instance of ImageResizer with image dimension.
     *
     * @param originalDimension image dimension.
     */
    public ImageResizer(Dimension originalDimension)
    {
        this.image = originalDimension;
    }

    public Dimension resizeToFit(Dimension toFit)
    {
        if (toFit == null)
        {
            throw new NullPointerException("Dimension can't be null");
        }

        double dScaleWidth = getScaleFactor(image.width, toFit.width);
        double dScaleHeight = getScaleFactor(image.height, toFit.height);

        double dScale = Math.min(dScaleHeight, dScaleWidth);

        int scaleWidth = (int) Math.round(image.getWidth() * dScale);
        int scaleHeight = (int) Math.round(image.getHeight() * dScale);

        return new Dimension(scaleWidth, scaleHeight);
    }

    private double getScaleFactor(int iMasterSize, int iTargetSize)
    {
        return (double) iTargetSize / iMasterSize;
    }

}
