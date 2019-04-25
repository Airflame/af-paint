package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InvertEffect implements Effect {
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
                int r = 255 - c.getRed();
                int g = 255 - c.getGreen();
                int b = 255 - c.getBlue();
                c = new Color(r, g, b);
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
