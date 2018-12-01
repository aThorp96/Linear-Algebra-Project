/**
*   Class model handles relationship logic between the
*   objects in the 2D space. The model generates rays, calculates
*   intersections, etc.
*
*   A model is comprised of the following componants:
*   - a single lens.
*       This lens is fixed in the center of the model.
*       A default radius and focal length for the lens is
*       provided.
*   - A number of rays (maximum of 3).
*       The first Ray has a fixed origin, and a dynamic angle.
*       When the angle is changed using updateAngle() method,
*       the resulting rays are calculated and created on the spot.
*
*   @author Andrew Thorp
*   @version 2018.11.28
**/
import java.util.ArrayList;
import java.awt.geom.Line2D;
import java.awt.geom.Area;

public class Model {

    public static final double   defaultRayAngle        = 0;
    public static final double   defaultLensRadius      = 150;
    public static final double   defaultLensThickness   = 10;
    public static final double   defaultLensFocalLength = 40;
    public static final Material defaultLensMaterial    = Material.GLASS;

    private Plotter plotter;

    private Lens lens;
    private ArrayList<Ray> rays;

    public Model() {
        plotter = Plotter.getPlotter();
        Lens defaultLens = new Lens(defaultLensRadius, defaultLensThickness,
                                    defaultLensMaterial, defaultLensFocalLength);
        setLens(defaultLens);
        Ray r1 = new Ray(defaultRayAngle);
        generateRays(r1);
    }

    public void setLens(Lens lens) {
        this.lens = (lens != null) ? lens : null;
        plotter.setLens(lens);
    }

    public void generateRays(Ray r1) {
        rays = new ArrayList<Ray>();
        rays.add(r1);

        Ray r2;
        Ray r3;

        int x1 = (int) (0.5 +  r1.getX());
        int y1 = (int) (0.5 +  r1.getY());
        int x2 = (int) (0.5 +  r1.getEnd()[0]);
        int y2 = (int) (0.5 +  r1.getEnd()[1]);

        int[] intersect;
        if (lens.getPath().intersects(x1, y1, x2, y2)) {
            intersect = findEntrance(lens, r1);
            r1.setDistance(intersect[1]);
            r2 = lens.refract(r1, intersect[0], intersect[1]);
        }

        for(int i = 0; i < rays.size(); i++) {
            plotter.setRay(rays.get(i), i);
        }
    }

    public int[] findEntrance(Lens lens, Ray ray) {
        int start = (int) ray.getX();
        int end = (int) ray.getEnd()[0];

        // call private recursize method
        int[] intersect = new int[]{200,-200};
        //intersect = findEntrance(lens, ray, start, end);
        for (int i = 0; i < end - start; i++) {
            int j = (int) ray.getY(i);

            int x = i + (int) ray.getX();
            int y = (int) j;

            //System.out.println("\ntesting [" + x + ", " + y + "]");

            if(lens.xIntersects(x)) {

                double[] lensDouobleYs = lens.getY(x);
                int[] lensYs = new int[]{(int) lensDouobleYs[0], (int) lensDouobleYs[1]};
                //System.out.printf("Values of lens:\n\tY: %d\n\tUpper: %d\n\tLower: %d\n",y,  lensYs[0], lensYs[1]);

                if (ray.getDirection() >= 0 && lensYs[0] - y <= 1 && lensYs[0] - y >= -1) {
                    intersect = new int[]{x, lensYs[0]};
                } else if(lensYs[1] - y <= 1 && lensYs[1] - y >= -1){
                    intersect = new int[]{x, lensYs[1]};
                }
            }
        }
        //System.out.printf("Lens and ray intersect at [%d, %d]\n", intersect[0], intersect[1]);

        return intersect;
    }

    private int[] findEntrance(Lens lens, Ray ray, int startX, int endX) throws IndexOutOfBoundsException {
        // base case
        if (endX - startX <= 1) return new int[]{endX, (int) ray.getY(endX)};

        return null;
    }

    public Lens getLens() {
        return lens;
    }

    public Ray getRay() {
        return rays.get(0);
    }

    public void setFocalLength(double l) {
        lens.setFocalLength(l);
        plotter.refresh();
    }

    public void setMaterial(Material m) {
        lens.setMaterial(m);
        plotter.refresh();
    }

    public void setThickness(double d) {
        lens.setThickness(d);
        plotter.refresh();
    }

    public void setAngle(double a) {
        rays.get(0).setDirection(a);
        Ray r1 = rays.get(0);

        generateRays(r1);

        plotter.refresh();
    }

    public void setRayY(double y) {
        Ray r1 = rays.get(0);
        r1.setY(y);

        generateRays(r1);

        plotter.refresh();
    }

}
