
import Jama.*;
import java.util.Arrays;
import java.awt.*;
import java.util.concurrent.TimeUnit;

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

        Lens testLens = new Lens(100d, 10d, Material.GLASS, 10d);
        plotter.setLens(testLens);

        Ray ray1 = new Ray(100);
        plotter.setRay(ray1, 0);

        plotter.refresh();


        //System.exit(0);
    }

}
