/* Name: John Miller
 * Date: 1 November 2017
 * Class: CS 335 - 001
 * Project: Maze
 *
 * Structure: Create all the buttons and sliders. Add them to the panel and add action listeners.
 *            Return the rows, cols, and speed based on the slider values.
 */

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

class ControlView extends JPanel{

    // Set initial rows, cols, and speed
    private int rows = 10;
    private int cols = 10;
    private int speed = 25;

    private JSlider rowSlider, columnSlider, speedSlider;
    private JLabel rowsLabel, columnsLabel, speedLabel;
    JButton solve, generate;
    JCheckBox showSolve, showGenerate;

    ControlView(ActionListener AL){

        // Create all buttons, sliders, check box's and labels
        JButton stop = new JButton("Stop");
        solve = new JButton("Solve");
        generate = new JButton("Generate");
        showSolve = new JCheckBox("Show Solve");
        showGenerate = new JCheckBox("Show Generation");
        speedSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 150, 70);
        rowSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 50, 10);
        columnSlider = new JSlider(SwingConstants.HORIZONTAL, 10, 50, 10);
        speedLabel = new JLabel("  Speed: Medium");
        rowsLabel = new JLabel("  Rows: 10");
        columnsLabel = new JLabel("  Columns: 10");

        // Add action listeners to the buttons
        stop.addActionListener(AL);
        solve.addActionListener(AL);
        generate.addActionListener(AL);

        // Set solve buttons to disabled until a maze is generated
        solve.setEnabled(false);
        showSolve.setEnabled(false);

        // Add all the pieces to the panel
        super.add(generate);
        super.add(showGenerate);
        super.add(solve);
        super.add(showSolve);
        super.add(speedLabel);
        super.add(speedSlider);
        super.add(rowsLabel);
        super.add(rowSlider);
        super.add(columnsLabel);
        super.add(columnSlider);
        super.add(stop);

        // Set the layout of the panel
        super.setLayout(new GridLayout(11, 1, 0, 0));
    }

    int returnRow(){

        // Create a row slider change listener. Set the row number to the value
        // of the slider
        rowSlider.addChangeListener(e -> {
            rows = rowSlider.getValue();
            rowsLabel.setText("  Rows: " + rows);
            repaint();
        });
        return rows;
    }

    int returnCol(){

        // Create a column slider change listener and set the number of
        // cols to the value of the slider
        columnSlider.addChangeListener(e -> {
            cols = columnSlider.getValue();
            columnsLabel.setText("  Columns: " + cols);
            repaint();
        });
        return cols;
    }

    int returnSpeed(){

        // Create a speed slider change listener
        speedSlider.addChangeListener(e -> {
            speed = speedSlider.getValue();

            // Change the speed value and return the speed based on the value
            // of the slider
            if (speed >= 100 && speed <= 150){
                speedLabel.setText("  Speed: Slow");
            }else if (speed >= 70 && speed < 100){
                speedLabel.setText("  Speed: Medium");
            }else{
                speedLabel.setText("  Speed: Fast");
            }
            repaint();
        });
        return speed;
    }
}
