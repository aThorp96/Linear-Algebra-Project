import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javax.swing.JComboBox; //This is a drop down.
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

/**
 * This program creates a visual display to interact
 * with the lens and ray to view the different ray
 * refractions based on certain specifiers.
 *
 * @author Matthew Glancy
 * @version 11.27.2018
 **/
public class GUI implements ActionListener, ChangeListener
{
    //GUI Outline
    private JFrame frame;
    private JPanel layout;
    private Model model;
    private Plotter plotter;
    private Lens lens;
    //Ray Angle
    private JPanel anglePanel;
    private JSlider angleSlider;
    private JTextField angleField;
    private JLabel angleLabel;
    //Lens Curve
    private JPanel curvePanel;
    private JSlider curveSlider;
    private JTextField curveField;
    private JLabel curveLabel;
    //Lens Thickness
    private JPanel thickPanel;
    private JSlider thickSlider;
    private JTextField thickField;
    private JLabel thickLabel;
    //Lens Material
    private JPanel materialPanel;
    private JComboBox<Material> materialDropDown;
    private JLabel materialLabel;

    /**
     * This constructor will first create the frame, second
     * create the panels, and third add the panels into the
     * frame and make it visible.
     *
     * @param title The name given to the program.
     * @param width The width of the program's frame.
     * @param height The height of the program's frame.
     **/
    public GUI(String title, int width, int height)
    {
        model = new Model();
        createPlotter();
        int canvasHeight = plotter.getCanvas().getHeight();
        model.setLens(lens);

        createFrame(title, width, height + canvasHeight);
        createPanels();
        
        frame.add(plotter.getPanel(), BorderLayout.CENTER);
        frame.add(layout, BorderLayout.PAGE_END);

        frame.setVisible(true);
    }

    private void createPlotter()
    {
        plotter = Plotter.getPlotter();
        lens = new Lens(100, 10, Material.GLASS, 10);
        model.setLens(lens);
        plotter.refresh();
    }
    
    /**
     * Creates the frame of the GUI and sets up a grid
     * layout to place the panels in.
     *
     * @param title The name given to the program.
     * @param width The width of the program's frame.
     * @param height The height of the program's frame.
     **/
    private void createFrame(String title, int width, int height)
    {
        frame = new JFrame();
        frame.setTitle(title);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    /**
     * Simple method to declutter the constructor.
     **/
    private void createPanels()
    {
        layout = new JPanel(new GridLayout(0, 2));

        anglePanel = new JPanel();
        createAnglePanel();

        curvePanel = new JPanel();
        createCurvePanel();

        thickPanel = new JPanel();
        createThickPanel();

        materialPanel = new JPanel();
        createMaterialPanel();

        layout.add(anglePanel);
        layout.add(curvePanel);
        layout.add(thickPanel);
        layout.add(materialPanel);
    }
    
    /**
     * Sets up the ray's angle panel and adds all the necessary
     * elements into it. This includes its slider, text field,
     * and a label to determine what the user is editing.
     **/
    private void createAnglePanel()
    {
        angleLabel = new JLabel("Ray Angle:");

        angleSlider = new JSlider(-30, 30, 0);
        angleSlider.setMajorTickSpacing(15);
        angleSlider.setPaintTicks(true);
        angleSlider.setPaintLabels(true);
        angleSlider.addChangeListener(this);

        angleField = new JTextField(3);
        angleField.addActionListener(this);
        angleField.setText("0");

        anglePanel.add(angleLabel);
        anglePanel.add(angleField);
        anglePanel.add(angleSlider);
    }

    /**
     * Sets up the len's curvature panel and adds all the necessary
     * elements into it. This includes its slider, text field,
     * and a label to determine what the user is editing.
     **/
    private void createCurvePanel()
    {
        curveLabel = new JLabel("Lens Curvature:");

        curveSlider = new JSlider(0, 150, 0);
        curveSlider.setMajorTickSpacing(5);
        curveSlider.setPaintTicks(true);
        curveSlider.setPaintLabels(true);
        curveSlider.addChangeListener(this);

        curveField = new JTextField(3);
        curveField.addActionListener(this);
        curveField.setText("0");

        curvePanel.add(curveLabel);
        curvePanel.add(curveField);
        curvePanel.add(curveSlider);
    }
    
    /**
     * Sets up the len's thickness panel and adds all the necessary
     * elements into it. This includes its slider, text field,
     * and a label to determine what the user is editing.
     **/
    private void createThickPanel()
    {
        thickLabel = new JLabel("Lens Thickness:");

        thickSlider = new JSlider(0, 50, 0);
        thickSlider.setMajorTickSpacing(5);
        thickSlider.setPaintTicks(true);
        thickSlider.setPaintLabels(true);
        thickSlider.addChangeListener(this);

        thickField = new JTextField(3);
        thickField.addActionListener(this);
        thickField.setText("0");

        thickPanel.add(thickLabel);
        thickPanel.add(thickField);
        thickPanel.add(thickSlider);
    }

    private void createMaterialPanel()
    {
        materialLabel = new JLabel("Lens Material:");
        materialPanel.add(materialLabel);

        materialDropDown = new JComboBox<Material>(Material.values());
        materialDropDown.setSelectedIndex(2);
        materialDropDown.addActionListener(this);
        materialPanel.add(materialDropDown);
    }

    /**
     * Reads when the user updates a text field or changes the
     * material the drop down. Limits edits so it can't go past
     * the minimum and maximum of their respective sliders.
     *
     * @param event The text field being changed, or a drop down.
     **/
    public void actionPerformed(ActionEvent event)
    {
        JTextField textSource = new JTextField();
        JComboBox<Material> comboSource = new JComboBox<Material>();
        if (event.getSource() instanceof JTextField)
        {
            textSource = (JTextField) event.getSource();
        }
        else if (event.getSource() instanceof JComboBox)
        {
            comboSource = (JComboBox<Material>) event.getSource();
        }

        if (textSource == angleField)
        {
            int value = Integer.valueOf(textSource.getText());
            value = value >= angleSlider.getMinimum() ? value : angleSlider.getMinimum();
            value = value <= angleSlider.getMaximum() ? value : angleSlider.getMaximum();
            
            angleSlider.setValue(value);
            model.setAngle(value);
        }
        else if (textSource == curveField)
        {
            int value = Integer.valueOf(textSource.getText());
            value = value >= curveSlider.getMinimum() ? value : curveSlider.getMinimum();
            value = value <= curveSlider.getMaximum() ? value : curveSlider.getMaximum();
            
            curveSlider.setValue(value);
            model.setFocalLength(value);
        }
        else if (textSource == thickField)
        {
            int value = Integer.valueOf(textSource.getText());
            value = value >= thickSlider.getMinimum() ? value : thickSlider.getMinimum();
            value = value <= thickSlider.getMaximum() ? value : thickSlider.getMaximum();
            
            thickSlider.setValue(value);
            model.setThickness(value);
        }

        if (comboSource == materialDropDown)
        {
            model.setMaterial((Material) materialDropDown.getSelectedItem());
        }

        plotter.refresh();
    }

    /**
     * Reads whenever a slider has been updated.
     *
     * @param event The slider being changed.
     **/
    public void stateChanged(ChangeEvent event)
    {
        JSlider source = (JSlider) event.getSource();
        if (source == angleSlider)
        {
            angleField.setText(source.getValue() + "");
            model.setAngle(source.getValue());
        }
        else if (source == curveSlider)
        {
            curveField.setText(source.getValue() + "");
            model.setFocalLength(source.getValue());
        }
        else if (source == thickSlider)
        {
            thickField.setText(source.getValue() + "");
            model.setThickness(source.getValue());
        }

        plotter.refresh();
    }

    /**
     * Creates the GUI with given name, width, and height.
     *
     * @param args Arguments.
     **/
    public static void main(String[] args)
    {
        GUI gui = new GUI("Ray Observor", 450, 175);
    }
}
