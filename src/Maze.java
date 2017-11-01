/* Name: John Miller
 * Date: 22 October 2017
 * Class: CS 335 - 001
 * Project: Maze
 *
 * Structure: Create each view controller and add them to the JFrame. Depending on what buttons are clicked
 *            either generate or solve the maze.
 */

import javax.swing.JFrame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.BorderLayout;

public class Maze extends JFrame implements ActionListener{

    // Create each view controller
    static ControlView controlView;
    static ProgressView progressView;
    private MazeView mazeView;

    private Maze(){

        super("Maze");

        controlView = new ControlView(this);
        progressView = new ProgressView();

        // Set the rows and columns for the Maze to the slider bar values
        int rows = controlView.returnRow();
        int cols = controlView.returnCol();
        controlView.returnSpeed();
        mazeView = new MazeView(rows, cols);

        // Create the content pane and add each view to the content pane
        Container c = getContentPane();

        c.add(mazeView, BorderLayout.CENTER);
        c.add(controlView, BorderLayout.EAST);
        c.add(progressView, BorderLayout.SOUTH);

        // Set the size of the JFrame
        setSize(800, 600);
        setResizable(true);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e){

        // If the stop button is clicked, stop the generating or solving. Only allow the user to
        // generate a new maze after an maze is stopped
        if (e.getActionCommand().equals("Stop")){

            mazeView.genRunning = false;
            mazeView.currentSolveCell = mazeView.finalSolveCell;
            controlView.generate.setEnabled(true);
            controlView.showGenerate.setEnabled(true);
            controlView.solve.setEnabled(false);
            controlView.showSolve.setEnabled(false);

            progressView.currentLabel.setText("Currently: Stopped");
            progressView.currentLabel.repaint();
        }

        // If the generate button is clicked
        else if (e.getActionCommand().equals("Generate")){

            // Disable generate so user has to solve maze they generated
            controlView.showGenerate.setEnabled(false);
            controlView.generate.setEnabled(false);

            // If the maze is being animated
            if (controlView.showGenerate.isSelected()){

                // Create the new grid size and then show the maze being generated
                mazeView.newGrid(controlView.returnRow(), controlView.returnCol());
                mazeView.revalidate();
                mazeView.repaint();
                mazeView.showGenerateMaze(mazeView.grid, mazeView.visited, controlView.returnSpeed());
            }
            else {

                // Create the new grid size and generate maze with no animation
                mazeView.newGrid(controlView.returnRow(), controlView.returnCol());
                mazeView.revalidate();
                mazeView.repaint();
                mazeView.generateMaze(mazeView.grid, mazeView.visited);

                // Set solve to true if no generate animation
                controlView.showSolve.setEnabled(true);
                controlView.solve.setEnabled(true);
            }
        }

        // If the solve button is clicked
        else if (e.getActionCommand().equals("Solve")){

            // Set solve buttons to disable
            controlView.solve.setEnabled(false);
            controlView.showSolve.setEnabled(false);

            // If the solve is supposed to be animated, show the solving
            if (controlView.showSolve.isSelected()){
                mazeView.showSolveMaze(controlView.returnSpeed());
            }
            // Else just generate the answer
            else{
                mazeView.solveMaze();

                // Set generate to true if solve animation is not clicked
                controlView.generate.setEnabled(true);
                controlView.showGenerate.setEnabled(true);
            }
        }
    }

    public static void main(String args[]){
        Maze M = new Maze();
        M.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
