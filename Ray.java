import Jama.Matrix;
import java.lang.Math;
import java.awt.geom.Line2D;

public class Ray {

    private final double MAX_ANGLE = Math.PI / 3;

    //matrix if of the form
    // 
    // |d|
    // |s| where s = tan(θ)
    //
    // where d is the starting y coordinate
    // and θ is the angle of trajectory
    private Matrix matrix;
    private double x;
    private double y;

    // One arg ray constructor.
    // Initializes matrixn to [distance from origin, theta = 0]
    //
    // @param d: distance of ray
    public Ray(double d) {
        setOrigin(50, 200);
        setMatrix(d, 0);
    }

    // Two arg constructor.
    // Initializes the ray to an angle and distance
    //
    // @param d: ray length
    // @param theta: angle of ray (Radians)
    public Ray(double d, double theta) {
        setOrigin(0, d);
        setMatrix(d, theta);
    }

    // Two arg constructor sets both the starting postition and the 
    public Ray(double x, double y, double theta) {
        setOrigin(x, y);
        setMatrix(y, theta);
    }

    // The set direction to the angle in radians
    // The angle bounds are +/- MAX_ANGLE
    //
    // @param angle: the angle.
    private double setDirection(double theta) {
        // Check angle not out of bounds
        theta = (theta > MAX_ANGLE) ? MAX_ANGLE : theta;
        theta = (theta < -MAX_ANGLE) ? -MAX_ANGLE : theta;

        return theta;
    }

    public void setMatrix(double d, double theta) {
        d = (d < 0) ? 0 : d;
        double s = Math.tan(setDirection(theta));
        double[][] matrix = {{d},{s}};
        this.matrix = new Matrix(matrix);
    }

    public void setOrigin(double x, double y) {
        this.x = (x < 0) ? 0 : x;
        this.y = y;
    }

    public void setX(double x) {
        this.x = (x < 0) ? 0 : x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public double[][] getMatrixAsArray() {
        return matrix.getArrayCopy();
    }

    public double getX() {
        return x;
    }

    public Matrix goDistance(double distance) {
        double[][] distanceTransformation = {{1, distance},{0,1}};
        Matrix transformationMatrix = new Matrix(distanceTransformation);
        Matrix result = transformationMatrix.times(matrix);
        return result;
    }

    public double getY() {
        return y;
    }

    public Line2D.Double getLine() {
        double theta = Math.atan(matrix.getArray()[1][0]);
        double hypot = matrix.getArray()[0][0];
        int x1 = (int) (0.5 + x);
        int y1 = (int) (0.5 + y);
        int y2 = -(x1 + (int) (0.5 + (hypot / Math.acos(theta))));
        int x2 = -(y1 + (int) (0.5 + (hypot / Math.asin(theta))));
        return new Line2D.Double(x1, y1, x2, y2);
    }

}

