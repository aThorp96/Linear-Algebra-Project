import Jama.Matrix;
import java.lang.Math;
import java.awt.geom.Line2D;

public class Ray {

    private final double MAX_ANGLE = 1;

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
    // Initializes matrix to [distance from origin, theta = 0]
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
        setOrigin(50, 200);
        setMatrix(d, theta);
    }

    // Two arg constructor sets both the starting postition and the 
    public Ray(double x, double y, double theta) {
        setOrigin(x, y);
        setMatrix(y, theta);
    }

    public void setDirection(double theta) {

        matrix.set(1, 0, Math.tan(theta));
    }

    public void setDistance(double y2) {

        matrix.set(0, 0, y2);
    }

    public void setX(double x) {
        this.x = (x < 0) ? 0 : x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    /**
     * Given a y value, returns the corrosponding
     * x value. If the Y value does not get touched by
     * the ray, method returns NaN.
     *
     * @param y: the y value
     * @return the corrosponding y value
     **/
    public double getX(double y) {
        //TODO: Write calculator
        return Double.NaN;
    }

    /**
     * Given a x value, returns the corrosponding
     * Y value. If the x value does not get touched by
     * the ray, method returns NaN.
     *
     * @param x: the x value
     * @return the corrosponding y value
     **/
     public double getY(double x) {
        //TODO: write pair calculator
        return Double.NaN;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public double getDirection() {
        double theta = Math.atan(matrix.get(1,0));
        return theta;
    }

    public double[][] getMatrixAsArray() {
        return matrix.getArrayCopy();
    }

    // The set direction to the angle in radians
    // The angle bounds are +/- MAX_ANGLE
    //
    // @param angle: the angle.
    private double changeDirection(double theta) {
        // Check angle not out of bounds
        theta = (theta > MAX_ANGLE) ? MAX_ANGLE : theta;
        theta = (theta < -MAX_ANGLE) ? -MAX_ANGLE : theta;

        return theta;
    }

    public void setMatrix(double y, double theta) {
        y = (y < 0) ? 0 : y;
        double s = Math.tan(changeDirection(theta));
        double[][] matrix = {{y},{s}};
        this.matrix = new Matrix(matrix);
    }

    public void setOrigin(double x, double y) {
        this.x = (x < 0) ? 0 : x;
        this.y = y;
    }

    public Matrix goDistance(double distance) {
        double[][] distanceTransformation = {{1, distance},{0,1}};
        Matrix transformationMatrix = new Matrix(distanceTransformation);
        Matrix result = transformationMatrix.times(matrix);
        return result;
    }

    // Not functioning. 
    public void rotate(double angle) {
        double concreteAngle = changeDirection(angle + getDirection());

        // if the angle doesn't change, don't update any data
        if (concreteAngle == angle + getDirection()) {
            double sinTheta = Math.sin(getDirection());
            double sinThetaPrime = Math.sin(concreteAngle);
            double opposite = matrix.get(0,0);
            double oppositePrime = y - ((opposite * sinThetaPrime) / sinTheta);

            System.out.printf("concreteAngle: %f\n sinTheta: %f\n sinThetaPrime: %f\n opposite: %f\n oppositePrime: %f\n",concreteAngle,  sinTheta, sinThetaPrime, opposite, oppositePrime);

            matrix.set(0,0, oppositePrime);
            setDirection(concreteAngle);
        }
    }

    public Line2D.Double getLine() {
        int x1 = (int) (0.5 + x);
        int y1 = (int) (0.5 + y);

        int y2 = y1 - (int) (0.5 + matrix.get(0,0));
        // if theta == 0 then x2 = hypotonus
        int x2 = x1 + (int) (0.5 + (matrix.get(0,0) / Math.sin(getDirection())));

        // if theta != 0, x2 = o / tan(theta)
        if (getDirection() != 0) {
            x2 = x1 + (int) (0.5 + (matrix.get(0,0) / Math.tan(getDirection())));
        }

        System.out.printf("x1: %d\n", x1);
        System.out.printf("y1: %d\n", y1);
        System.out.printf("x2: %d\n", x2);
        System.out.printf("y2: %d\n", y2);
        int o = y2 - y1;
        int a = x2 - x1;
        double h = Math.sqrt((double) (o * o) + (a * a));
        System.out.printf("Hypotenuse: %f\n", h);
        System.out.printf("angle(Radians): %f\n\n", getDirection());

        return new Line2D.Double(x1, y1, x2, y2);
    }

}

