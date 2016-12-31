package logic;

import java.awt.*;

public class DimensionResizer
{
    private Dimension original;

    /**
     * Creates an instance of DimensionResizer with given original dimension.
     *
     * @param originalDimension original dimension.
     */
    public DimensionResizer(Dimension originalDimension)
    {
        this.original = originalDimension;
    }

    /**
     * Resizes original dimension to fit in given dimension
     * @param toFit dimension to fit
     * @return resized dimension
     */
    public Dimension resizeToFit(Dimension toFit)
    {
        if (toFit == null)
        {
            throw new NullPointerException("Dimension can't be null");
        }

        double dScaleWidth = getScaleFactor(original.width, toFit.width);
        double dScaleHeight = getScaleFactor(original.height, toFit.height);

        double dScale = Math.min(dScaleHeight, dScaleWidth);

        int scaleWidth = (int) Math.round(original.getWidth() * dScale);
        int scaleHeight = (int) Math.round(original.getHeight() * dScale);

        return new Dimension(scaleWidth, scaleHeight);
    }

    /**
     * Resizes original dimension to fit in smaller dimension than original
     * @param toFit dimension to fit
     * @return resized dimension if toFit is smaller or original dimension
     */
    public Dimension resizeBiggerToFit(Dimension toFit)
    {
        if (toFit == null)
        {
            throw new NullPointerException("Dimension can't be null");
        }

        double dScaleWidth = getScaleFactor(original.width, toFit.width);
        double dScaleHeight = getScaleFactor(original.height, toFit.height);

        double dScale = Math.min(dScaleHeight, dScaleWidth);

        if (dScale < 1d)
        {
            int scaleWidth = (int) Math.round(original.getWidth() * dScale);
            int scaleHeight = (int) Math.round(original.getHeight() * dScale);

            return new Dimension(scaleWidth, scaleHeight);
        }
        else
        {
            return original;
        }
    }

    private double getScaleFactor(int iMasterSize, int iTargetSize)
    {
        return (double) iTargetSize / iMasterSize;
    }

}
