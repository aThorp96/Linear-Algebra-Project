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

    public static final double   origin                 = 200;
    public static final double   defaultRayAngle        = .5;

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
        Ray r1 = new Ray(10, defaultRayAngle);
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

        int[] intersect1;
        int[] intersect2;
        if (lens.aimedAtBy(r1) && r1.getEnd()[0] > lens.getVertex()) {
            intersect1 = findEntrance(lens, r1);
            r1.setDistance(intersect1[1]);
            r2 = lens.refract(r1, intersect1[0], intersect1[1]);
            rays.add(r2);

            intersect2 = findExit(lens, r2);
            r2.setDistance(intersect2[1]);
            r3 = lens.refract(r2, intersect2[0], intersect2[1]);
            rays.add(r3);
        }

        for(int i = 0; i < rays.size(); i++) {
            plotter.setRay(rays.get(i), i);
        }
    }

    public int[] findEntrance(Lens lens, Ray ray) {
        int start = (int) ray.getX();
        int end = (int) ray.getEnd()[0];
        int[] intersect;

        if (ray.getDirection() > 0) intersect = new int[]{200,(int) ray.getY()};
        else if (ray.getDirection() < 0) intersect = new int[]{200,-(int) ray.getY()};
        else intersect = new int[]{200, (int) ray.getY()};

        if (end > lens.getVertex()) {
            start = lens.getVertex();
            end = lens.getCorner(true);
        }

        while (start < end) {
            double lowerBound = lens.getY(start)[0];
            double upperBound = lens.getY(start + 1)[0];
            double rayY = Math.abs(ray.getY(start));
            //System.out.printf("Upper and Lower: %f - %f\n" +
            //                  "           rayY: %f\n\n", lowerBound, upperBound, rayY);
            if (rayY >= lowerBound && rayY <= upperBound) {
                intersect[0] = (int) start;
                intersect[1] = (int) (0.5 + ray.getY(start));
                plotter.plotPoint(start, ray.getY() - ray.getY(start + 1));
                break;
            }
            start++;
        }

        return intersect;
    }

    public int[] findExit(Lens lens, Ray ray) {
        int start = (int) ray.getX();
        int end = (int) ray.getEnd()[0];
        int[] intersect;

        if (ray.getDirection() > 0) intersect = new int[]{200,(int) ray.getY()};
        else if (ray.getDirection() < 0) intersect = new int[]{200,-(int) ray.getY()};
        else intersect = new int[]{200, (int) ray.getY()};

        if (end > lens.getVertex()) {
            start = lens.getVertex();
            end = lens.getCorner(false);
        }

        while (start < end) {
            double lowerBound = lens.getY(end - 1)[0];
            double upperBound = lens.getY(end)[0];
            double rayY = ray.getY() - Math.abs(ray.getY(start));
            //System.out.printf("Upper and Lower: %f - %f\n" +
            //                  "           rayY: %f\n\n", lowerBound, upperBound, rayY);
            if (rayY >= lowerBound && rayY <= upperBound) {
                intersect[0] = (int) start;
                intersect[1] = (int) (0.5 + ray.getY(end));
                plotter.plotPoint(start, ray.getY() - ray.getY(end + 1));
                break;
            }
            end--;
        }

        return intersect;
    }

    public Lens getLens() {
        return lens;
    }

    public Ray getRay() {
        return rays.get(0);
    }

    public void setFocalLength(double l) {
        lens.setFocalLength(l);
        generateRays(getRay());
        plotter.refresh();
    }

    public void setMaterial(Material m) {
        lens.setMaterial(m);

        generateRays(getRay());
        plotter.refresh();
    }

    public void setThickness(double d) {
        lens.setThickness(d);
        generateRays(getRay());
        plotter.refresh();
    }

    public void setAngle(double a) {
        a = Math.toRadians(a);
        Ray r1 = rays.get(0);

        if (a > 0)
        {
            r1.setDistance(100);
        }
        else if (a < 0)
        {
           r1.setDistance(-100);
        }
        else
        {
           r1.setDistance(200);
        }

        r1.setDirection(a);
        
        generateRays(r1);
        
        plotter.refresh();
    }

    public void setRayY(double y) {
        y = origin - y;
        Ray r1 = rays.get(0);
        r1.setY(y);

        generateRays(r1);

        plotter.refresh();
    }

}
