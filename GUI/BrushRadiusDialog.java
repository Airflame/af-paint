package GUI;

import javax.swing.*;
import java.awt.*;

public class BrushRadiusDialog extends JDialog {
    private PaintPanel paintPanel;
    private JSpinner spinner;

    BrushRadiusDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Set brush radius", true);
        paintPanel = panel;
        setLocationByPlatform(true);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel brushPanel = new JPanel();
        SpinnerModel model = new SpinnerNumberModel(paintPanel.getBrushRadius(), 1, 50, 1);
        spinner = new JSpinner(model);
        JButton button = new JButton("OK");
        button.addActionListener((event) -> getRadius());
        brushPanel.add(new JLabel("Radius:"));
        brushPanel.add(spinner);
        brushPanel.add(button);
        add(brushPanel);
        pack();
    }

    private void getRadius() {
        paintPanel.setBrushRadius((double) spinner.getValue());
        setVisible(false);
    }
}
