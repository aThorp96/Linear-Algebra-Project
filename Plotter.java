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

    // Wrapper for canvas repaint
    public void refresh() {
        canvas.repaint();
    }

/*    public static void main(String[] args) {
        Plotter plotter = Plotter.getPlotter();
        Random r = new Random();
        for (int i = 0; i < 400; i++) {
            plotter.plot(r.nextInt(400), r.nextInt(400));
        }

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
        }

        plotter.canvas.setColor(Color.RED);

        for (int i = 0; i < 800; i++) {
            plotter.plot(r.nextInt(400), r.nextInt(400));
        }

        plotter.refresh();
    }

*/    /**
        MyCanvas is the canvas that handles the plotting of points.
        Plotter is effectively a wrapper for the canvas.
    */
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
        }

        // two arg sontructor allows for custom size XxY
        //
        //@param x the x dimension
        //@param y: the y dimension
        public MyCanvas(int x, int y) {
            xDimension = x;
            yDimension = y;
            setBackground (Color.BLACK);
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
            for(int i = 0; i < xDimension; i++) {
                for (int j = 0; j < yDimension; j++) {
                }
            }
        }

        private void paintLens(Graphics g) {
            g.setColor(lensColor);
            int[][] points = calculateLensPoints();
            for (int[] i : points) g.fillRect(i[0], i[1], 1, 1);
        }

        private void paintRays(Graphics g) {

        }

        private int[][]  calculateLensPoints() {
            int size = (int) ( 0.5 + lens.getRadius());
            int[][] points = new int[size * 2][2];
            for (int i = 0; i < points.length; i += 2) {
                int x = (int) (i - lens.getVertex());
                int y = (int) (lens.getEdge(i) + 0.5);
                points[i] = new int[]{x, y};
                points[i + 1] = new int[]{x, -y};
            }
            return points;
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
