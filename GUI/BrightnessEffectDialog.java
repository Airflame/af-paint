package GUI;

import Filtering.ColorsEffect;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrightnessEffectDialog extends JDialog {
    private PaintPanel paintPanel;
    private JSlider brightnessSlider;

    BrightnessEffectDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Brightness", true);
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

        brightnessSlider = new JSlider(JSlider.HORIZONTAL, -128,128,0);
        brightnessSlider.setMajorTickSpacing(64);
        brightnessSlider.setMinorTickSpacing(8);
        brightnessSlider.setPaintTicks(true);
        brightnessSlider.setPaintLabels(true);
        brightnessSlider.addChangeListener((event) -> refreshPreview());

        JPanel brightnessPanel = new JPanel();
        brightnessPanel.setLayout(new BorderLayout());

        JButton button = new JButton("OK");
        button.addActionListener((event) -> {
            paintPanel.applyEffect(new ColorsEffect(brightnessSlider.getValue(),
                    brightnessSlider.getValue(), brightnessSlider.getValue()));
            setVisible(false);
            paintPanel.removePreview();
            dispose();
        });

        brightnessPanel.add(new JLabel("Brightness:"), BorderLayout.NORTH);
        brightnessPanel.add(brightnessSlider, BorderLayout.CENTER);
        brightnessPanel.add(button, BorderLayout.SOUTH);
        add(brightnessPanel);
        pack();
    }

    private void refreshPreview() {
        ColorsEffect br = new ColorsEffect(brightnessSlider.getValue(),
                brightnessSlider.getValue(), brightnessSlider.getValue());
        paintPanel.setPreview(br.process(paintPanel.getImage()));
    }
}
