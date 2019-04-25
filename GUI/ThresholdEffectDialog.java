package GUI;

import Filtering.ThresholdEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ThresholdEffectDialog extends JDialog {
    private PaintPanel paintPanel;
    private Color brightColor;
    private Color darkColor;
    private JSlider thresholdSlider;

    ThresholdEffectDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Thresholding", true);
        paintPanel = panel;
        paintPanel.dumpToImage();
        brightColor = Color.WHITE;
        darkColor = Color.BLACK;
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                paintPanel.removePreview();
            }
        });

        thresholdSlider = new JSlider(JSlider.HORIZONTAL, 0,256,128);
        thresholdSlider.setMajorTickSpacing(64);
        thresholdSlider.setMinorTickSpacing(8);
        thresholdSlider.setPaintTicks(true);
        thresholdSlider.setPaintLabels(true);
        thresholdSlider.addChangeListener((event) -> refreshPreview());

        JPanel thresholdPanel = new JPanel();
        thresholdPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,3));

        JButton brightColorButton = new JButton("Bright color");
        brightColorButton.setBackground(brightColor);
        brightColorButton.addActionListener((event) -> {
            brightColor = JColorChooser.showDialog(null, "Choose bright color", brightColor);
            brightColorButton.setBackground(brightColor);
            refreshPreview();
        });
        JButton darkColorButton = new JButton("Dark color");
        darkColorButton.setBackground(darkColor);
        darkColorButton.addActionListener((event) -> {
            darkColor = JColorChooser.showDialog(null,"Choose dark color", darkColor);
            darkColorButton.setBackground(darkColor);
            refreshPreview();
        });
        JButton button = new JButton("OK");
        button.addActionListener((event) -> {
            paintPanel.applyEffect(new ThresholdEffect(thresholdSlider.getValue(), brightColor, darkColor));
            setVisible(false);
            paintPanel.removePreview();
            dispose();
        });

        buttonPanel.add(brightColorButton);
        buttonPanel.add(darkColorButton);
        buttonPanel.add(button);

        thresholdPanel.add(new JLabel("Set threshold:"), BorderLayout.NORTH);
        thresholdPanel.add(thresholdSlider, BorderLayout.CENTER);
        thresholdPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(thresholdPanel);
        pack();

        refreshPreview();
    }

    private void refreshPreview() {
        ThresholdEffect th = new ThresholdEffect(thresholdSlider.getValue(), brightColor, darkColor);
        paintPanel.setPreview(th.process(paintPanel.getImage()));
    }
}
