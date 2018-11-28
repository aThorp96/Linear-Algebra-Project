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

    private Lens lens;
    private ArrayList<Ray> rays;

    public Model(int dimension) {
        setLens(lens);
        generateRays(0);
    }

    public void setLens(Lens lens) {
        this.lens = (lens != null) ? lens : null;
    }

    public generateRays(double angle) {

    }
    
}
