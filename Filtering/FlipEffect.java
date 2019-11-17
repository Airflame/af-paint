package Filtering;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FlipEffect implements Effect {
    private boolean vertical;

    public FlipEffect(boolean vertical) {
        this.vertical = vertical;
    }

    @Override
    public BufferedImage process(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        int height = image.getHeight();
        int width = image.getWidth();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color c;
                if (vertical) {
                    c = new Color(image.getRGB(i, height - 1 - j));
                } else {
                    c = new Color(image.getRGB(width - 1 - i, j));
                }
                result.setRGB(i, j, c.getRGB());
            }
        }
        return result;
    }
}
