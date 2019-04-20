package Filtering;

import java.awt.image.BufferedImage;

public interface Effect {
    BufferedImage process(BufferedImage image);
}
