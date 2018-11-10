
import Jama.*;

public class test {

    public static void main(String[] args ) {
        Matrix m = Matrix.random(400, 2);
        int y = m.getColumnDimension();
        int x = m.getRowDimension();
        double[][] arr = m.getArray();

        Plotter plotter = Plotter.getPlotter();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                plotter.plot(399 * arr[i][0], 399 * arr[i][1]);
                System.out.printf("Index [%d,%d]: %f\n", i, j, 400 * arr[i][j]);
            }
        }


        plotter.refresh();
    }

}
