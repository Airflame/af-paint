package GUI;

import Filtering.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;

public class PaintFrame extends JFrame {
    private PaintPanel panel;
    private Dimension size;
    private final int widthInterval = 16;
    private final int heightInterval = 61;

    public PaintFrame() {
        setTitle("AF-Paint");
        setLocationByPlatform(true);
        size = new Dimension(500 + widthInterval, 500 + heightInterval);
        panel = new PaintPanel();
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu editMenu = new JMenu("Edit");
        JMenu optionsMenu = new JMenu("Options");
        JMenu effectsMenu = new JMenu("Effects");
        JMenu filterMenu = new JMenu("Filters");

        JMenuItem newItem = new JMenuItem("New");
        JMenuItem openItem = new JMenuItem("Open");
        JMenuItem saveItem = new JMenuItem("Save");
        newItem.addActionListener((event) -> {
            panel.clearImage();
            panel.clearImageHistory();
        });
        newItem.setAccelerator(KeyStroke.getKeyStroke('N',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        openItem.addActionListener((event) -> open());
        openItem.setAccelerator(KeyStroke.getKeyStroke('O',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        saveItem.addActionListener((event) -> save());
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener((event) -> panel.undo());
        undoItem.setAccelerator(KeyStroke.getKeyStroke('Z',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener((event) -> panel.redo());
        redoItem.setAccelerator(KeyStroke.getKeyStroke('Y',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        JMenuItem flipItem = new JMenuItem("Flip image");
        flipItem.addActionListener((event) -> {
            FlipEffectDialog fed = new FlipEffectDialog(this, panel);
            fed.setVisible(true);
        });
        JMenuItem rotateItem = new JMenuItem("Rotate image");
        rotateItem.addActionListener((event) -> {
            size = new Dimension(
                    (int) size.getHeight() - heightInterval + widthInterval,
                    (int) size.getWidth() - widthInterval + heightInterval);
            panel.applyEffect(new RotateEffect());
            pack();
        });
        editMenu.add(undoItem);
        editMenu.add(redoItem);
        editMenu.addSeparator();
        editMenu.add(flipItem);
        editMenu.add(rotateItem);

        JMenuItem brushSizeItem = new JMenuItem("Set brush radius");
        brushSizeItem.addActionListener((event) -> {
            BrushRadiusDialog brd = new BrushRadiusDialog(this, panel);
            brd.setVisible(true);
        });
        JMenuItem brushColorItem = new JMenuItem("Change brush color");
        brushColorItem.addActionListener((event) -> panel.chooseBrushColor());
        JMenuItem backgroundColorItem = new JMenuItem("Change background color");
        backgroundColorItem.addActionListener((event) -> panel.chooseBackgroundColor());
        optionsMenu.add(brushSizeItem);
        optionsMenu.add(brushColorItem);
        optionsMenu.add(backgroundColorItem);

        JMenuItem greyscaleItem = new JMenuItem("Black and white");
        greyscaleItem.addActionListener((event) -> panel.applyEffect(new GreyscaleEffect()));
        JMenuItem invertItem = new JMenuItem("Invert");
        invertItem.addActionListener((event) -> panel.applyEffect(new InvertEffect()));
        JMenuItem brightnessItem = new JMenuItem("Brightness");
        brightnessItem.addActionListener((event) -> {
            BrightnessEffectDialog brd = new BrightnessEffectDialog(this, panel);
            brd.setVisible(true);
        });
        JMenuItem colorsItem = new JMenuItem("Colors");
        colorsItem.addActionListener((event) -> {
            ColorsEffectDialog cld = new ColorsEffectDialog(this, panel);
            cld.setVisible(true);
        });
        JMenuItem thresholdItem = new JMenuItem("Thresholding");
        thresholdItem.addActionListener((event) -> {
            ThresholdEffectDialog trd = new ThresholdEffectDialog(this, panel);
            trd.setVisible(true);
        });
        JMenuItem noiseItem = new JMenuItem("Generate noise");
        noiseItem.addActionListener((event) -> {
                NoiseEffectDialog nd = new NoiseEffectDialog(this, panel);
                nd.setVisible(true);
        });
        effectsMenu.add(greyscaleItem);
        effectsMenu.add(invertItem);
        effectsMenu.addSeparator();
        effectsMenu.add(brightnessItem);
        effectsMenu.add(colorsItem);
        effectsMenu.add(thresholdItem);
        effectsMenu.addSeparator();
        effectsMenu.add(noiseItem);

        JMenuItem blurItem = new JMenuItem("Blur");
        blurItem.addActionListener((event) -> panel.applyEffect(new BlurFilter()));
        JMenuItem sharpenItem = new JMenuItem("Sharpen");
        sharpenItem.addActionListener((event) -> panel.applyEffect(new SharpenFilter()));
        JMenuItem edgeItem = new JMenuItem("Edge detection");
        edgeItem.addActionListener((event) -> {
            panel.applyEffect(new GreyscaleEffect());
            panel.applyEffect(new EdgeFilter());
        });
        JMenuItem customItem = new JMenuItem("Custom");
        customItem.addActionListener((event) -> {
            CustomFilterDialog cfd = new CustomFilterDialog(this, panel);
            cfd.setVisible(true);
        });
        filterMenu.add(blurItem);
        filterMenu.add(sharpenItem);
        filterMenu.add(edgeItem);
        filterMenu.addSeparator();
        filterMenu.add(customItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(optionsMenu);
        menuBar.add(effectsMenu);
        menuBar.add(filterMenu);
        add(panel);
        setJMenuBar(menuBar);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                int pw = getWidth() - widthInterval;
                int ph = getHeight() - heightInterval;
                if (panel.getImage() != null) {
                    panel.setImage(
                            panel.getImage().getSubimage(0, 0, pw, ph)
                    );
                }
                panel.setSize(pw, ph);
                size = new Dimension(pw + widthInterval, ph + heightInterval);
            }
        });
        pack();
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    private void save() {
        BufferedImage im = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_ARGB);
        JFileChooser fileChooser = new JFileChooser();
        panel.paint(im.getGraphics());

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ImageIO.write(im, "png", selectedFile);
            } catch (IOException e) {
                System.err.println("An IOException was caught :" + e.getMessage());
            }
        }
    }

    private void open() {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter imageFilter = new FileNameExtensionFilter(
                "Image files", ImageIO.getReaderFileSuffixes());
        fileChooser.addChoosableFileFilter(imageFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage buff = ImageIO.read(selectedFile);
                size = new Dimension(buff.getWidth(), buff.getHeight());
                panel.setImage(buff);
                pack();
            } catch (IOException e) {
                System.err.println("An IOException was caught :" + e.getMessage());
            }
        }
    }
}