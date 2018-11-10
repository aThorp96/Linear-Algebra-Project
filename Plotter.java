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

    // wrapper for MyCanvas addPoint
    public void plot(int x, int y) {
        canvas.addPoint(x, y);
    }

    // wrapper for MyCanvas removePoint
    public void unPlot(int x, int y) {
        canvas.removePoint(x, y);
    }

    // wrapper for MyCanvas addPoint
    public void plot(double x, double y) {
        canvas.addPoint((int) (x + 0.5), (int)(y + 0.5));
    }

    // wrapper for MyCanvas removePoint
    public void unPlot(double x, double y) {
        canvas.removePoint((int) (x + 0.5), (int)(y + 0.5));
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
        boolean[][] data;
        Color color;

        // No-arg constructor sets the default size to 400x400
        public MyCanvas() {
            xDimension = 400;
            yDimension = 400;
            data = new boolean[yDimension][xDimension];
            setBackground (Color.BLACK);
            setSize(xDimension, yDimension);
            color = Color.WHITE;
        }

        // two arg sontructor allows for custom size XxY
        //
        //@param x the x dimension
        //@param y: the y dimension
        public MyCanvas(int x, int y) {
            xDimension = x;
            yDimension = y;
            data = new boolean[yDimension][xDimension];
            setBackground (Color.BLACK);
            setSize(xDimension, yDimension);
            color = Color.WHITE;
        }

        // paint replots all the points on the graph.
        // the points are all plotted in the color according to the color field
        // Currently there is method to change the color of each point
        //
        // ** This should never be called directly **
        // @param g: the internal Graphic. Not accessable in the current scope.
        public void paint(Graphics g) {
            // reset the color in case of change
            g.setColor(color);
            //Iterate through the data
            for(int i = 0; i < xDimension; i++) {
                for (int j = 0; j < yDimension; j++) {
                    if (data[i][j]) g.fillRect(i, j, 1, 1);
                }
            }
        }

        // Adds a point to the data map
        private void addPoint(int x, int y) {
            data[x][y] = true;
        }

        // removes a point from the data map
        private void removePoint(int x, int y) {
            data[x][y] = false;
        }

        // Change the plot color
        private void setColor(Color c) {
            this.color = c;
        }
    }
}
