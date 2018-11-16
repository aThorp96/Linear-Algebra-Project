import Jama.Matrix; 

public class Lens
{
    private enum Material
    {
        GLASS, ACRYLIC
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
}
