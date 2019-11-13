package Filtering;

public class BlurFilter extends AbstractFilter {
    public BlurFilter() {
        kernel = new double[][] {{0.11,0.11,0.11},{0.11,0.11,0.11},{0.11,0.11,0.11}};
    }
}
