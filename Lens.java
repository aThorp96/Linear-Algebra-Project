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

    public double getFocalRadius(double x, double y) {
        double b = origin - y;
        double c = origin - x;
        c += (x <= origin) ? focalLength : -focalLength;
        return Math.sqrt(c * c + b + b);
    }

    // refract takes in a ray that may or may not cross the lens
    // and return the resulting refracted ray.
    // @param rayIn: the ray that crosses the lens
    public Ray refract(Ray rayIn, int x, int y)  {
        Matrix s = rayIn.getMatrix();
        Matrix R = new Matrix(2, 2);

        double m1;
        double m2;
        double r = getFocalRadius(x, y);

        if (x <= origin) {
            m1 = Material.AIR.getRefractionIndex();
            m2 = this.material.getRefractionIndex();
        } else {
            m1 = this.material.getRefractionIndex();
            m2 = Material.AIR.getRefractionIndex();
        }

        R.set(0, 0, 1);
        R.set(1, 0, 0);
        R.set(0, 1, ((m1 - m2) / (r * m2)));
        R.set(1, 1, m1 / m2);

        Matrix sPrime = R.times(rayIn.getMatrix());
        Ray rayOut = new Ray(x, y, 0);
        rayOut.setMatrix(sPrime);

        return rayOut;
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
