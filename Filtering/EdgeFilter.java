package Filtering;

public class EdgeFilter extends AbstractFilter {
    public EdgeFilter() {
        kernel = new double[][] {{-1,-1,-1},{-1,8,-1},{-1,-1,-1}};
    }
}
