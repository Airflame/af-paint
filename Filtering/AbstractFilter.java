package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class AbstractFilter implements Effect {
    protected double kernel[][];

    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gr = result.getGraphics();
        gr.drawImage(image, 0, 0, null);
        gr.dispose();

        int height = image.getHeight();
        int width = image.getWidth();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int kernX = 0; kernX < kernel.length; kernX++) {
                    for (int kernY = 0; kernY < kernel.length; kernY++) {
                        int i = x + kernX - kernel.length / 2;
                        int j = y + kernY - kernel.length / 2;
                        if (i < 0)
                            i = 0;
                        if (i >= width)
                            i = width - 1;
                        if (j < 0)
                            j = 0;
                        if (j >= height)
                            j = height - 1;
                        Color c = new Color(image.getRGB(i, j));
                        r += kernel[kernY][kernX] * c.getRed();
                        g += kernel[kernY][kernX] * c.getGreen();
                        b += kernel[kernY][kernX] * c.getBlue();
                    }
                }
                if (r > 255)
                    r = 255;
                if (g > 255)
                    g = 255;
                if (b > 255)
                    b = 255;
                if (r < 0)
                    r = 0;
                if (g < 0)
                    g = 0;
                if (b < 0)
                    b = 0;
                Color c = new Color(r, g, b);
                result.setRGB(x, y, c.getRGB());
            }
        }
        return result;
    }
}
