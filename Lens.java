import Jama.Matrix;

import java.awt.geom.*;

public class Lens
{
    // Parabola values
    private double focalLength;

    // Lens values
    private double origin;
    private double thickness;
    private double radius;
    private Material material;
    private Path2D path;

    public Lens(double radius, double thickness, Material material, double focalLength) {
        setOrigin(200);
        setRadius(radius);
        setThickness(thickness);
        setMaterial(material);
        setFocalLength(focalLength);
    }

    public double getOrigin() {
        return origin;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public double getThickness() {
        return thickness;
    }

    public double getRadius() {
        return radius;
    }

    public Material getMaterial() {
        return material;
    }

    public Path2D getPath() {
        return path;
    }

    public void setFocalLength(double length) {
        focalLength = length;
        updatePath();
    }

    public void setThickness(double thickness) {
        this.thickness = thickness < 0 ? 0 : thickness;
        updatePath();
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setOrigin(double n) {
        origin = n;
        updatePath();
    }

    public void setRadius(double r) {
        radius = r;
        updatePath();
    }

    public Matrix calculateTransformation() {
        return new Matrix(-1, -1);
    }

    // refract takes in a ray that may or may not cross the lens
    // and return the resulting refracted ray.
    // @param rayIn: the ray that crosses the lens
    public Ray refract(Ray rayIn)  {
        return null;
    }

    // setPath creates the path from the parameters given.
    // must be called after focalLength, origin, thickness, and radius have been set.
    public void updatePath() {
        Path2D path = new Path2D.Double();

        double topRightX    = origin + (thickness / 2);
        double topRightY    = origin - radius;
        double topLeftX     = origin - (thickness / 2);
        double topLeftY     = origin - radius;
        double BottomRightX = origin + (thickness / 2);
        double BottomRightY = origin + radius;
        double BottomLeftX  = origin - (thickness / 2);
        double BottomLeftY  = origin + radius;

        // right curve
        QuadCurve2D.Double curve1 = new QuadCurve2D.Double(
            topLeftX,
            topLeftY,
            origin - focalLength,
            origin,
            BottomLeftX,
            BottomLeftY
            );

        // left curve
        QuadCurve2D.Double curve2 = new QuadCurve2D.Double(
            topRightX,
            topRightY,
            origin + focalLength,
            origin,
            BottomRightX,
            BottomRightY
            );

        // Top edge
        Line2D topEdge = new Line2D.Double(
            topLeftX,
            topLeftY,
            topRightX,
            topRightY
            );


        // Top edge
        Line2D bottomEdge = new Line2D.Double(
            BottomLeftX,
            BottomLeftY,
            BottomRightX, BottomRightY
            );

        path.append(curve1.getPathIterator(null), false);
        path.append(curve2.getPathIterator(null), false);
        path.append(bottomEdge.getPathIterator(null), false);
        path.append(topEdge.getPathIterator(null), false);

        this.path = path;
    }

}
