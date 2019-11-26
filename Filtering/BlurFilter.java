package Filtering;

public class BlurFilter extends AbstractFilter {
    public BlurFilter() {
        double n = 1.0 / 9.0;
        kernel = new double[][]{{n, n, n}, {n, n, n}, {n, n, n}};
    }
}
