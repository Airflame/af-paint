package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RotateEffect implements Effect {
    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getHeight(), image.getWidth(), BufferedImage.TYPE_INT_ARGB);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int i = 0; i < width; i++)
        {
            for (int j = 0; j < height; j++)
            {
                Color c = new Color(image.getRGB(i, j));
                result.setRGB(j, width-i-1, c.getRGB());
            }
        }
        return result;
    }
}
