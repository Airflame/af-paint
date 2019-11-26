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
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int kern_x = 0; kern_x < kernel.length; kern_x++) {
                    for (int kern_y = 0; kern_y < kernel.length; kern_y++) {
                        int x = i + kern_x - kernel.length / 2;
                        int y = j + kern_y - kernel.length / 2;
                        if (x < 0)
                            x = 0;
                        if (x >= width)
                            x = width - 1;
                        if (y < 0)
                            y = 0;
                        if (y >= height)
                            y = height - 1;
                        Color c = new Color(image.getRGB(x, y));
                        r += kernel[kern_y][kern_x] * c.getRed();
                        g += kernel[kern_y][kern_x] * c.getGreen();
                        b += kernel[kern_y][kern_x] * c.getBlue();
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
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
