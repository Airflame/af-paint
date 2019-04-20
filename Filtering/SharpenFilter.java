package Filtering;

public class SharpenFilter extends Filter {
    public SharpenFilter() {
        kernel = new float[][] {{0,-1,0},{-1,5,-1},{0,-1,0}};
    }
}
