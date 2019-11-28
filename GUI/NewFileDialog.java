package GUI;

import javax.swing.*;
import java.awt.*;

public class NewFileDialog extends JDialog {
    private PaintPanel paintPanel;
    private JSpinner spinnerX;
    private JSpinner spinnerY;

    NewFileDialog(JFrame owner, PaintPanel panel) {
        super(owner, "New file", true);
        setLocationByPlatform(true);
        paintPanel = panel;

        JPanel newFilePanel = new JPanel();
        newFilePanel.setLayout(new BorderLayout());
        JPanel sizePanel = new JPanel();
        sizePanel.setLayout(new GridLayout(2,2));
        SpinnerModel modelX = new SpinnerNumberModel(paintPanel.getPreferredSize().width,
                1, 1000, 1);
        SpinnerModel modelY = new SpinnerNumberModel(paintPanel.getPreferredSize().height,
                1, 1000, 1);
        spinnerX = new JSpinner(modelX);
        spinnerY = new JSpinner(modelY);
        JButton button = new JButton("OK");
        button.addActionListener((event) -> createNewFile());
        sizePanel.add(new JLabel("Width:"));
        sizePanel.add(spinnerX);
        sizePanel.add(new JLabel("Height:"));
        sizePanel.add(spinnerY);
        newFilePanel.add(sizePanel, BorderLayout.NORTH);
        newFilePanel.add(button, BorderLayout.SOUTH);
        add(newFilePanel);
        pack();
    }

    private void createNewFile() {
        Dimension size = new Dimension(new Dimension((int) spinnerX.getValue(), (int) spinnerY.getValue()));
        paintPanel.clearImage();
        paintPanel.clearImageHistory();
        paintPanel.setPreferredSize(size);
        paintPanel.setSize(size);
        paintPanel.repaint();
        getOwner().pack();
        setVisible(false);
        dispose();
    }
}
