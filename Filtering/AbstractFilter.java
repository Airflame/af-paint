package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

abstract public class AbstractFilter implements Effect {
    protected float kernel[][];

    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics gr = result.getGraphics();
        gr.drawImage(image, 0, 0, null);
        gr.dispose();
        float sum = 0;
        for (float[] row : kernel) {
            for (float item : row) {
                sum += item;
            }
        }
        for (int x = 0; x < kernel.length; x++)
        {
            for (int y = 0; y < kernel.length; y++)
            {
                kernel[x][y] /= sum;
            }
        }

        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = kernel.length/2; i < width-kernel.length/2; i++)
        {
            for (int j = kernel.length/2; j < height-kernel.length/2; j++)
            {
                int r = 0;
                int g = 0;
                int b = 0;
                for (int x = 0; x < kernel.length; x++)
                {
                    for (int y = 0; y < kernel.length; y++)
                    {
                        Color c = new Color(image.getRGB(i+x-kernel.length/2, j+y-kernel.length/2));
                        r += kernel[x][y] * c.getRed();
                        g += kernel[x][y] * c.getGreen();
                        b += kernel[x][y] * c.getBlue();
                    }
                }
                if (r > 255)
                    r = 255;
                if (g > 255)
                    g = 255;
                if (b > 255)
                    b = 255;
                while (r < 0)
                    r += 128;
                while (g < 0)
                    g += 128;
                while (b < 0)
                    b += 128;
                Color c = new Color(r, g, b);
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
