/* Name: John Miller
 * Date: 1 November 2017
 * Class: CS 335 - 001
 * Project: Maze
 */

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;

class ProgressView extends JPanel{

    JLabel progressLabel, currentLabel;

    ProgressView(){

        // Make a progress and current label
        progressLabel = new JLabel("Progress: 0%");
        currentLabel = new JLabel("Currently: Doing Nothing");

        // Add them to the JPanel
        super.add(progressLabel);
        super.add(currentLabel);

        // Set the layout of the panel
        super.setLayout(new GridLayout(1, 2, 0,0));
    }
}
