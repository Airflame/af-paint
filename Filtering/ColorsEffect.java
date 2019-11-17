package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorsEffect implements Effect {
    private int rOffset;
    private int gOffset;
    private int bOffset;

    public ColorsEffect(int r, int g, int b) {
        rOffset = r;
        gOffset = g;
        bOffset = b;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = new Color(image.getRGB(i, j));
                int r = c.getRed() + rOffset;
                if (r < 0)
                    r = 0;
                if (r > 255)
                    r = 255;
                int g = c.getGreen() + gOffset;
                if (g < 0)
                    g = 0;
                if (g > 255)
                    g = 255;
                int b = c.getBlue() + bOffset;
                if (b < 0)
                    b = 0;
                if (b > 255)
                    b = 255;
                c = new Color(r, g, b);
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
