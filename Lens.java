import Jama.Matrix; 

public class Lens
{
    // Parabola values
    private double focalLength;
    private double vertex;

    // Lens values
    private double origin;
    private double thickness;
    private double radius;
    private Material material;

    public Lens(double origin, double radius, double thickness, Material material, double focalLength, double vertex) {
        setOrigin(origin);
        setRadius(radius);
        setThickness(radius);
        setMaterial(material);
        setFocalLength(focalLength);
        setVertex(vertex);
    }

    public Lens(double thickness, Material material, double focalLength, double vertex) {
        setOrigin(origin);
        setRadius(radius);
        setThickness(radius);
        setMaterial(material);
        setFocalLength(focalLength);
        setVertex(vertex);
    }

    public double getOrigin() {
        return origin;
    }

    public double getFocalLength()
    {
        return focalLength;
    }

    public double getThickness()
    {
        return thickness;
    }

    public Material getMaterial()
    {
        return material;
    }

    public double getVertex() {
        return vertex;
    }

    public double getRadius() {
        return radius;
    }

    public void setFocalLength(double length)
    {
        focalLength = length;
    }

    public void setThickness(double thickness)
    {
        this.thickness = thickness < 0 ? 0 : thickness;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    public void setVertex(double v) {
        vertex = v;
    }

    public void setOrigin(double n) {
        origin = n;
    }

    public void setRadius(double r) {
        radius = r;
    }

    public Matrix calculateTransformation()
    {
        return new Matrix(-1, -1);
    }

    // refract takes in a ray that may or may not cross the lens
    // and return the resulting refracted ray.
    // @param rayIn: the ray that crosses the lens
    public Ray refract(Ray rayIn)  {
        return null;
    }

    // Given an X coordinate, getIntersect gives the
    // upper Y coordinate for (x, y)
    public double getEdge(double x) {
        double y;
        // Left parabola 
        if (x <= origin - thickness) {
            y = Math.sqrt((x - vertex) / focalLength);

        // Right parabola
        } else if (x >= origin + thickness) {
            y = Math.sqrt(-(x - vertex) / focalLength);

        // Lens edge
        } else {
            y = radius;
        }
        return y;
    }

    // Enumerated type for lens materials.
    // Each material has an associated refraction index.
    //
    // Indexes accessable at https://en.wikipedia.org/wiki/List_of_refractive_indices
    private enum Material
    {
        ICE     (1.31),
        GLASS   (1.58),
        ACRYLIC (1.491),
        DIAMOND (2.42);

        private final double refractionIndex;

        Material(double rID) {
            refractionIndex = rID;
        }

        double getRefractionIndex() {
            return refractionIndex;
        }
    }

}
