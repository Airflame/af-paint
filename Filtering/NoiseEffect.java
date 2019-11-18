package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NoiseEffect implements Effect {
    private Random generator = new Random();
    private int strength;
    private boolean monochromatic = true;

    public NoiseEffect(int strength, boolean monochromatic) {
        this.strength = strength;
        this.monochromatic = monochromatic;
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
                    if (monochromatic) {
                        int r = c.getRed();
                        int g = c.getGreen();
                        int b = c.getBlue();
                        int a = generator.nextInt(strength) - strength / 2;
                        while (r + a < 0 || r + a > 255 || g + a < 0 || g + a > 255 || b + a < 0 || b + a > 255)
                            a = generator.nextInt(strength) - strength / 2;
                        c = new Color(r + a, g + a, b + a);
                    } else {
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
                }
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
