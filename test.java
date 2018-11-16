
import Jama.*;
import java.util.Arrays;
import java.awt.Color;

public class test {

    public static void main(String[] args ) {
        int y = 2;
        int x = 40000;
        double[][] arr = new double[40000][2];

        int n = 0;

        for (int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                double[] tuple = {i * 2, j * 2};
                arr[n] = tuple;
                n++;
            }
        }


        Plotter plotter = Plotter.getPlotter();
        plotter.setName("Random Plotter");

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                //plotter.plot(arr[i][0], arr[i][1], Color.RED);
                System.out.printf("Index [%d,%d]: %f\n", i, j, arr[i][j]);
            }
        }

        plotter.refresh();

    }

}
