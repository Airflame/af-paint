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
    private Dimension size;

    public PaintFrame() {
        setTitle("AF-Paint");
        setLocationByPlatform(true);
        size = new Dimension(500,500);
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
            panel.clearPaint();
            panel.clearImage();
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
        editMenu.add(undoItem);
        editMenu.add(redoItem);

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
        effectsMenu.add(greyscaleItem);
        effectsMenu.add(brightnessItem);
        effectsMenu.add(colorsItem);
        effectsMenu.add(thresholdItem);

        JMenuItem blurItem = new JMenuItem("Blur");
        blurItem.addActionListener((event) -> panel.applyEffect(new BlurFilter()));
        JMenuItem sharpenItem = new JMenuItem("Sharpen");
        sharpenItem.addActionListener((event) -> panel.applyEffect(new SharpenFilter()));
        JMenuItem customItem = new JMenuItem("Custom");
        customItem.addActionListener((event) -> {
            CustomFilterDialog cfd = new CustomFilterDialog(this, panel);
            cfd.setVisible(true);
        });
        filterMenu.add(blurItem);
        filterMenu.add(sharpenItem);
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
                panel.clearPaint();
                pack();
            } catch (IOException e) {
                System.err.println("An IOException was caught :" + e.getMessage());
            }
        }
    }
}