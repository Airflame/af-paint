package GUI;

import Filtering.*;

import javax.swing.*;
import javax.swing.filechooser.*;
import javax.swing.filechooser.FileFilter;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class PaintFrame extends JFrame {
    private PaintPanel panel;

    public PaintFrame() {
        setTitle("AF-Paint");
        setLocationByPlatform(true);
        setResizable(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel = new PaintPanel();
        panel.setPreferredSize(new Dimension(500, 500));
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
            NewFileDialog nfd = new NewFileDialog(this, panel);
            nfd.setVisible(true);
        });
        newItem.setAccelerator(KeyStroke.getKeyStroke('N',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        openItem.addActionListener((event) -> open());
        openItem.setAccelerator(KeyStroke.getKeyStroke('O',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        saveItem.addActionListener((event) -> save());
        saveItem.setAccelerator(KeyStroke.getKeyStroke('S',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);

        JMenuItem undoItem = new JMenuItem("Undo");
        undoItem.addActionListener((event) -> panel.undo());
        undoItem.setAccelerator(KeyStroke.getKeyStroke('Z',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        JMenuItem redoItem = new JMenuItem("Redo");
        redoItem.addActionListener((event) -> panel.redo());
        redoItem.setAccelerator(KeyStroke.getKeyStroke('Y',
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        JMenuItem flipItem = new JMenuItem("Flip image");
        flipItem.addActionListener((event) -> {
            FlipEffectDialog fed = new FlipEffectDialog(this, panel);
            fed.setVisible(true);
        });
        JMenuItem rotateItem = new JMenuItem("Rotate image");
        rotateItem.addActionListener((event) -> {
            panel.setPreferredSize(new Dimension(panel.getPreferredSize().height, panel.getPreferredSize().width));
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
        brushColorItem.addActionListener((event) -> chooseBrushColor());
        JMenuItem backgroundColorItem = new JMenuItem("Change background color");
        backgroundColorItem.addActionListener((event) -> chooseBackgroundColor());
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

        pack();
    }

    private void save() {
        BufferedImage im = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);
        panel.paint(im.getGraphics());

        FileFilter pngFilter = new FileFilter() {
            public String getDescription() {
                return "PNG Images (*.png)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".png");
                }
            }
        };
        FileFilter jpgFilter = new FileFilter() {
            public String getDescription() {
                return "JPEG Images (*.jpg)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".jpg");
                }
            }
        };
        FileFilter bmpFilter = new FileFilter() {
            public String getDescription() {
                return "BMP Images (*.bmp)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".bmp");
                }
            }
        };

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(pngFilter);
        fileChooser.addChoosableFileFilter(jpgFilter);
        fileChooser.addChoosableFileFilter(bmpFilter);
        fileChooser.setAcceptAllFileFilterUsed(false);

        int returnValue = fileChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            FileFilter selectedFilter = fileChooser.getFileFilter();
            String description = selectedFilter.getDescription();
            String formatName = description.substring(0, description.indexOf(" "));
            String extension = description.substring(description.length() - 5, description.length() - 1);
            String absolutePath = selectedFile.getAbsolutePath();
            if (!absolutePath.endsWith(extension))
                absolutePath += extension;
            selectedFile = new File(absolutePath);
            try {
                boolean val = ImageIO.write(im, formatName, selectedFile);
                System.out.println(val);
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
                panel.setImage(buff);
                panel.setSize(new Dimension(buff.getWidth(), buff.getHeight()));
                panel.setPreferredSize(new Dimension(buff.getWidth(), buff.getHeight()));
                panel.repaint();
                pack();
            } catch (IOException e) {
                System.err.println("An IOException was caught :" + e.getMessage());
            }
        }
    }

    private void chooseBrushColor() {
        panel.setBrushColor(JColorChooser.showDialog(null, "Choose brush color",
                panel.getBrushColor()));
    }

    private void chooseBackgroundColor() {
        panel.setBackgroundColor(JColorChooser.showDialog(null, "Choose brush color",
                panel.getBackgroundColor()));
    }
}