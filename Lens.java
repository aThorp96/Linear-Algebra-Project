import Jama.Matrix; 

public class Lens
{
    private enum Material
    {
        GLASS   (1.58);
        ACRYLIC (1.491);

        private final double refractionIndex;

        public Material(double rID) {
            refractionIndex = rID;
        }

        public double getRefractionIndex() {
            return refractionIndex;
        }
    }

    private Material material;

    private double focalLength;
    private double thickness;

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

    public void setFocalLength(double length)
    {
        focalLength = length < 0 ? 0 : length;
    }

    public void setThickness(double thickness)
    {
        this.thickness = thickness < 0 ? 0 : thickness;
    }

    public void setMaterial(Material material)
    {
        this.material = material;
    }

    public Matrix calculateTransformation()
    {
        return new Matrix(-1, -1);
    }

    // refract takes in a ray that may or may not cross the lens
    // and return the resulting refracted ray.
    // @param rayIn: the ray that crosses the lens
    public Ray refract(Ray rayIn)  {

    }
}
