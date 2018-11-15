import Jama.Matrix;
import java.lang.Math;

public class Ray {

    private final double MAX_ANGLE = Math.PI / 3;

    //matrix if of the form
    // 
    // |d|
    // |θ|
    //
    // where d is the starting y coordinate
    // and θ is the angle of trajectory
    private Matrix matrix;
    private double[] origin;

    // One arg ray constructor.
    // Initializes matrixn to [distance from origin, theta = 0]
    public Ray(double d) {
        setOrigin(0, 0);
        setMatrix(d, 0);
    }

    public Ray(double d, double theta) {
        setOrigin(0, 0);
        setMatrix(d, theta);
    }

    // Two arg constructor sets both the starting postition and the 
    public Ray(double x, double y, double d,  double theta) {
        setOrigin(x, y);
        setMatrix(d, theta);
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
        theta = setDirection(theta);
        double[][] matrix = {{d},{theta}};
        this.matrix = new Matrix(matrix);
    }

    public void setOrigin(double x, double y) {
        x = (x < 0) ? 0 : x;
        y = (y < 0) ? 0 : y;
        double[] o = {x, y};
        origin = o;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public double[][] getMatrixAsArray() {
        return matrix.getArrayCopy();
    }

    public double[] getOrigin() {
        return origin;
    }

    public Matrix goDistance(double distance) {
        double[][] distanceTransformation = {{1, distance},{0,1}};
        Matrix result = matrix.times(new Matrix(distanceTransformation));
        return result;
    }

}

