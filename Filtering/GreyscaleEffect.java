package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GreyscaleEffect implements Effect {
    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = new Color(image.getRGB(i, j));
                int w = (c.getRed() + c.getBlue() + c.getGreen()) / 3;
                c = new Color(w, w, w);
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
