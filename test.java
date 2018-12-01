
import Jama.*;
import java.util.Arrays;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class test {

    public static void main(String[] args ) {


        Plotter plotter = Plotter.getPlotter();
        plotter.setName("Random Plotter");
        Model model = new Model();

        Lens testLens = new Lens(100d, 10d, Material.GLASS, 80d);
        plotter.setLens(testLens);

        Ray ray1 = new Ray(12, 0.1);
        Ray ray2 = new Ray(200, 200, -0.25);
        ray2.setDistance(-10);
        plotter.setRay(ray1, 0);
        plotter.setRay(ray2, 1);
        plotter.refresh();
        int x1 = (int) ray1.getX();
        int y1 = (int) ray1.getY();
        int x2 = (int) ray1.getEnd()[0];
        int y2 = (int) ray1.getEnd()[1];
        //System.out.printf("%d, %d, %d, %d, Intersects: %b\n", x1, y1, x2, y2, testLens.getPath().intersects(x1, y1, x2, y2));
/*        for (int i = 0; i < 10; i++) {
            ray1.setDirection(ray1.getDirection() + 0.05);
            ray1.setDistance(100);
            int[] intersect = model.findEntrance(testLens, ray1);
            ray1.setDistance(intersect[1]);
           plotter.plotPoint(intersect[0], intersect[1]);
            testLens.setFocalLength(testLens.getFocalLength() + 2);
            plotter.refresh();
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch(Exception e) {}
        }
        for (int i = 0; i < 200; i++) {
            if(testLens.xIntersects(i)) {
               plotter.plotPoint(i, testLens.getY(i)[0]);
               System.out.printf("%d\t%.2f\n", i, testLens.getY(i)[0]);
                plotter.refresh();
            }
        }*/

        for(int i = (int) ray1.getX(); i < ray1.getEnd()[0]; i++) {
            int j = (int) ray1.getY(i);
        }

    }

}
