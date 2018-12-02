/**
 * Plotter.java
 * Class that plots pixels one at a time.
 *
 * @author Andrew Thorp
 * @version 2018.11.09
 */

import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;


public class Plotter {

    public static final Color LENS_COLOR = new Color(0xd9e5fc);
    private static Plotter plotterSingleton;
    private Frame frame;
    private Panel controlPanel;
    private MyCanvas canvas;

    private Plotter() {
        prepareGUI();
    }

    public void plotPoint(double x, double y) {
        Point2D p = new Point2D.Double(x, y);
        canvas.addPoint(p);
    }

    // prepareGUI handles the initialization
    // of the frame and GUI
    private void prepareGUI() {

        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout());

        canvas = new MyCanvas();

        controlPanel.add(canvas);

    }

    // getter for plotter plotterSingleton
    public static synchronized Plotter getPlotter() {
        if (plotterSingleton == null) {
            plotterSingleton = new Plotter();
        }
        return plotterSingleton;
    }
    
    /**
     * Used for the GUI to add the panel into its frame.
     *
     * @return Panel The panel of the Plotter.
     **/
    public Panel getPanel()
    {
        return controlPanel;
    }
    
    /**
     * Used for the GUI to interact with the canvas.
     *
     * @return Canvas The canvas of the Plotter.
     **/
    public Canvas getCanvas()
    {
        return canvas;
    }

    // Set change the window title
    //
    //@param name: the new window title.
    public void setName(String name) {
        frame.setTitle(name);
    }

    public void setLens(Lens lens) {
        canvas.setLens(lens);
    }

    public void setRay(Ray ray, int index) {
        canvas.setRay(ray, index);
    }

    // Wrapper for canvas repaint
    public void refresh() {
        canvas.repaint();
    }

    class MyCanvas extends Canvas {

        int xDimension;
        int yDimension;
        Color lensColor;
        Color lazerColor;
        Lens lens;
        Ray[] rays;
        ArrayList<Point2D> points;

        // No-arg constructor sets the default size to 400x400
        public MyCanvas() {
            points = new ArrayList<Point2D>();
            lensColor = LENS_COLOR;
            lazerColor = Color.RED;
            setBackground (Color.WHITE);
            rays = new Ray[3];
            xDimension = 400;
            yDimension = 400;
            setSize(xDimension, yDimension);
        }

        // paint replots all the points on the graph.
        // the points are all plotted in the color according to the color field
        // Currently there is method to change the color of each point
        //
        // ** This should never be called directly **
        // @param g: the internal Graphic. Not accessable in the current scope.
        public void paint(Graphics g) {
            // reset the color in case of change
            //Iterate through the data
            paintLens(g);
            paintRays(g);
            paintPoints(g);
        }

        private void paintLens(Graphics g) {
            //TODO fill the lens
            // g.setColor(lensColor);
            // ((Graphics2D)g).fill(new Area(lens.getPath()));
            g.setColor(Color.BLACK);
            ((Graphics2D)g).draw(lens.getPath()); 
        }

        private void paintPoints(Graphics g) {
            g.setColor(Color.GREEN);
            for(Point2D i : points)
                ((Graphics2D)g).draw(new Rectangle2D.Double(i.getX(), i.getY(), 0,0)); 
        }

        private void paintRays(Graphics g) {
            g.setColor(Color.RED);
            for(int i = 0; i < 3; i++) paintRay(g, rays[i]);
        }

        private void paintRay(Graphics g, Ray ray) {
            if (ray != null) ((Graphics2D) g).draw(ray.getLine());
        }

        // Adds a ray object to the list.
        // Ray numbers:
        // 0: The starting ray
        // 1: The ray inside the lens
        // 3: The exit ray
        private void setRay(Ray ray, int rayNumber) {
            if (ray != null) rays[rayNumber] = ray;
        }

        private void addPoint(Point2D p) {
            points.add(p);
        }

        private void setLens(Lens lens) {
            if (lens != null) this.lens = lens;
        }
    }
}
