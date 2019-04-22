package GUI;

import Filtering.ColorsEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ColorsEffectDialog extends JDialog {
    private PaintPanel paintPanel;
    private JSlider redSlider;
    private JSlider greenSlider;
    private JSlider blueSlider;

    ColorsEffectDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Colors", true);
        paintPanel = panel;
        paintPanel.dumpToImage();
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                paintPanel.removePreview();
            }
        });

        redSlider = new JSlider(JSlider.HORIZONTAL, -256,256,0);
        redSlider.setMajorTickSpacing(128);
        redSlider.setMinorTickSpacing(16);
        redSlider.setPaintTicks(true);
        redSlider.setPaintLabels(true);
        redSlider.addChangeListener((event) -> refreshPreview());
        greenSlider = new JSlider(JSlider.HORIZONTAL, -256,256,0);
        greenSlider.setMajorTickSpacing(128);
        greenSlider.setMinorTickSpacing(16);
        greenSlider.setPaintTicks(true);
        greenSlider.setPaintLabels(true);
        greenSlider.addChangeListener((event) -> refreshPreview());
        blueSlider = new JSlider(JSlider.HORIZONTAL, -256,256,0);
        blueSlider.setMajorTickSpacing(128);
        blueSlider.setMinorTickSpacing(16);
        blueSlider.setPaintTicks(true);
        blueSlider.setPaintLabels(true);
        blueSlider.addChangeListener((event) -> refreshPreview());

        JPanel colorsPanel = new JPanel();
        colorsPanel.setLayout(new BorderLayout());
        JPanel slidersPanel = new JPanel();
        slidersPanel.setLayout(new BorderLayout());

        JPanel redPanel = new JPanel();
        redPanel.setLayout(new BorderLayout());
        redPanel.add(new JLabel("Red:"), BorderLayout.NORTH);
        redPanel.add(redSlider, BorderLayout.SOUTH);

        JPanel greenPanel = new JPanel();
        greenPanel.setLayout(new BorderLayout());
        greenPanel.add(new JLabel("Green:"), BorderLayout.NORTH);
        greenPanel.add(greenSlider, BorderLayout.SOUTH);

        JPanel bluePanel = new JPanel();
        bluePanel.setLayout(new BorderLayout());
        bluePanel.add(new JLabel("Blue:"), BorderLayout.NORTH);
        bluePanel.add(blueSlider, BorderLayout.SOUTH);

        slidersPanel.add(redPanel, BorderLayout.NORTH);
        slidersPanel.add(greenPanel, BorderLayout.CENTER);
        slidersPanel.add(bluePanel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        JButton button = new JButton("OK");
        button.addActionListener((event) -> {
            paintPanel.applyEffect(new ColorsEffect(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue()));
            setVisible(false);
            paintPanel.removePreview();
        });
        buttonPanel.add(button);

        colorsPanel.add(slidersPanel, BorderLayout.NORTH);
        colorsPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(colorsPanel);
        pack();
    }

    private void refreshPreview() {
        ColorsEffect cl = new ColorsEffect(redSlider.getValue(), greenSlider.getValue(), blueSlider.getValue());
        paintPanel.setPreview(cl.process(paintPanel.getImage()));
    }
}
