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

public class Model {

    public static final double   defaultRayAngle        = 0;
    public static final double   defaultLensRadius      = 150;
    public static final double   defaultLensThickness   = 10;
    public static final double   defaultLensFocalLength = 40;
    public static final Material defaultLensMaterial    = Material.GLASS;

    private Lens lens;
    private ArrayList<Ray> rays;

    public Model(int dimension) {
        Lens defaultLens = new Lens(defaultLensRadius, defaultLensThickness,
                                    defaultLensMaterial, defaultLensFocalLength);
        setLens(defaultLens);
        //generateRays(0);
    }

    public void setLens(Lens lens) {
        this.lens = (lens != null) ? lens : null;
    }

    public void generateRays(double angle) {

        Ray r1 = new Ray(10d);
        Ray r2;
        Ray r3;

        int x1 = (int) (0.5 +  r1.getX());
        int y1 = (int) (0.5 +  r1.getY());
        int x2 = (int) (0.5 +  r1.getEnd()[0]);
        int y2 = (int) (0.5 +  r1.getEnd()[1]);

        int[] intersect;
        if (lens.getPath().intersects(x1, y1, x2, y2)){
            intersect = findIntersect(lens, r1);
            r2 = new ray(intersect[0], intersect[1], 0);
            Matrix m = lens.refract(r2);
        }
        
    }

    public int[] findIntersect(Lens lens, Ray ray) {
        int start = (int) ray.getX();
        int end = (int) ray.getEnd()[0];

        // call private recursize method
        int[] intersect;
        intersect = findIntersect(lens, ray, start, end);
        System.out.printf("Lens and ray intersect at [%d, %d]\n", intersect[0], intersect[1]);
        return intersect;
    }

    private int[] findIntersect(Lens lens, Ray ray, int startX, int endX) throws IndexOutOfBoundsException {
        // base case
        if (startX >= endX) return new int[]{startX, (int) ray.getY(startX)};

        int origX = (int) ray.getX();

        int x1 = (int) (0.5 +  startX);
        int y1 = (int) (0.5 +  ray.getY(x1 - origX));
        int x2 = (int) (0.5 +  endX);
        int y2 = (int) (0.5 +  ray.getY(x2 - origX));

        int midX = (int) (0.5 +  (x2 + x1) / 2);
        int midY = (int) (0.5 +  y1 - ((y1 - y2) / 2));

        // check lower
        if (lens.getPath().intersects(x1, y1, midX, midY)) { 
            return findIntersect(lens, ray, x1, midX);
        } else if (lens.getPath().intersects(midX , midY , x2, y2)) {
            return findIntersect(lens, ray, midX, x2);
        }

        throw new IndexOutOfBoundsException("Ray MUST intersect Lens for recrusion: x1: " + startX + " y1: " + y1 + ", x2: " + endX + " y2: " + y2);
    }
    
}
