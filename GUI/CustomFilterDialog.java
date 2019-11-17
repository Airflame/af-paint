package GUI;

import Filtering.CustomFilter;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class CustomFilterDialog extends JDialog {
    private PaintPanel paintPanel;
    private int size;
    private JSpinner sizeSpinner;
    private ArrayList<JSpinner> spinners;
    private double[][] kernel;

    CustomFilterDialog(JFrame owner, PaintPanel panel) {
        super(owner, "Set custom filter", true);
        setLocationByPlatform(true);
        paintPanel = panel;
        setLocationByPlatform(true);
        setResizable(false);

        JPanel sizePanel = new JPanel();
        panel.setLayout(new BorderLayout());

        SpinnerModel model = new SpinnerNumberModel(3, 3, 9, 2);
        sizeSpinner = new JSpinner(model);
        JButton sizeButton = new JButton("OK");
        sizeButton.addActionListener((event) -> showKernelPanel());
        sizePanel.add(new JLabel("Size:"));
        sizePanel.add(sizeSpinner);
        sizePanel.add(sizeButton);

        add(sizePanel);
        pack();
    }

    private void showKernelPanel() {
        size = (int) sizeSpinner.getValue();
        getContentPane().removeAll();
        JPanel kernelPanel = new JPanel();
        kernelPanel.setLayout(new GridLayout(size + 1, size));

        ArrayList<SpinnerModel> models = new ArrayList<>();
        spinners = new ArrayList<>();
        for (int i = 0; i < size * size; i++) {
            SpinnerModel model = new SpinnerNumberModel(0, -10, 10, 1);
            JSpinner spinner = new JSpinner(model);
            models.add(model);
            spinners.add(spinner);
            kernelPanel.add(new JSpinner(model));
        }
        JButton kernelButton = new JButton("OK");
        kernelButton.addActionListener((event) -> createKernel());
        kernelPanel.add(kernelButton);
        add(kernelPanel);
        pack();
    }

    private void createKernel() {
        kernel = new double[size][size];
        int i = 0;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                kernel[y][x] = (float) ((int) spinners.get(i).getValue());
                i++;
            }
        }
        setVisible(false);
        paintPanel.applyEffect(new CustomFilter(kernel));
        dispose();
    }
}
