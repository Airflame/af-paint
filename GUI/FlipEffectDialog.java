package GUI;

import Filtering.FlipEffect;

import javax.swing.*;
import java.awt.*;

public class FlipEffectDialog extends JDialog {
    private PaintPanel paintPanel;
    private boolean vertical;

    FlipEffectDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Flip image", true);
        setLocationByPlatform(true);
        paintPanel = panel;
        vertical = false;
        setLocationByPlatform(true);
        setResizable(false);

        JPanel flipPanel = new JPanel();

        flipPanel.setLayout(new BorderLayout());
        String[] options = {"Horizontal", "Vertical"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSelectedIndex(0);
        comboBox.addActionListener((event) -> {
            vertical = comboBox.getSelectedIndex() != 0;
        });
        JButton button = new JButton("OK");
        button.addActionListener((event) -> {
            paintPanel.applyEffect(new FlipEffect(vertical));
            setVisible(false);
            dispose();
        });

        flipPanel.add(comboBox, BorderLayout.NORTH);
        flipPanel.add(button, BorderLayout.SOUTH);
        add(flipPanel);
        pack();
    }
}
