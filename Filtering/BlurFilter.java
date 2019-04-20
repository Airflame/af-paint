package Filtering;

public class BlurFilter extends Filter {
    public BlurFilter() {
        kernel = new float[][] {{1,1,1},{1,1,1},{1,1,1}};
    }
}
