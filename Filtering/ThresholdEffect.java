package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ThresholdEffect implements Effect {
    private int threshold;
    private Color brightColor;
    private Color darkColor;

    public ThresholdEffect(int t, Color bc, Color dc) {
        threshold = t;
        brightColor = bc;
        darkColor = dc;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                Color c = new Color(image.getRGB(i, j));
                int w = (c.getRed() + c.getBlue() + c.getGreen())/3;
                if(w > threshold)
                    result.setRGB(i, j, brightColor.getRGB());
                else
                    result.setRGB(i, j, darkColor.getRGB());
            }
        }
        return result;
    }
}
