package GUI;

import Filtering.Effect;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public class PaintPanel extends JPanel {
    private double radius;
    private BufferedImage image;
    private BufferedImage preview;
    private Stack<BufferedImage> previousImages;
    private Stack<BufferedImage> removedImages;
    private List<Point> path;
    private Color brushColor;
    private Color backgroundColor;

    PaintPanel() {
        radius = 5;
        previousImages = new Stack<>();
        removedImages = new Stack<>();
        brushColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        setBackground(backgroundColor);
        MouseAdapter ma = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {
                path = new ArrayList<>(25);
                path.add(e.getPoint());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                path.add(dragPoint);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                previousImages.push(image);
                removedImages.clear();
                BufferedImage im = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                paint(im.getGraphics());
                setImage(im);
                path = null;
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
        setBackground(Color.WHITE);
        setSize(new Dimension(500, 500));
        BufferedImage im = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        paint(im.getGraphics());
        setImage(im);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (image != null) {
            g2d.drawImage(image, 0, 0, this);
        }
        if (path != null) {
            Point from = null;
            for (Point p : path) {
                if (from != null) {
                    g2d.setColor(brushColor);
                    g2d.setStroke(new BasicStroke((float) (radius)));
                    g2d.draw(new Line2D.Double(from.x, from.y, p.x, p.y));
                }
                from = p;
            }
        }
        if (preview != null) {
            g2d.drawImage(preview, 0, 0, this);
        }
        g2d.dispose();
    }

    void clearImage() {
        image = null;
    }

    void clearImageHistory() {
        previousImages.clear();
        removedImages.clear();
    }

    void setImage(BufferedImage image) {
        this.image = image;
    }

    BufferedImage getImage() {
        return image;
    }

    void removePreview() {
        preview = null;
        repaint();
    }

    void setPreview(BufferedImage image) {
        preview = image;
        repaint();
    }

    void chooseBrushColor() {
        brushColor = JColorChooser.showDialog(null, "Choose brush color", brushColor);
    }

    void chooseBackgroundColor() {
        backgroundColor = JColorChooser.showDialog(null, "Choose brush color", backgroundColor);
        setBackground(backgroundColor);
        clearImage();
    }

    void setBrushRadius(double r) {
        radius = r;
    }

    double getBrushRadius() {
        return radius;
    }

    void undo() {
        if (!previousImages.isEmpty()) {
            removedImages.push(image);
            setImage(previousImages.pop());
        }
        repaint();
    }

    void redo() {
        if (!removedImages.isEmpty()) {
            previousImages.push(image);
            setImage(removedImages.pop());
        }
        repaint();
    }

    void applyEffect(Effect effect) {
        previousImages.push(image);
        removedImages.clear();
        setImage(effect.process(image));
        repaint();
    }
}