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
    private List<List<Point>> points;
    private Stack<List<Point>> removedPoints;
    private Color brushColor;
    private Color backgroundColor;

    PaintPanel() {
        radius = 5;
        points = new ArrayList<>(25);
        removedPoints = new Stack<>();
        brushColor = Color.BLACK;
        backgroundColor = Color.WHITE;
        setBackground(backgroundColor);
        MouseAdapter ma = new MouseAdapter() {
            private List<Point> currentPath;

            @Override
            public void mousePressed(MouseEvent e) {
                currentPath = new ArrayList<>(25);
                currentPath.add(e.getPoint());
                points.add(currentPath);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                Point dragPoint = e.getPoint();
                currentPath.add(dragPoint);
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentPath = null;
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        if (image != null) {
            g2d.drawImage(image, 0, 0, this);
        }
        for (List<Point> path : points) {
            Point from = null;
            for (Point p : path) {
                if (from != null) {
                    g2d.setColor(brushColor);
                    g2d.setStroke(new BasicStroke((float)(radius)));
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

    void clearPaint() {
        points.clear();
        repaint();
    }

    void clearImage() {
        image = null;
    }

    void setImage(BufferedImage image) {
        clearImage();
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
        dumpToImage();
        brushColor = JColorChooser.showDialog(null, "Choose brush color", brushColor);
    }

    void chooseBackgroundColor() {
        backgroundColor = JColorChooser.showDialog(null, "Choose brush color", backgroundColor);
        setBackground(backgroundColor);
        clearImage();
        clearPaint();
    }

    void setBrushRadius(double r) {
        dumpToImage();
        radius = r;
    }

    double getBrushRadius() {
        return radius;
    }

    void undo() {
        if (!points.isEmpty()) {
            removedPoints.add(points.get(points.size()-1));
            points.remove(points.size()-1);
            repaint();
        }
    }

    void redo() {
        if (!removedPoints.isEmpty()) {
            points.add(removedPoints.pop());
            repaint();
        }
    }

    void applyEffect(Effect effect) {
        if(preview == null)
            dumpToImage();
        setImage(effect.process(image));
        repaint();
    }

    void dumpToImage() {
        BufferedImage im = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        paint(im.getGraphics());
        setImage(im);
        clearPaint();
    }

    Dimension getImageSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
}