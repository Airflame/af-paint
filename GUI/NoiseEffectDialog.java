package GUI;

import Filtering.NoiseEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NoiseEffectDialog extends JDialog {
    private PaintPanel paintPanel;
    private JSlider noiseSlider;

    NoiseEffectDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Generate noise", true);
        setLocationByPlatform(true);
        paintPanel = panel;
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                paintPanel.removePreview();
            }
        });

        noiseSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        noiseSlider.setMajorTickSpacing(20);
        noiseSlider.setMinorTickSpacing(5);
        noiseSlider.setPaintTicks(true);
        noiseSlider.setPaintLabels(true);
        noiseSlider.addChangeListener((event) -> refreshPreview());

        JPanel noisePanel = new JPanel();
        noisePanel.setLayout(new BorderLayout());

        JButton button = new JButton("OK");
        button.addActionListener((event) -> {
            paintPanel.applyEffect(new NoiseEffect(noiseSlider.getValue()));
            setVisible(false);
            paintPanel.removePreview();
            dispose();
        });

        noisePanel.add(new JLabel("Set strength:"), BorderLayout.NORTH);
        noisePanel.add(noiseSlider, BorderLayout.CENTER);
        noisePanel.add(button, BorderLayout.SOUTH);
        add(noisePanel);
        pack();
    }

    private void refreshPreview() {
        NoiseEffect n = new NoiseEffect(noiseSlider.getValue());
        paintPanel.setPreview(n.process(paintPanel.getImage()));
    }
}
