package Filtering;

public class BlurFilter extends AbstractFilter {
    public BlurFilter() {
        kernel = new float[][] {{1,1,1},{1,1,1},{1,1,1}};
    }
}
