
import Jama.*;
import java.util.Arrays;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class test {

    public static void main(String[] args ) {


        Plotter plotter = Plotter.getPlotter();
        plotter.setName("Random Plotter");
        Model model = new Model(400);

        Lens testLens = new Lens(100d, 10d, Material.GLASS, 10d);
        plotter.setLens(testLens);

        Ray ray1 = new Ray(90, 0.5);
        Ray ray2 = new Ray(200, 200, -0.25);
        ray2.setDistance(-10);
        plotter.setRay(ray1, 0);
        plotter.setRay(ray2, 1);
        plotter.refresh();
        int x1 = (int) ray1.getX();
        int y1 = (int) ray1.getY();
        int x2 = (int) ray1.getEnd()[0];
        int y2 = (int) ray1.getEnd()[1];
        System.out.printf("%d, %d, %d, %d, Intersects: %b\n", x1, y1, x2, y2, testLens.getPath().intersects(x1, y1, x2, y2));
        int[] intersect = model.findIntersect(testLens, ray2);
        ray2.setDistance(intersect[1]);
        plotter.refresh();

    }

}
