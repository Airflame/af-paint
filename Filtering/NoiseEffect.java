package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NoiseEffect implements Effect {
    private Random generator = new Random();
    private int strength;

    public NoiseEffect(int strength) {
        this.strength = strength;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c = new Color(image.getRGB(i, j));

                if (strength > 0) {
                    int r = (c.getRed() + generator.nextInt(strength) - strength / 2) % 255;
                    if (r < 0)
                        r = 0;
                    int g = (c.getGreen() + generator.nextInt(strength) - strength / 2) % 255;
                    if (g < 0)
                        g = 0;
                    int b = (c.getBlue() + generator.nextInt(strength) - strength / 2) % 255;
                    if (b < 0)
                        b = 0;
                    c = new Color(r, g, b);
                }
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
