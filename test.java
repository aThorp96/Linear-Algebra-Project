
import Jama.*;

public class test {

    public static void main(String[] args ) {
        Matrix m = Matrix.random(400, 2);
        int y = m.getColumnDimension();
        int x = m.getRowDimension();
        double[][] arr = m.getArray();

        Plotter plotter = Plotter.getPlotter();

        for (int i = 0; i < x; i++)
                plotter.plot(arr[i][0], arr[i][1]);


        plotter.refresh();
    }

}
