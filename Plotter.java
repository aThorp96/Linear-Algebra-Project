/**
 * Plotter.java
 * Class that plots pixels one at a time.
 *
 * @author Andrew Thorp
 * @version 2018.11.09
 */

import java.util.concurrent.TimeUnit;
import java.util.Random;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.QuadCurve2D;


public class Plotter {

    public static final Color LENS_COLOR = new Color(0xd9e5fc);
    private static Plotter plotterSingleton;
    private Frame frame;
    private Panel controlPanel;
    private MyCanvas canvas;

    private Plotter() {
        prepareGUI();
    }

    // prepareGUI handles the initialization
    // of the frame and GUI
    private void prepareGUI() {
        frame = new Frame("");
        frame.setSize(410, 440);
        frame.setLayout(new GridLayout(1,1));
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });
        controlPanel = new Panel();
        controlPanel.setLayout(new FlowLayout());

        canvas = new MyCanvas();

        controlPanel.add(canvas);
        frame.add(controlPanel);
        frame.setVisible(true);

    }

    // getter for plotter plotterSingleton
    public static synchronized Plotter getPlotter() {
        if (plotterSingleton == null) {
            plotterSingleton = new Plotter();
        }
        return plotterSingleton;
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

        // No-arg constructor sets the default size to 400x400
        public MyCanvas() {
            lensColor = LENS_COLOR;
            lazerColor = Color.RED;
            xDimension = 400;
            yDimension = 400;
            setBackground (Color.WHITE);
            setSize(xDimension, yDimension);
        }

        // paint replots all the points on the graph.
        // the points are all plotted in the color according to the color field
        // Currently there is method to change the color of each point
        //
        // ** This should never be called directly **
        // @param g: the internal Graphic. Not accessable in the current scope.
        public void paint(Graphics g) {
        System.out.println("painting");
            // reset the color in case of change
            //Iterate through the data
            paintLens(g);
        }

        private void paintLens(Graphics g) {
            g.setColor(Color.BLACK);
            ((Graphics2D)g).draw(lens.getPath());

        }

        private void paintRays(Graphics g) {

        }

        // Adds a ray object to the list.
        // Ray numbers:
        // 0: The starting ray
        // 1: The ray inside the lens
        // 3: The exit ray
        private void setRay(int rayNumber, Ray ray) {
            if (ray != null) rays[rayNumber] = ray;
        }

        private void setLens(Lens lens) {
            if (lens != null) this.lens = lens;
        }
    }
}
