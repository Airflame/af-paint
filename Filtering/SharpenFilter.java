package Filtering;

public class SharpenFilter extends AbstractFilter {
    public SharpenFilter() {
        kernel = new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
    }
}
