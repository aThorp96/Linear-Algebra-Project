import jama.Matrix;
import java.util.Math;

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

    // One arg ray constructor.
    // Initializes matrixn to [distance from origin, theta = 0]
    public Ray(double d) {
        setMatrix(d, 0);
    }

    // Two arg constructor sets both the starting postition and the 
    public Ray(double y, double theta) {
        setMatrix(y, theta);
    }

    // The set direction to the angle in radians
    // The angle bounds are +/- MAX_ANGLE
    //
    // @param angle: the angle.
    private void setDirection(double theta) {
        // Check angle not out of bounds
        angle = (angle > MAX_ANGLE) ? MAX_ANGLE : angle;
        angle = (angle < -MAX_ANGLE) ? -MAX_ANGLE : angle;

        direction = angle;
    }

    public setOrigin(double d, double theta) {
        theta = setDirection(theta);
        origin = new Matrix(double[][]{{d},{theta}});
    }

    public Matrix getOrigin() {
        return origin;
    }

    public double[][] getOrigin() {
        return origin.toArrayCopy();
    }

    public Matrix goDistance(double distance) {
        double[][] distanceTransformation = {{1, distance},{0,1}};
        Matrix result = origin.times(distanceFormula);
    }
}
